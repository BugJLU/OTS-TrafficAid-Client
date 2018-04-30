package org.bugjlu.ots_trafficaid_client;

import org.bugjlu.ots_trafficaid_client.remote.remote_object.Contact;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.ContactService;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.ContactServiceImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    ContactService contactService = new ContactServiceImpl();
    @Test
    public void testtesttest() throws Exception {
        Contact contact = new Contact();
        contact.setSubjectId("2");
        contact.setObjectId("6");
        contact.setGroupType(0);
        Boolean b = contactService.addContact(contact);
        Assert.assertTrue(b);
        Contact c = contactService.getContact("7", "2");
        Assert.assertNotNull(c);
        System.out.println(c.getIntimacy());
        contactService.removeContact("7", "2");
        Assert.assertTrue(b);

    }
}