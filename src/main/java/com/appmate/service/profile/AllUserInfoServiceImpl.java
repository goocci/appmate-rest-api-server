package com.appmate.service.profile;

import com.appmate.model.location.MyFavoriteZone;
import com.appmate.model.profile.*;
import com.appmate.repository.profile.AboutMeRepository;
import com.appmate.repository.profile.MyFavoriteAppRepository;
import com.appmate.repository.profile.MySkillRepository;
import com.appmate.repository.profile.SkillWantedRepository;
import com.appmate.service.location.MyFavoriteZoneService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * Created by uujc0207 on 2017. 4. 6..
 */
@Service("allUserInfoService")
@Transactional
public class AllUserInfoServiceImpl implements AllUserInfoService{

    @Autowired ImageService imageService;
    @Autowired AboutMeRepository aboutMeRepository;
    @Autowired ILikeYouService iLikeYouService;
    @Autowired IGoodYouService iGoodYouService;
    @Autowired AboutMeService aboutMeService;
    @Autowired MySkillRepository mySkillRepository;
    @Autowired SkillWantedRepository skillWantedRepository;
    @Autowired MyFavoriteAppRepository myFavoriteAppRepository;
    @Autowired MyFavoriteZoneService myFavoriteZoneService;

    // 나의 모든 프로필 정보 GET
    public JSONObject getUserInfoAll(String user_id){

        JSONObject all_info_obj = new JSONObject();

        // 이미지
        String imageURL = imageService.getUserImageURL(user_id).getImage();
        System.out.println("이미지 : " + imageURL);

        // 닉네임
        String nick_name = aboutMeRepository.findOne(user_id).getNick_name();
        System.out.println("닉네임 : " + nick_name);

        // 팔로워 수
        long followers = Long.parseLong(iLikeYouService.getLikeCount(user_id).get("count").toString());
        System.out.println("팔로워 수 : " + followers);

        // 좋아요 수
        long goods = Long.parseLong(iGoodYouService.getGoodCount(user_id).get("count").toString());
        System.out.println("좋아요 수 : " + goods);

        // 기본 정보(About Me)
        AboutMe aboutMe = aboutMeRepository.findOne(user_id);

        String all_career = aboutMe.getCareer();
        String greetings;
        String[] career;
        String homepage;

        if(all_career.equals("등록된 경력이 없습니다.")){
            String[] none_career_arr = {"등록된 경력이 없습니다."};
            greetings = aboutMe.getGreetings();
            career = none_career_arr;
            homepage = aboutMe.getHomepage();
        } else {
            String all_career2 = all_career.substring(1,all_career.length()-1);
            String[] career_arr = all_career2.split(",");
            for(int i = 0 ; i < career_arr.length ; i++){
                String trim = career_arr[i].trim();
                career_arr[i] = trim;
            }
            greetings = aboutMe.getGreetings();
            career = career_arr;
            homepage = aboutMe.getHomepage();
        }

        System.out.println("인삿말 : " + greetings);
        System.out.println("경력 : " + Arrays.toString(career));
        System.out.println("홈페이지 : " + homepage);

        // 나의 스킬, 포지션 컬러
        MySkill mySkill = mySkillRepository.findOne(user_id);

        String all_skills = mySkill.getMy_skills();
        String[] skills_arr;
        String first_skill;
        String color = null;

        if(all_skills.equals("등록된 나의 스킬이 없습니다.") || all_skills.equals("[]")){
            String[] empty_arr = new String[0];
            skills_arr = empty_arr;
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

        System.out.println("나의 스킬 : " + Arrays.toString(skills_arr));
        System.out.println("포지션 컬러 : " + color);

        // 내가 원하는 스킬
        SkillWanted skillWanted = skillWantedRepository.findOne(user_id);

        String all_skills_wanted = skillWanted.getSkills_wanted();
        String[] skills_wanted_arr;

        if(all_skills_wanted.equals("등록된 원하는 스킬이 없습니다.") || all_skills_wanted.equals("[]")){
            String[] empty_arr = new String[0];
            skills_wanted_arr = empty_arr;
        } else {
            String all_skills_wanted2 = all_skills_wanted.substring(1,all_skills_wanted.length()-1);
            String all_skills_wanted3 = all_skills_wanted2.replaceAll(" ","");
            skills_wanted_arr = all_skills_wanted3.split(",");
        }

        System.out.println("내가 원하는 스킬 : " + Arrays.toString(skills_wanted_arr));

        // 내가 좋아하는 앱
        MyFavoriteApp myFavoriteApp = myFavoriteAppRepository.findOne(user_id);

        String all_apps = myFavoriteApp.getMy_favorite_apps();
        String[] my_favorite_apps_arr;

        if(all_apps.equals("등록된 앱이 없습니다.") || all_apps.equals("[]")){
            String[] empty_arr = new String[0];
            my_favorite_apps_arr = empty_arr;
        } else {
            String all_apps2 = all_apps.substring(1,all_apps.length()-1);
            String all_app3 = all_apps2.replaceAll(" ","");
            my_favorite_apps_arr = all_app3.split(",");
        }

        System.out.println("내가 좋아하는 앱 : " + Arrays.toString(my_favorite_apps_arr));

        // 나를 좋아요한 사람들
        List<IGoodYou> good_me_list = iGoodYouService.findGoodMe(user_id);

        // 내가 즐겨찾는 장소
        List<MyFavoriteZone> my_favorite_zone_list = myFavoriteZoneService.findMyZones(user_id);

        // 내가 즐겨찾기한 사람들
        List<ILikeYou> like_you_list = iLikeYouService.findLikeYou(user_id);

        all_info_obj.put("user_id", user_id);
        all_info_obj.put("image", imageURL);
        all_info_obj.put("nick_name", nick_name);
        all_info_obj.put("follower_count", followers);
        all_info_obj.put("good_me_count", goods);
        all_info_obj.put("greetings", greetings);
        all_info_obj.put("career", career);
        all_info_obj.put("homepage", homepage);
        all_info_obj.put("my_skills", skills_arr);
        all_info_obj.put("color", color);
        all_info_obj.put("skills_wanted", skills_wanted_arr);
        all_info_obj.put("my_favorite_apps", my_favorite_apps_arr);
        all_info_obj.put("people_good_me", good_me_list);
        all_info_obj.put("my_favorite_zones", my_favorite_zone_list);
        all_info_obj.put("my_favorite_people", like_you_list);

        return all_info_obj;
    }

    public JSONObject getPositionColor(String user_id) {

        JSONObject position_color_obj = new JSONObject();

        MySkill mySkill = mySkillRepository.findOne(user_id);

        String all_skills = mySkill.getMy_skills();
        String[] skills_arr;
        String first_skill;

        if(all_skills.equals("등록된 나의 스킬이 없습니다.") || all_skills.equals("[]")){
            position_color_obj.put("color", "none");
        } else {
            String all_skills2 = all_skills.substring(1,all_skills.length()-1); // [ ] 제거
            String all_skills3 = all_skills2.replaceAll(" ",""); // 공백 제거
            skills_arr = all_skills3.split(","); // 배열 변환
            first_skill = skills_arr[0];

            for(int i = 0 ; i < dev_arr.length ; i++){
                if(dev_arr[i].equals(first_skill)){
                    position_color_obj.put("color", "dev");
                }
            }
            for(int i = 0 ; i < design_arr.length ; i++){
                if(design_arr[i].equals(first_skill)){
                    position_color_obj.put("color", "design");
                }
            }
            for(int i = 0 ; i < plan_arr.length ; i++){
                if(plan_arr[i].equals(first_skill)){
                    position_color_obj.put("color", "plan");
                }
            }
        }

        return position_color_obj;
    }

    // 포지션 리스트
    String[] dev_arr = {"iOS", "안드로이드", "웹프론트", "백엔드", "게임개발"};
    String[] design_arr = {"모바일디자인", "웹디자인", "게임디자인"};
    String[] plan_arr = {"모바일기획", "웹기획", "게임기획", "인생기획"};
}
