package org.bugjlu.ots_trafficaid_client.remote.remote_service;

import org.bugjlu.ots_trafficaid_client.remote.remote_assist.RestHandler;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.Resource;
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

public class ResourceServiceImpl implements ResourceService {
    @Override
    public List<Resource> getResourcesFrom(String id) {
        Map<String, String> p = new TreeMap<>();
        p.put("id", id);
        String url = RestHandler.getURL("resource", "getfrom", p);
        ParameterizedTypeReference<List<Resource>> typeRef = new ParameterizedTypeReference<List<Resource>>(){};
        ResponseEntity<List<Resource>> responseEntity = RestHandler.getRestTemplate().exchange(url,
                HttpMethod.GET,
                new HttpEntity<>(null),
                typeRef);
        return responseEntity.getBody();
    }

    @Override
    public Boolean removeResource(Integer rid) {
        Map<String, String> p = new TreeMap<>();
        p.put("rid", rid.toString());
        String url = RestHandler.getURL("resource", "remove", p);
        ParameterizedTypeReference<Boolean> typeRef = new ParameterizedTypeReference<Boolean>(){};
        ResponseEntity<Boolean> responseEntity = RestHandler.getRestTemplate().exchange(url,
                HttpMethod.GET,
                new HttpEntity<>(null),
                typeRef);
        return responseEntity.getBody();
    }

    @Override
    public Boolean addResource(Resource res) {
        String url = RestHandler.getURL("resource", "add", null);
        ParameterizedTypeReference<Boolean> typeRef = new ParameterizedTypeReference<Boolean>(){};
        ResponseEntity<Boolean> responseEntity = RestHandler.getRestTemplate().exchange(url,
                HttpMethod.POST,
                new HttpEntity<>(res),
                typeRef);
        return responseEntity.getBody();
    }
}
