package org.bugjlu.ots_trafficaid_client.remote.remote_assist;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by mac on 2018/4/29.
 */

public class RestHandler {
    static RestTemplate restTemplate;
//    static String URL = "http://192.168.31.32:8080/";
    static String URL = "http://211.159.147.229:8080/ots_server/";
    static {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }
    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }
    public static String getURL(String category, String request, Map<String, String> parameters) {
        String url = URL+category+"/"+request;
        if (parameters == null || parameters.size() == 0) {
            return url;
        }
        int i = 0;
        for (Map.Entry<String, String> e:
             parameters.entrySet()) {
            if (i == 0) {
                url += "?";
            } else {
                url += "&";
            }
            ++i;
            url += e.getKey()+"="+e.getValue();
        }
        return url;
    }
//    public static Object postFor(String url, Object obj) {
//        ParameterizedTypeReference<obj> typeRef = new ParameterizedTypeReference<obj.class>() {
//        };
//        ResponseEntity<> responseEntity = restTemplate.exchange(url,
//                HttpMethod.POST,
//                new HttpEntity<>(obj),
//                typeRef);
//        List<User> result = responseEntity.getBody();
//        return result;
//    }
}
