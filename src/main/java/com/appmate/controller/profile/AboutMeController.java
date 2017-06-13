package com.appmate.controller.profile;

import com.appmate.model.profile.AboutMe;
import com.appmate.repository.profile.AboutMeRepository;
import com.appmate.service.profile.AboutMeService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * Created by uujc0207 on 2017. 3. 20..
 */
@RestController
@RequestMapping("/profiles")
public class AboutMeController {

    @Autowired
    private AboutMeRepository aboutMeRepository;

    @Autowired
    private AboutMeService aboutMeService;

    // GET - My About Me
    @RequestMapping(value = "/about_me/{user_id}", method = RequestMethod.GET)
    public String getAboutMe(@PathVariable String user_id) {

        AboutMe aboutMe_check_user_id = aboutMeRepository.findOne(user_id);

        if (aboutMe_check_user_id == null) {
            System.out.println("User with id " + user_id + " not found");
            return "User with id " + user_id + " not found";
        }

        JSONObject aboutMe = aboutMeService.findOne(user_id);

        return aboutMe.toString();
    }

    // PATCH - My About Me
    @RequestMapping(value = "/about_me/{user_id}", method = RequestMethod.PATCH)
    public ResponseEntity<AboutMe> updateAboutMe(@PathVariable("user_id") String user_id, @RequestBody AboutMeController aboutMe) {

        AboutMe currentAboutMe = aboutMeRepository.findOne(user_id);

        if (currentAboutMe == null) {
            System.out.println("User with id " + user_id + " not found");
            return new ResponseEntity<AboutMe>(HttpStatus.NOT_FOUND);
        }

        if(aboutMe.getNick_name() != null){
            currentAboutMe.setNick_name(aboutMe.getNick_name());
        }
        if(aboutMe.getGreetings() != null){
            currentAboutMe.setGreetings(aboutMe.getGreetings());
        }
        if(aboutMe.getHomepage() != null){
            currentAboutMe.setHomepage(aboutMe.getHomepage());
        }
        if(aboutMe.getCareer() != null){
            if(aboutMe.getCareer().length == 0) {
                currentAboutMe.setCareer("등록된 경력이 없습니다.");
            } else {
                currentAboutMe.setCareer(Arrays.toString(aboutMe.getCareer()));
            }
        }

        aboutMeRepository.save(currentAboutMe);

        return new ResponseEntity<AboutMe>(currentAboutMe, HttpStatus.OK);
    }

    private String nick_name;
    private String greetings;
    private String[] career;
    private String homepage;

    public String getNick_name() {
        return nick_name;
    }

    public String getGreetings() {
        return greetings;
    }

    public String[] getCareer() {
        return career;
    }

    public String getHomepage() {
        return homepage;
    }
}
