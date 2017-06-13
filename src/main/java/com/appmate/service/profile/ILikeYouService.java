package com.appmate.service.profile;

import com.appmate.model.profile.ILikeYou;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by uujc0207 on 2017. 3. 24..
 */
public interface ILikeYouService {

    List<ILikeYou> findLikeYou(String i_user_id);

    JSONObject checkLikeYou(String i_user_id, String you_user_id);

    ILikeYou findILikeYou(String i_user_id, String you_user_id);

    JSONObject getLikeCount(String i_user_id);

    void deleteILikeYou(String i_user_id, String you_user_id);

}
