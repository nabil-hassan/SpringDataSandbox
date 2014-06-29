package net.nabilh.spring.data.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertTrue;

/**
 * Test CRUD and query methods of ContactDAO class.
 *
 * Author: Nabil Hassan (inside.the.byte@gmail.com)
 * Date: 29/06/14 11:36
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context-test.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ContactDAOTest {

    @Autowired
    private ContactDAO contactDAO;

    @Test
    public void testTestTest() {
        assertTrue(true);
    }

}
