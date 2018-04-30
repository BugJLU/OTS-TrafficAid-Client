package org.bugjlu.ots_trafficaid_client.remote.remote_service;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.*;

/**
 * Created by mac on 2018/4/29.
 */

public interface ContactService {
    public Boolean addContact(Contact contact);
    public Boolean removeContact(String subjId, String objId);
    public Contact getContact(String subjId, String objId);
}
