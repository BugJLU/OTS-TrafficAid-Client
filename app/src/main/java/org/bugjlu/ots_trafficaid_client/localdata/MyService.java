package org.bugjlu.ots_trafficaid_client.localdata;

import org.bugjlu.ots_trafficaid_client.remote.remote_object.User;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.ContactService;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.ContactServiceImpl;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.ResourceService;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.ResourceServiceImpl;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.UserService;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.UserServiceImpl;

public class MyService {

    public static String userName = null;
    public static ContactService contactService;
    public static ResourceService resourceService;
    public static UserService userService;

    static {
        contactService = new ContactServiceImpl();
        resourceService = new ResourceServiceImpl();
        userService = new UserServiceImpl();
    }
}
