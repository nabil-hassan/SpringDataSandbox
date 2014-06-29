package net.nabilh.spring.data.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity domain class to model a UK address.
 *
 * Author: Nabil Hassan
 * Date: 29/06/14 00:23
 */
@Entity
@SuppressWarnings("unused")
public class Address {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String postcode;

    @Column(nullable = false)
    private String addressLine1;

    @Column(nullable = true)
    private String addressLine2;

    @Column(nullable = true)
    private String addressLine3;

    @Column(nullable = true)
    private String addressLine4;

    @Column(nullable = true)
    private String addressLine5;

    @Column(nullable = false)
    private String town;

    @Column(nullable = false)
    private String county;

    @OneToMany (mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Contact> contacts = new ArrayList<Contact>();

    // String constants
    private static final String CONTACT_PARAM_CANNOT_BE_NULL_EX = "System Exception: Contact parameter cannot be null";

    public Address() {
    }

    public Address(String postcode, String addressLine1, String town, String county) {
        this(postcode, addressLine1, null, null, null, null, town, county);
    }

    public Address(String postcode, String addressLine1, String addressLine2, String addressLine3,
                   String addressLine4, String addressLine5, String town, String county) {
        this.postcode = postcode;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.addressLine4 = addressLine4;
        this.addressLine5 = addressLine5;
        this.town = town;
        this.county = county;
    }

    public Long getId() {
        return id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
    }

    public String getAddressLine5() {
        return addressLine5;
    }

    public void setAddressLine5(String addressLine5) {
        this.addressLine5 = addressLine5;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    /**
     * Associates a contact with address.
     * If the contact is null an exception is thrown.
     * @param contact - the contact to associate with the address.
     * @throws Exception
     */
    public void addContact(Contact contact) throws Exception{
        if (contact == null) {
            throw new Exception(CONTACT_PARAM_CANNOT_BE_NULL_EX);
        }
        // Is the contact already associated with an address?
        if (contact.getAddress() != null) {
            Address currentContactAddress = contact.getAddress();

            /* If the contact is currently associated with another address, the relationship is removed in order to establish the new one.
               Otherwise If the contact is already associated with the current address, return. */
            if (currentContactAddress.getId() != null && !(currentContactAddress.getId().equals(id))) {
                contact.getAddress().getContacts().remove(contact);
                contact.setAddress(null);
            } else if(currentContactAddress.getId() != null && currentContactAddress.getId().equals(id)) {
                return;
            }
        }
        // Form relationship between contact and this address.
        contacts.add(contact);
        contact.setAddress(this);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", postcode='" + postcode + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", addressLine3='" + addressLine3 + '\'' +
                ", addressLine4='" + addressLine4 + '\'' +
                ", addressLine5='" + addressLine5 + '\'' +
                ", town='" + town + '\'' +
                ", county='" + county + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
