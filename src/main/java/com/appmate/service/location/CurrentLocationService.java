package com.appmate.service.location;

import com.appmate.model.location.CurrentLocation;

import java.util.List;

/**
 * Created by uujc0207 on 2017. 3. 26..
 */
public interface CurrentLocationService {

    public CurrentLocation findMyCurrentLocation(String user_id);

    public List<CurrentLocation> findAllCurrentLocation();

}
