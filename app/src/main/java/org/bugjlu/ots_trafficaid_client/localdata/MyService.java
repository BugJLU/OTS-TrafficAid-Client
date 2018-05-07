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

    public static class contactType {
        public static int relative = 0;
        public static int friend = 1;
        public static int college = 2;
        public static int stranger = 3;
        public static String getName(int i) {
            switch (i) {
                case 0:
                    return "relative";
//                    break;
                case 1:
                    return "friend";
//                    break;
                case 2:
                    return "college";
//                    break;
                case 3:
                    return "stranger";
//                    break;
                default:
                    return "stranger";
//                    break;
            }
        }
    }

    static {
        contactService = new ContactServiceImpl();
        resourceService = new ResourceServiceImpl();
        userService = new UserServiceImpl();
    }
}
