package net.nabilh.spring.data.dao;

import net.nabilh.spring.data.domain.Address;
import net.nabilh.spring.data.domain.Contact;
import net.nabilh.spring.data.domain.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test CRUD and query methods of ContactDAO class.
 *
 * Author: Nabil Hassan
 * Date: 29/06/14 11:36
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context-test.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ContactDAOTest {

    @Autowired
    private ContactDAO contactDAO;

    @Autowired
    private AddressDAO addressDAO;

    // Contact entity test data constants.
    private static final Title CONTACT_1_TITLE = Title.MR;
    private static final String CONTACT_1_FORENAME = "Nabil";
    private static final String CONTACT_1_SURNAME = "Hassan";
    private static final Title CONTACT_2_TITLE = Title.MR;
    private static final String CONTACT_2_FORENAME = "John";
    private static final String CONTACT_2_SURNAME = "Smith";

    // Address entity test data constants.
    private static final String ADDRESS_1_POSTCODE = "BN16HF";
    private static final String ADDRESS_1_TOWN = "Brighton";
    private static final String ADDRESS_1_COUNTY = "East Sussex";
    private static final String ADDRESS_1_LINE1 = "56 Beaconsfield Villas";
    private static final String ADDRESS_2_POSTCODE = "GU14SJ";
    private static final String ADDRESS_2_TOWN = "Guildford";
    private static final String ADDRESS_2_COUNTY = "Surrey";
    private static final String ADDRESS_2_LINE1 = "9 Bedford Road";

    // Assertion message constants
    private static final String COULD_NOT_PERSIST_ADDRESS = "Could not persist a new address instance.";
    private static final String COULD_NOT_PERSIST_CONTACT = "Could not persist a new contact instance.";
    private static final String NO_CONTACTS_FOUND = "No persisted contact were found.";
    private static final String MORE_THAN_ONE_CONTACT_FOUND = "Found more than one contact with specified surname. Expected exactly 1 match.";
    private static final String INCORRECT_CONTACT_FOUND = "Query returned wrong contact..";
    private static final String ERROR_ADDING_ADDRESS = "Error adding contact address: {0}";
    private static final String NO_ADDRESS_ASSOCIATION_FOR_CONTACT ="Contact does not have an association to address";
    private static final String NO_CONTACT_ASSOCIATION_FOR_ADDRESS ="Address does not have an association to contact";
    private static final String CONTACT_ADDRESS_DOES_NOT_MATCH_EXPECTED ="Contact address does not match expected address";
    private static final String ADDRESS_CONTACT_DOES_NOT_MATCH_EXPECTED ="Address contact does not match expected contact";

    /**
     * Verify that it is possible to persist a new Contact instance to the database.
     */
    @Test
    public void testPersistContact() {
        Contact contact = new Contact(CONTACT_1_TITLE, CONTACT_1_FORENAME, CONTACT_1_SURNAME);
        contactDAO.save(contact);
        assertNotNull(COULD_NOT_PERSIST_CONTACT, contact.getId());
    }

    /**
     * Verify that it is possible to persist and associate new Contact and Address instances.
     */
    @Test
    public void testPersistContactAddress() {
        // Instantiate and persist address.
        Address address = new Address(ADDRESS_1_POSTCODE, ADDRESS_1_LINE1, ADDRESS_1_TOWN, ADDRESS_1_COUNTY);
        addressDAO.save(address);

        // Instantiate contact, attempt to associate with address.
        Contact contact = new Contact(CONTACT_1_TITLE, CONTACT_1_FORENAME, CONTACT_1_SURNAME);
        try {
            address.addContact(contact);
        } catch (Exception ex) {
            fail(ERROR_ADDING_ADDRESS + ex.getMessage());
        }

        // Persist contact. Verify that both contact and address were persisted, and that address can be obtained from contact and vice versa.
        contactDAO.save(contact);
        assertNotNull(COULD_NOT_PERSIST_CONTACT, contact.getId());
        assertNotNull(COULD_NOT_PERSIST_ADDRESS, address.getId());
        assertNotNull(NO_ADDRESS_ASSOCIATION_FOR_CONTACT, contact.getAddress());
        assertEquals(CONTACT_ADDRESS_DOES_NOT_MATCH_EXPECTED, contact.getAddress().getId(), address.getId());
        assertTrue(NO_CONTACT_ASSOCIATION_FOR_ADDRESS, address.getContacts().size() == 1);
        assertEquals(ADDRESS_CONTACT_DOES_NOT_MATCH_EXPECTED, address.getContacts().iterator().next().getId(), contact.getId());
    }

    /**
     * Verify that it is possible to update a contact's address.
     */
    @Test
    public void testUpdateContactAddress() {
        // Instantiate and persist addresses.
        Address address1 = new Address(ADDRESS_1_POSTCODE, ADDRESS_1_LINE1, ADDRESS_1_TOWN, ADDRESS_1_COUNTY);
        addressDAO.save(address1);
        Address address2 = new Address(ADDRESS_2_POSTCODE, ADDRESS_2_LINE1, ADDRESS_2_TOWN, ADDRESS_2_COUNTY);
        addressDAO.save(address2);

        // Instantiate contact, attempt to associate with address 1.
        Contact contact = new Contact(CONTACT_1_TITLE, CONTACT_1_FORENAME, CONTACT_1_SURNAME);
        try {
            address1.addContact(contact);
        } catch (Exception ex) {
            fail(MessageFormat.format(ERROR_ADDING_ADDRESS, ex.getMessage()));
        }

        // Persist contact. Verify that contact is associated with address 1.
        contactDAO.save(contact);
        assertEquals(CONTACT_ADDRESS_DOES_NOT_MATCH_EXPECTED, contact.getAddress().getId(), address1.getId());
        assertEquals(ADDRESS_CONTACT_DOES_NOT_MATCH_EXPECTED, address1.getContacts().iterator().next().getId(), contact.getId());

        // Associate with address 2, verify association has updated.
        try {
            address2.addContact(contact);
        } catch (Exception ex) {
            fail(MessageFormat.format(ERROR_ADDING_ADDRESS, ex.getMessage()));
        }
        assertEquals(CONTACT_ADDRESS_DOES_NOT_MATCH_EXPECTED, contact.getAddress().getId(), address2.getId());
        assertEquals(ADDRESS_CONTACT_DOES_NOT_MATCH_EXPECTED, address2.getContacts().iterator().next().getId(), contact.getId());
    }

    /**
     * Verify the findBySurname() method returns entities that match the specified surname
     */
    @Test
    public void testFindBySurname() {
        // Instantiate and persist 2 customers. Each has a different surname.
        Contact contactSurnameMatch = new Contact(CONTACT_1_TITLE, CONTACT_1_FORENAME, CONTACT_1_SURNAME);
        Contact contactNoSurnameMatch = new Contact(CONTACT_2_TITLE, CONTACT_2_FORENAME, CONTACT_2_SURNAME);
        contactDAO.save(contactSurnameMatch);
        contactDAO.save(contactNoSurnameMatch);

        // Use findBySurname() to return a list of contacts matching supplied surname. Only one match should be returned, and the id should be the same as contactSurnameMatch.
        List<Contact> dbContactList = contactDAO.findBySurname(CONTACT_1_SURNAME);
        assertTrue(NO_CONTACTS_FOUND, dbContactList.size() > 0);
        assertEquals(MORE_THAN_ONE_CONTACT_FOUND, dbContactList.size(), 1);
        assertEquals(INCORRECT_CONTACT_FOUND, dbContactList.iterator().next().getId(), contactSurnameMatch.getId());
    }

    /**
     * Verify that the delete() method will remove a persisted contact instance from the database.
     */
    @Test
    public void testDeleteCustomer() {
        // Instantiate and persist 2 customers.
        Contact contactToBeDeleted = new Contact(CONTACT_1_TITLE, CONTACT_1_FORENAME, CONTACT_1_SURNAME);
        Contact contact = new Contact(CONTACT_2_TITLE, CONTACT_2_FORENAME, CONTACT_2_SURNAME);
        contactDAO.save(contactToBeDeleted);
        contactDAO.save(contact);

        // Delete contactToBeDeleted and query the db. Only one contact should be returned, and it should be same as "contact".
        contactDAO.delete(contactToBeDeleted);

        // Use findBySurname() to return a list of contacts matching supplied surname. Only one match should be returned, and the id should be the same as contactSurnameMatch.
        List<Contact> dbContactList = (List<Contact>) contactDAO.findAll();
        assertTrue(NO_CONTACTS_FOUND, dbContactList.size() > 0);
        assertEquals(MORE_THAN_ONE_CONTACT_FOUND, dbContactList.size(), 1);
        assertEquals(INCORRECT_CONTACT_FOUND, dbContactList.iterator().next().getId(), contact.getId());
    }

}
