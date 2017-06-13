package com.appmate.service.profile;

import com.appmate.model.profile.ILikeYou;
import com.appmate.repository.profile.ILikeYouRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uujc0207 on 2017. 3. 24..
 */
@Service("iLikeYouService")
@Transactional
public class ILikeYouServiceImpl implements ILikeYouService {

    @Autowired
    private ILikeYouRepository iLikeYouRepository;

    // 내가 즐겨찾는 사람 GET
    public List<ILikeYou> findLikeYou(String i_user_id) {

        List<ILikeYou> iLikeYous;
        List<ILikeYou> likeYouList = new ArrayList<ILikeYou>();

        iLikeYous = iLikeYouRepository.findAll();

        for(ILikeYou iLikeYou : iLikeYous){
            if(iLikeYou.getI_user_id().equals(i_user_id)){
                likeYouList.add(iLikeYou);
            }
        }

        return likeYouList;
    }

    // 즐겨찾기 중복 확인
    public JSONObject checkLikeYou(String i_user_id, String you_user_id) {

        List<ILikeYou> iLikeYous;

        iLikeYous = iLikeYouRepository.findAll();

        JSONObject check_obj = new JSONObject();

        for(ILikeYou iLikeYou : iLikeYous){
            if(iLikeYou.getI_user_id().equals(i_user_id) && iLikeYou.getYou_user_id().equals(you_user_id)){
                check_obj.put("check", 1);

                return check_obj;
            }
        }

        check_obj.put("check", 0);

        return check_obj;
    }

    // 팔로워 수
    public JSONObject getLikeCount(String i_user_id) {

        List<ILikeYou> iLikeYous;
        long count = 0;

        iLikeYous = iLikeYouRepository.findAll();

        for(ILikeYou iLikeYou : iLikeYous){
            if(iLikeYou.getYou_user_id().equals(i_user_id)){
                count++;
            }
        }

        JSONObject count_obj = new JSONObject();
        count_obj.put("count", count);

        return count_obj;
    }

    // 특정 즐겨찾기 찾기
    public ILikeYou findILikeYou(String i_user_id, String you_user_id){

        List<ILikeYou> iLikeYous;

        iLikeYous = iLikeYouRepository.findAll();

        for(ILikeYou iLikeYou : iLikeYous){
            if(iLikeYou.getI_user_id().equals(i_user_id) && iLikeYou.getYou_user_id().equals(you_user_id)){
                return iLikeYou;
            }
        }

        return null;
    }

    // 즐겨찾기 제거
    public void deleteILikeYou(String i_user_id, String you_user_id){

        List<ILikeYou> iLikeYous;

        iLikeYous = iLikeYouRepository.findAll();

        for(ILikeYou iLikeYou : iLikeYous){
            if(iLikeYou.getI_user_id().equals(i_user_id) && iLikeYou.getYou_user_id().equals(you_user_id)){
                iLikeYouRepository.delete(iLikeYou);
            }
        }
    }

}
