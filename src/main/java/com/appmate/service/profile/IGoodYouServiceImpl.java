package com.appmate.service.profile;

import com.appmate.model.profile.IGoodYou;
import com.appmate.repository.profile.IGoodYouRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uujc0207 on 2017. 3. 24..
 */
@Service("iGoodYouService")
@Transactional
public class IGoodYouServiceImpl implements IGoodYouService {

    @Autowired
    private IGoodYouRepository iGoodYouRepository;

    // 좋아요 중복 확인
    public JSONObject checkGoodYou(String i_user_id, String you_user_id) {

        List<IGoodYou> iGoodYous;

        iGoodYous = iGoodYouRepository.findAll();

        JSONObject check_obj = new JSONObject();

        for(IGoodYou iGoodYou : iGoodYous){
            if(iGoodYou.getI_user_id().equals(i_user_id) && iGoodYou.getYou_user_id().equals(you_user_id)){
                check_obj.put("check", 1);

                return check_obj;
            }
        }

        check_obj.put("check", 0);

        return check_obj;
    }

    // 나를 좋아요한 사람들 GET
    public List<IGoodYou> findGoodMe(String i_user_id) {

        List<IGoodYou> iGoodYous;
        List<IGoodYou> goodYouList = new ArrayList<IGoodYou>();

        iGoodYous = iGoodYouRepository.findAll();

        for(IGoodYou iGoodYou : iGoodYous){
            if(iGoodYou.getYou_user_id().equals(i_user_id)){
                goodYouList.add(iGoodYou);
            }
        }

        return goodYouList;
    }

    // 나의 좋아요 개수 GET
    public JSONObject getGoodCount(String i_user_id) {

        List<IGoodYou> iGoodYous;
        long count = 0;

        iGoodYous = iGoodYouRepository.findAll();

        for(IGoodYou iGoodYou : iGoodYous){
            if(iGoodYou.getYou_user_id().equals(i_user_id)){
                count++;
            }
        }

        JSONObject count_obj = new JSONObject();
        count_obj.put("count", count);

        return count_obj;
    }

    // 특정 좋아요 찾기
    public IGoodYou findIGoodYou(String i_user_id, String you_user_id){

        List<IGoodYou> iGoodYous;

        iGoodYous = iGoodYouRepository.findAll();

        for(IGoodYou iGoodYou : iGoodYous){
            if(iGoodYou.getI_user_id().equals(i_user_id) && iGoodYou.getYou_user_id().equals(you_user_id)){
                return iGoodYou;
            }
        }

        return null;
    }


    // 좋아요 제거
    public void deleteIGoodYou(String i_user_id, String you_user_id){

        List<IGoodYou> iGoodYous;

        iGoodYous = iGoodYouRepository.findAll();

        for(IGoodYou iGoodYou : iGoodYous){
            if(iGoodYou.getI_user_id().equals(i_user_id) && iGoodYou.getYou_user_id().equals(you_user_id)){
                iGoodYouRepository.delete(iGoodYou);
            }
        }
    }

}
