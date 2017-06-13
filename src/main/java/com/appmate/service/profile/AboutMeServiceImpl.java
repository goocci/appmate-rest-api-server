package com.appmate.service.profile;

import com.appmate.model.profile.AboutMe;
import com.appmate.repository.profile.AboutMeRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Created by uujc0207 on 2017. 3. 31..
 */
@Service("aboutMeService")
@Transactional
public class AboutMeServiceImpl implements AboutMeService{

    @Autowired
    private AboutMeRepository aboutMeRepository;

    // 나의 기본정보 GET
    public JSONObject findOne(String user_id){

        AboutMe aboutMe = aboutMeRepository.findOne(user_id);

        String all_career = aboutMe.getCareer();

        if(all_career.equals("등록된 경력이 없습니다.")){
            // 등록된 경력 없음
            String[] none_career_arr = {"등록된 경력이 없습니다."};

            JSONObject myAboutMeJsonObj = new JSONObject();
            myAboutMeJsonObj.put("user_id", user_id);
            myAboutMeJsonObj.put("nick_name", aboutMe.getNick_name());
            myAboutMeJsonObj.put("greetings", aboutMe.getGreetings());
            myAboutMeJsonObj.put("career", none_career_arr);
            myAboutMeJsonObj.put("homepage", aboutMe.getHomepage());

            return myAboutMeJsonObj;

        } else {
            // 등록된 경력 있음
            String all_career2 = all_career.substring(1,all_career.length()-1); // [ ] 제거
            String[] career_arr = all_career2.split(",");
            // 요소 앞 공백 제거
            for(int i = 0 ; i < career_arr.length ; i++){
                String trim = career_arr[i].trim();
                career_arr[i] = trim;
            }

            JSONObject myAboutMeJsonObj = new JSONObject();
            myAboutMeJsonObj.put("user_id", user_id);
            myAboutMeJsonObj.put("nick_name", aboutMe.getNick_name());
            myAboutMeJsonObj.put("greetings", aboutMe.getGreetings());
            myAboutMeJsonObj.put("career", career_arr);
            myAboutMeJsonObj.put("homepage", aboutMe.getHomepage());

            return myAboutMeJsonObj;
        }

    }
}
