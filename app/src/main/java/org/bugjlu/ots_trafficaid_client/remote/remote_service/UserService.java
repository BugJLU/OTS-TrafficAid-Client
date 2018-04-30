package org.bugjlu.ots_trafficaid_client.remote.remote_service;

import org.bugjlu.ots_trafficaid_client.remote.remote_object.User;

import java.util.List;

/**
 * Created by mac on 2018/4/29.
 */

public interface UserService {
    public Boolean addUser(User user);
    public User updateUserInfo(User user);
    public User changeLocation(String id, String gx, String gy);
    public User getUser(String id);
    public List<User> getUserAround(String id, int distance);
    public List<User> getHeplerAround(String id);
}