package org.bugjlu.ots_trafficaid_client.remote.remote_service;

import org.bugjlu.ots_trafficaid_client.remote.remote_assist.RestHandler;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.Contact;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by mac on 2018/4/29.
 */

public class ContactServiceImpl implements ContactService {

    @Override
    public Boolean addContact(Contact contact) {
        String url = RestHandler.getURL("contact", "add", null);
        ParameterizedTypeReference<Boolean> typeRef = new ParameterizedTypeReference<Boolean>(){};
        ResponseEntity<Boolean> responseEntity = RestHandler.getRestTemplate().exchange(url,
                HttpMethod.POST,
                new HttpEntity<>(contact),
                typeRef);
        return responseEntity.getBody();
    }

    @Override
    public Boolean removeContact(String subjId, String objId) {
        Map<String, String> p = new TreeMap<>();
        p.put("subjId", subjId);
        p.put("objId", objId);
        String url = RestHandler.getURL("contact", "remove", p);
        ParameterizedTypeReference<Boolean> typeRef = new ParameterizedTypeReference<Boolean>(){};
        ResponseEntity<Boolean> responseEntity = RestHandler.getRestTemplate().exchange(url,
                HttpMethod.GET,
                new HttpEntity<>(null),
                typeRef);
        return responseEntity.getBody();
    }

    @Override
    public Contact getContact(String subjId, String objId) {
        Map<String, String> p = new TreeMap<>();
        p.put("subjId", subjId);
        p.put("objId", objId);
        String url = RestHandler.getURL("contact", "getinfo", p);
        ParameterizedTypeReference<Contact> typeRef = new ParameterizedTypeReference<Contact>(){};
        ResponseEntity<Contact> responseEntity = RestHandler.getRestTemplate().exchange(url,
                HttpMethod.GET,
                new HttpEntity<>(null),
                typeRef);
        return responseEntity.getBody();
    }
}
