package com.appmate.service.profile;

import org.json.JSONObject;

/**
 * Created by uujc0207 on 2017. 4. 6..
 */
public interface AllUserInfoService {

    public JSONObject getUserInfoAll(String user_id);

    public JSONObject getPositionColor(String user_id);
}
