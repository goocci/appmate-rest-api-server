package com.appmate.service.profile;

import com.appmate.model.profile.SkillWanted;
import com.appmate.repository.profile.SkillWantedRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by uujc0207 on 2017. 4. 4..
 */
@Service("skillWantedService")
@Transactional
public class SkillWantedServiceImpl implements SkillWantedService{

    @Autowired
    private SkillWantedRepository skillWantedRepository;

    // 내가 원하는 스킬 GET
    public JSONObject findOne(String user_id){
        SkillWanted skillWanted = skillWantedRepository.findOne(user_id);

        String all_skills_wanted = skillWanted.getSkills_wanted();

        if(all_skills_wanted.equals("등록된 원하는 스킬이 없습니다.") || all_skills_wanted.equals("[]")){
            // 등록된 원하는 스킬 없음
            String[] empty_arr = new String[0];
            JSONObject mySkillsWantedJsonObj = new JSONObject();
            mySkillsWantedJsonObj.put("user_id", user_id);
            mySkillsWantedJsonObj.put("skills_wanted", empty_arr);

            return mySkillsWantedJsonObj;

        } else {
            // 등록된 원하는 스킬 있음
            String all_skills_wanted2 = all_skills_wanted.substring(1,all_skills_wanted.length()-1); // [ ] 제거
            String all_skills_wanted3 = all_skills_wanted2.replaceAll(" ",""); // 공백 제거
            String[] skills_wanted_arr = all_skills_wanted3.split(","); // 배열 변환

            JSONObject mySkillsWantedJsonObj = new JSONObject();
            mySkillsWantedJsonObj.put("user_id", user_id);
            mySkillsWantedJsonObj.put("skills_wanted", skills_wanted_arr);

            return mySkillsWantedJsonObj;
        }
    }
}
