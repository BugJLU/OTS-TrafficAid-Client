package org.bugjlu.ots_trafficaid_client.remote.remote_service;

import org.bugjlu.ots_trafficaid_client.remote.remote_object.Resource;

import java.util.List;

/**
 * Created by mac on 2018/4/29.
 */

public interface ResourceService {
    public List<Resource> getResourcesFrom(String id);
    public Boolean removeResource(Integer rid);
    public Boolean addResource(Resource res);
}
