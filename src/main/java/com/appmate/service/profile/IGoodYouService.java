package com.appmate.service.profile;

import com.appmate.model.profile.IGoodYou;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by uujc0207 on 2017. 3. 24..
 */
public interface IGoodYouService {

    JSONObject checkGoodYou(String i_user_id, String you_user_id);

    List<IGoodYou> findGoodMe(String i_user_id);

    JSONObject getGoodCount(String i_user_id);

    IGoodYou findIGoodYou(String i_user_id, String you_user_id);

    void deleteIGoodYou(String i_user_id, String you_user_id);

}
