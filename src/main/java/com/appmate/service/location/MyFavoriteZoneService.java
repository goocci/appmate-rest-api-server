package com.appmate.service.location;

import com.appmate.model.location.MyFavoriteZone;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by uujc0207 on 2017. 3. 25..
 */
public interface MyFavoriteZoneService {

    MyFavoriteZone findZone(String user_id, int zone_num);

    List<MyFavoriteZone> findMyZones(String user_id);

    JSONArray findAll();
}
