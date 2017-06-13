package com.appmate.service.profile;

import com.appmate.model.profile.AboutMe;
import org.json.JSONObject;

/**
 * Created by uujc0207 on 2017. 3. 31..
 */
public interface AboutMeService {

    public JSONObject findOne(String user_id);
}
