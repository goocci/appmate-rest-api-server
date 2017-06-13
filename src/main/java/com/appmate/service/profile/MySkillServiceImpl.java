package com.appmate.service.profile;

import com.appmate.model.profile.MySkill;
import com.appmate.repository.profile.MySkillRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by uujc0207 on 2017. 4. 4..
 */
@Service("mySkillService")
@Transactional
public class MySkillServiceImpl implements MySkillService{

    @Autowired
    private MySkillRepository mySkillRepository;

    // 나의 스킬 GET
    public JSONObject findOne(String user_id){
        MySkill mySkill = mySkillRepository.findOne(user_id);

        String all_skills = mySkill.getMy_skills();

        if(all_skills.equals("등록된 나의 스킬이 없습니다.") || all_skills.equals("[]")){
            // 등록된 스킬 없음
            String[] empty_arr = new String[0];
            JSONObject mySkillsJsonObj = new JSONObject();
            mySkillsJsonObj.put("user_id", user_id);
            mySkillsJsonObj.put("my_skills", empty_arr);

            return mySkillsJsonObj;

        } else {
            // 등록된 스킬 있음
            String all_skills2 = all_skills.substring(1,all_skills.length()-1); // [ ] 제거
            String all_skills3 = all_skills2.replaceAll(" ",""); // 공백 제거
            String[] skills_arr = all_skills3.split(","); // 배열 변환

            JSONObject mySkillsJsonObj = new JSONObject();
            mySkillsJsonObj.put("user_id", user_id);
            mySkillsJsonObj.put("my_skills", skills_arr);

            return mySkillsJsonObj;
        }
    }
}
