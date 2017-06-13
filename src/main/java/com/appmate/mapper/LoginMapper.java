package com.appmate.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by uujc0207 on 2017. 3. 18..
 */
@Mapper
public interface LoginMapper {

    @Insert("INSERT INTO user (user_id, device_token) VALUES (#{0}, '')")
    public void createUser(String id);

    @Insert("INSERT INTO about_me (user_id, career, greetings, homepage, nick_name) VALUES (#{0}, #{1}, '등록된 인삿말이 없습니다.', '등록된 홈페이지가 없습니다.', #{2})")
    public void createAboutMe(String client_id, String career, String nick_name);

    @Insert("INSERT INTO image (user_id, image) VALUES (#{0}, #{1})")
    public void createImage(String client_id, String image);

    @Insert("INSERT INTO my_favorite_app (user_id, my_favorite_apps) VALUES (#{0}, '등록된 앱이 없습니다.')")
    public void createMyFavoriteApp(String id);

    @Insert("INSERT INTO my_skill (user_id, my_skills) VALUES (#{0}, '등록된 나의 스킬이 없습니다.')")
    public void createMySkill(String id);

    @Insert("INSERT INTO my_favorite_zone (user_id, latitude, longitude, zone_num, address) VALUES (#{0}, '', '', #{1}, '')")
    public void createMyFavoriteZone(String id, int zone_num);

    @Insert("INSERT INTO current_location (user_id, latitude, longitude) VALUES (#{0}, '', '')")
    public void createCurrentLocation(String id);

    @Insert("INSERT INTO skill_wanted (user_id, skills_wanted) VALUES (#{0}, '등록된 원하는 스킬이 없습니다.')")
    public void createSkillWanted(String id);

}
