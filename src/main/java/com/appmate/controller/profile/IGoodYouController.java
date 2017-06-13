package com.appmate.controller.profile;

import com.appmate.model.profile.IGoodYou;
import com.appmate.model.profile.ILikeYou;
import com.appmate.repository.profile.IGoodYouRepository;
import com.appmate.repository.profile.ILikeYouRepository;
import com.appmate.service.profile.IGoodYouService;
import com.appmate.service.profile.ILikeYouService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by uujc0207 on 2017. 3. 20..
 */
@RestController
@RequestMapping("/profiles")
public class IGoodYouController {

    @Autowired
    private IGoodYouRepository iGoodYouRepository;

    @Autowired
    private IGoodYouService iGoodYouService;

    // POST - 좋아요 추가
    @RequestMapping(value = "/i_good_you/add", method=RequestMethod.POST)
    public HttpStatus createIGoodYou(@RequestBody IGoodYou iGoodYou) {

        if(iGoodYou.getI_user_id() == null || iGoodYou.getYou_user_id() == null){
            System.out.println("I_User_Id And You_User_Id Must be NOT NULL");
        } else {
            iGoodYouRepository.save(iGoodYou);
            return HttpStatus.OK;
        }

        return HttpStatus.BAD_REQUEST;
    }

    // GET - 좋아요 확인
    @RequestMapping(value = "/i_good_you/check/{i_user_id}/{you_user_id}", method = RequestMethod.GET)
    public String  checkILiKeYou(@PathVariable("i_user_id") String i_user_id,
                                    @PathVariable("you_user_id") String you_user_id) {

        JSONObject check_obj = iGoodYouService.checkGoodYou(i_user_id, you_user_id);

        return check_obj.toString();
    }

    // GET - 나를 좋아요한 사람들
    @RequestMapping(value = "/i_good_you/people_good_me/{i_user_id}", method = RequestMethod.GET)
    public List<IGoodYou> getGoodMe(@PathVariable("i_user_id") String i_user_id) {

        List<IGoodYou> goodMeList = iGoodYouService.findGoodMe(i_user_id);

        if (i_user_id == null) {
            System.out.println("User with id " + i_user_id + " not found");
            return null;
        }

        return goodMeList;
    }

    // GET - 나의 좋아하는 사람들의 개수
    @RequestMapping(value = "/i_good_you/people_good_me/count/{i_user_id}", method = RequestMethod.GET)
    public String getGoodCount(@PathVariable("i_user_id") String i_user_id) {

        JSONObject goodCount = iGoodYouService.getGoodCount(i_user_id);

        return goodCount.toString();
    }

    // DELETE - 좋아요 해제
    @RequestMapping(value = "/i_good_you/delete/{i_user_id}/{you_user_id}", method = RequestMethod.DELETE)
    public ResponseEntity<IGoodYou> deleteIGoodYou(@PathVariable("i_user_id") String i_user_id,
                                                   @PathVariable("you_user_id") String you_user_id){

        IGoodYou iGoodYou = iGoodYouService.findIGoodYou(i_user_id, you_user_id);

        if (iGoodYou == null) {
            System.out.println("Unable to delete. User with id " + i_user_id + " not found");
            return new ResponseEntity<IGoodYou>(HttpStatus.NOT_FOUND);
        }

        iGoodYouService.deleteIGoodYou(i_user_id, you_user_id);

        return new ResponseEntity<IGoodYou>(HttpStatus.NO_CONTENT);
    }
}
