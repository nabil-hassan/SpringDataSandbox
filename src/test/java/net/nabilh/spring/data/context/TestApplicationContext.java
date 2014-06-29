package net.nabilh.spring.data.context;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test class which verifies application context.
 * N.B. normally would be separate tests for the main app context file, and test app context file, but since we this app does not have a main file, no need to test for it.
 *
 * Author: Nabil Hassan
 * Date: 29/06/14 11:43
 */
public class TestApplicationContext {

    private static final String APP_CONTEXT_ERROR = "Error starting application context";

    /**
     * Verifies the application context can be parsed and used to create an IOC container.
     * Fails if there is any problem starting the IOC container
     */
    @Test
    public void testApplicationContextTestStartup() {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("application-context-test.xml");
        } catch (BeansException ex) {
            ex.printStackTrace();
            fail(APP_CONTEXT_ERROR);
        }
    }


}
