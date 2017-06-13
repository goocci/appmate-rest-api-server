package com.appmate.service.location;

import com.appmate.model.location.MyFavoriteZone;
import com.appmate.model.profile.MySkill;
import com.appmate.repository.location.MyFavoriteZoneRepository;
import com.appmate.repository.profile.MySkillRepository;
import com.appmate.service.profile.ImageService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uujc0207 on 2017. 3. 25..
 */
@Service("myFavoriteZoneService")
@Transactional
public class MyFavoriteZoneServiceImpl implements MyFavoriteZoneService{

    @Autowired
    private MyFavoriteZoneRepository myFavoriteZoneRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private MySkillRepository mySkillRepository;

    // 나의 즐겨찾는 장소들 중에 특정 번호의 장소 찾기 (PATCH를 위한)
    public MyFavoriteZone findZone(String user_id, int zone_num){

        List<MyFavoriteZone> myFavoriteZones;
        myFavoriteZones = myFavoriteZoneRepository.findAll();

        for(MyFavoriteZone myFavoriteZone : myFavoriteZones){
            if(myFavoriteZone.getUser_id().equals(user_id) && myFavoriteZone.getZone_num() == zone_num){
                return myFavoriteZone;
            }
        }

        return null;
    }

    // 나의 즐겨찾는 장소들 GET
    public List<MyFavoriteZone> findMyZones(String user_id) {

        List<MyFavoriteZone> myFavoriteZones;
        List<MyFavoriteZone> MyZonesList = new ArrayList<MyFavoriteZone>();

        myFavoriteZones = myFavoriteZoneRepository.findAll();

        for(MyFavoriteZone myFavoriteZone : myFavoriteZones){
            if(myFavoriteZone.getUser_id().equals(user_id)){
                MyZonesList.add(myFavoriteZone);
            }
        }

        return MyZonesList;
    }

    // 모든 사용자의 즐겨찾는 장소들 GET (+이미지, +포지션컬러)
    public JSONArray findAll() {

        List<MyFavoriteZone> myFavoriteZones;
        JSONArray zones_arr = new JSONArray();
        JSONObject zones_obj;

        myFavoriteZones = myFavoriteZoneRepository.findAll();

        for(MyFavoriteZone myFavoriteZone : myFavoriteZones){
            zones_obj = new JSONObject();
            zones_obj.put("id", myFavoriteZone.getId());
            zones_obj.put("user_id", myFavoriteZone.getUser_id());
            zones_obj.put("latitude", myFavoriteZone.getLatitude());
            zones_obj.put("longitude", myFavoriteZone.getLongitude());
            zones_obj.put("zone_num", myFavoriteZone.getZone_num());
            zones_obj.put("address", myFavoriteZone.getAddress());
            zones_obj.put("image", imageService.getUserImageURL(myFavoriteZone.getUser_id()).getImage());

            MySkill mySkill = mySkillRepository.findOne(myFavoriteZone.getUser_id());
            String all_skills = mySkill.getMy_skills();
            String[] skills_arr;
            String first_skill;
            String color = null;

            if(all_skills.equals("등록된 나의 스킬이 없습니다.") || all_skills.equals("[]")){
                color = "none";
            } else {
                String all_skills2 = all_skills.substring(1,all_skills.length()-1); // [ ] 제거
                String all_skills3 = all_skills2.replaceAll(" ",""); // 공백 제거
                skills_arr = all_skills3.split(","); // 배열 변환
                first_skill = skills_arr[0];

                for(int i = 0 ; i < dev_arr.length ; i++){
                    if(dev_arr[i].equals(first_skill)){
                        color = "dev";
                    }
                }
                for(int i = 0 ; i < design_arr.length ; i++){
                    if(design_arr[i].equals(first_skill)){
                        color = "design";
                    }
                }
                for(int i = 0 ; i < plan_arr.length ; i++){
                    if(plan_arr[i].equals(first_skill)){
                        color = "plan";
                    }
                }
            }
            zones_obj.put("color", color);

            zones_arr.put(zones_obj);
        }

        return zones_arr;
    }

    // 포지션 리스트
    String[] dev_arr = {"iOS", "안드로이드", "웹프론트", "백엔드", "게임개발"};
    String[] design_arr = {"모바일디자인", "웹디자인", "게임디자인"};
    String[] plan_arr = {"모바일기획", "웹기획", "게임기획", "인생기획"};

}
