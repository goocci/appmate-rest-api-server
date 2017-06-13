package com.appmate.controller.profile;

import com.appmate.service.profile.AllUserInfoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by uujc0207 on 2017. 4. 6..
 */
@RestController
@RequestMapping("profiles")
public class AllUserInfoController {

    @Autowired
    private AllUserInfoService allUserInfoService;

    // GET - 특정 사용자의 모든 정보
    @RequestMapping(value = "/getUserInfoAll/{user_id}", method = RequestMethod.GET)
    public String getUserInfo(@PathVariable String user_id) {

        JSONObject userInfo = allUserInfoService.getUserInfoAll(user_id);

        return userInfo.toString();
    }

    // GET - 특정 사용자의 포지션 컬러
    @RequestMapping(value = "/position_color/{user_id}", method = RequestMethod.GET)
    public String getPositionColor(@PathVariable String user_id) {

        JSONObject position_color_obj = allUserInfoService.getPositionColor(user_id);

        return position_color_obj.toString();
    }
}
