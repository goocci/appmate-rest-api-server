package com.appmate.controller.profile;

import com.appmate.model.profile.ILikeYou;
import com.appmate.repository.profile.ILikeYouRepository;
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
public class ILikeYouController {

    @Autowired
    private ILikeYouRepository iLikeYouRepository;

    @Autowired
    private ILikeYouService iLikeYouService;

    // POST - 즐겨찾기 추가
    @RequestMapping(value = "/i_like_you/add", method=RequestMethod.POST)
    public HttpStatus createILikeYou(@RequestBody ILikeYou iLikeYou) {

        if(iLikeYou.getI_user_id() == null || iLikeYou.getYou_user_id() == null){
            System.out.println("I_User_Id And You_User_Id Must be NOT NULL");
        } else {
            iLikeYouRepository.save(iLikeYou);
            return HttpStatus.OK;
        }

        return HttpStatus.BAD_REQUEST;
    }

    // GET - 즐겨찾기 확인
    @RequestMapping(value = "/i_like_you/check/{i_user_id}/{you_user_id}", method = RequestMethod.GET)
    public String  checkILiKeYou(@PathVariable("i_user_id") String i_user_id,
                                    @PathVariable("you_user_id") String you_user_id) {

        JSONObject check_obj = iLikeYouService.checkLikeYou(i_user_id, you_user_id);

        return check_obj.toString();
    }

    // GET - 내가 즐겨찾기한 사람들
    @RequestMapping(value = "/i_like_you/my_favorite_people/{i_user_id}", method = RequestMethod.GET)
    public List<ILikeYou> getLikeYou(@PathVariable("i_user_id") String i_user_id) {

        List<ILikeYou> likeYouList = iLikeYouService.findLikeYou(i_user_id);

        if (i_user_id == null) {
            System.out.println("User with id " + i_user_id + " not found");
            return null;
        }

        return likeYouList;
    }

    // GET - 나를 즐겨찾기한 사람들의 개수
    @RequestMapping(value = "/i_like_you/people_like_me/count/{i_user_id}", method = RequestMethod.GET)
    public String getLikeCount(@PathVariable("i_user_id") String i_user_id) {

        JSONObject likeCount = iLikeYouService.getLikeCount(i_user_id);

        return likeCount.toString();
    }

    // DELETE - 즐겨찾기 해제
    @RequestMapping(value = "/i_like_you/delete/{i_user_id}/{you_user_id}", method = RequestMethod.DELETE)
    public ResponseEntity<ILikeYou> deleteILikeYou(@PathVariable("i_user_id") String i_user_id,
                                                   @PathVariable("you_user_id") String you_user_id){

        ILikeYou iLikeYou = iLikeYouService.findILikeYou(i_user_id, you_user_id);

        if (iLikeYou == null) {
            System.out.println("Unable to delete. User with id " + i_user_id + " not found");
            return new ResponseEntity<ILikeYou>(HttpStatus.NOT_FOUND);
        }

        iLikeYouService.deleteILikeYou(i_user_id, you_user_id);

        return new ResponseEntity<ILikeYou>(HttpStatus.NO_CONTENT);
    }
}
