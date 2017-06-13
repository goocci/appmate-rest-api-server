package com.appmate.controller.profile;

import com.appmate.model.profile.MyFavoriteApp;
import com.appmate.repository.profile.MyFavoriteAppRepository;
import com.appmate.service.profile.MyFavoriteAppService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * Created by uujc0207 on 2017. 3. 20..
 */
@RestController
@RequestMapping("/profiles")
public class MyFavoriteAppController {

    @Autowired
    private MyFavoriteAppRepository myFavoriteAppRepository;

    @Autowired
    private MyFavoriteAppService myFavoriteAppService;

    // GET - My Favorite Apps
    @RequestMapping(value = "/my_favorite_apps/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<String> getMyFavoriteApps(@PathVariable String user_id) {

        // 유저 ID 확인
        MyFavoriteApp check_id = myFavoriteAppRepository.findOne(user_id);
        if(check_id == null){
            System.out.println("User with id " + user_id + " not found");
            return new ResponseEntity<String>("User with id " + user_id + " not found", HttpStatus.OK);
        }

        JSONObject myFavoriteApps = myFavoriteAppService.findOne(user_id);

        return new ResponseEntity<String>(myFavoriteApps.toString(), HttpStatus.OK);
    }

    // PATCH - My Favorite Apps
    @RequestMapping(value = "/my_favorite_apps/{user_id}", method = RequestMethod.PATCH)
    public ResponseEntity<MyFavoriteApp> updateMyFavoriteApps(@PathVariable("user_id") String user_id, @RequestBody MyFavoriteAppController my_favorite_apps) {

        MyFavoriteApp currentMyFavoriteApp = myFavoriteAppRepository.findOne(user_id);

        if (currentMyFavoriteApp == null) {
            System.out.println("User with id " + user_id + " not found");
            return new ResponseEntity<MyFavoriteApp>(HttpStatus.NOT_FOUND);
        }

        if(my_favorite_apps.getMy_favorite_apps() != null){
            currentMyFavoriteApp.setMy_favorite_apps(Arrays.toString(my_favorite_apps.getMy_favorite_apps()));
        }

        myFavoriteAppRepository.save(currentMyFavoriteApp);

        return new ResponseEntity<MyFavoriteApp>(currentMyFavoriteApp, HttpStatus.OK);
    }

    private String[] my_favorite_apps;

    public String[] getMy_favorite_apps() {
        return my_favorite_apps;
    }
}