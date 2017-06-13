package com.appmate.service.profile;

import com.appmate.model.profile.MyFavoriteApp;
import com.appmate.repository.profile.MyFavoriteAppRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by uujc0207 on 2017. 4. 4..
 */
@Service("myFavoriteAppService")
@Transactional
public class MyFavoriteAppServiceImpl implements MyFavoriteAppService{

    @Autowired
    private MyFavoriteAppRepository myFavoriteAppRepository;

    // 내가 좋아하는 앱 GET
    public JSONObject findOne(String user_id){
        MyFavoriteApp myFavoriteApp = myFavoriteAppRepository.findOne(user_id);

        String all_apps = myFavoriteApp.getMy_favorite_apps();

        if(all_apps.equals("등록된 앱이 없습니다.") || all_apps.equals("[]")){
            // 등록된 앱 없음
            String[] empty_arr = new String[0];
            JSONObject myFavoriteAppsJsonObj = new JSONObject();
            myFavoriteAppsJsonObj.put("user_id", user_id);
            myFavoriteAppsJsonObj.put("my_favorite_apps", empty_arr);

            return myFavoriteAppsJsonObj;

        } else {
            // 등록된 앱 있음
            String all_apps2 = all_apps.substring(1,all_apps.length()-1); // [ ] 제거
            String all_app3 = all_apps2.replaceAll(" ",""); // 공백 제거
            String[] apps_arr = all_app3.split(","); // 배열 변환

            JSONObject myFavoriteAppsJsonObj = new JSONObject();
            myFavoriteAppsJsonObj.put("user_id", user_id);
            myFavoriteAppsJsonObj.put("my_favorite_apps", apps_arr);

            return myFavoriteAppsJsonObj;
        }
    }
}
