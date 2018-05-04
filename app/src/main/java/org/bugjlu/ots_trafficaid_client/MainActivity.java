package org.bugjlu.ots_trafficaid_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.bugjlu.ots_trafficaid_client.remote.remote_object.Contact;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.Resource;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.User;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.ContactService;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.ContactServiceImpl;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.ResourceService;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.ResourceServiceImpl;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.UserService;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.UserServiceImpl;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tv1;
    Button cnm;
    public class GetMsgRunnable implements Runnable {

        @Override
        public void run() {
//            final String str = String.valueOf(contactTest());
//            final String str = resourceTest();
//            final String str = userTest();
//            final String str = aroundTest();
            final String str = addUserTest();
//            User u = userService.getUser("10086");
//            final String str = u == null ? "null" : u.getName();
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    tv1.setText(str);
                }
            });
        }
    }

    ContactService contactService = new ContactServiceImpl();
    ResourceService resourceService = new ResourceServiceImpl();
    UserService userService = new UserServiceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.tv1);
        cnm = (Button)findViewById(R.id.cnm);
        cnm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new GetMsgRunnable()).start();
            }
        });
    }

    public String  addUserTest() {
        String str = "";
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(String.valueOf(i));
            user.setName("User"+i);
            user.setType(0);
            user.setGender(i%2);
            user.setIdCode("IDCode: "+i);
            user.setPlateNum("PlateNum: "+i);
            user.setCarType("CarType: "+i);
            user.setGeoX("0");
            user.setGeoY("0");
            Boolean b = userService.addUser(user);
            str += b+"|";
        }
        return str;
    }

    public double contactTest() {
        Contact contact = new Contact();
        contact.setSubjectId("2");
        contact.setObjectId("6");
        contact.setGroupType(0);
        Boolean b = contactService.addContact(contact);
        Contact c = contactService.getContact("7", "2");
        contactService.removeContact("7", "2");
        return c.getIntimacy();
    }
    public String resourceTest() {
        Resource resource = new Resource();
        resource.setName("papap");
        resource.setType(0);
        resource.setPossessorId("3");
        resourceService.addResource(resource);
        List<Resource> rs = resourceService.getResourcesFrom("3");
        Integer ridid=0;
        for (Resource r :
                rs) {
            if (r.getName().equals("blabla36")) {
                resourceService.removeResource(r.getId());
            }
            if (r.getName().equals("papap")){
                ridid = r.getId();
            }
        }
        return ridid.toString();
    }
    public String userTest() {
        User user = new User();
        user.setId("a01");
        user.setName("Android");
        user.setType(0);
        user.setCarType("BMW");
        user.setGender(0);
        user.setIdCode("abc123");
        user.setPlateNum("NBNBNBNBNB");
        user.setGeoX("0");
        user.setGeoY("0");
        userService.addUser(user);
        userService.changeLocation("a01", "126.574165", "43.878269");
        user.setIdCode("abc456");
        userService.updateUserInfo(user);
        User user1 = userService.getUser(user.getId());
        String str = user1.getIdCode() +" "+ user1.getGeoX()+" "+user1.getGeoY();
        return str;
    }
    public String aroundTest() {
        List<User> users = userService.getUserAround("7", 10000);
        String str = "";
        for (User u :
                users) {
            str += u.getId()+" ";
        }
        str += "|";
        List<User> users2 = userService.getHeplerAround("7");
        for (User u :
                users2) {
            str += u.getId()+" ";
        }
        return str;
    }
}
