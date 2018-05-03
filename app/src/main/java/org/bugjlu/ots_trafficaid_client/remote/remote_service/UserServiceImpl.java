package org.bugjlu.ots_trafficaid_client.remote.remote_service;

import org.bugjlu.ots_trafficaid_client.remote.remote_assist.RestHandler;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by mac on 2018/4/30.
 */

public class UserServiceImpl implements UserService {
    @Override
    public Boolean addUser(User user) {
        String url = RestHandler.getURL("user", "add", null);
        ParameterizedTypeReference<Boolean> typeRef = new ParameterizedTypeReference<Boolean>(){};
        ResponseEntity<Boolean> responseEntity = RestHandler.getRestTemplate().exchange(url,
                HttpMethod.POST,
                new HttpEntity<>(user),
                typeRef);
        return responseEntity.getBody();
    }

    @Override
    public User updateUserInfo(User user) {
        String url = RestHandler.getURL("user", "updateinfo", null);
        ParameterizedTypeReference<User> typeRef = new ParameterizedTypeReference<User>(){};
        ResponseEntity<User> responseEntity = RestHandler.getRestTemplate().exchange(url,
                HttpMethod.POST,
                new HttpEntity<>(user),
                typeRef);
        return responseEntity.getBody();
    }

    @Override
    public User changeLocation(String id, String gx, String gy) {
        Map<String, String> p = new TreeMap<>();
        p.put("id", id);
        p.put("gx", gx);
        p.put("gy", gy);
        String url = RestHandler.getURL("user", "updategeo", p);
        ParameterizedTypeReference<User> typeRef = new ParameterizedTypeReference<User>(){};
        ResponseEntity<User> responseEntity = RestHandler.getRestTemplate().exchange(url,
                HttpMethod.GET,
                new HttpEntity<>(null),
                typeRef);
        return responseEntity.getBody();
    }

    @Override
    public User getUser(String id) {
        Map<String, String> p = new TreeMap<>();
        p.put("id", id);
        String url = RestHandler.getURL("user", "getinfo", p);
        ParameterizedTypeReference<User> typeRef = new ParameterizedTypeReference<User>(){};
        ResponseEntity<User> responseEntity = RestHandler.getRestTemplate().exchange(url,
                HttpMethod.GET,
                new HttpEntity<>(null),
                typeRef);
        return responseEntity.getBody();
    }

    @Override
    public List<User> getUserAround(String id, int distance) {
        Map<String, String> p = new TreeMap<>();
        p.put("id", id);
        p.put("distance", String.valueOf(distance));
        String url = RestHandler.getURL("user", "getuseraround", p);
        ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>(){};
        ResponseEntity<List<User>> responseEntity = RestHandler.getRestTemplate().exchange(url,
                HttpMethod.GET,
                new HttpEntity<>(null),
                typeRef);
        return responseEntity.getBody();
    }

    @Override
    public List<User> getHeplerAround(String id) {
        Map<String, String> p = new TreeMap<>();
        p.put("id", id);
        String url = RestHandler.getURL("user", "gethelperaround", p);
        ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>(){};
        ResponseEntity<List<User>> responseEntity = RestHandler.getRestTemplate().exchange(url,
                HttpMethod.GET,
                new HttpEntity<>(null),
                typeRef);
        return responseEntity.getBody();
    }
}
