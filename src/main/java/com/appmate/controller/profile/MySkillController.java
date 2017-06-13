package com.appmate.controller.profile;

import com.appmate.model.profile.MySkill;
import com.appmate.repository.profile.MySkillRepository;
import com.appmate.service.profile.MySkillService;
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
public class MySkillController {

    @Autowired
    private MySkillRepository mySkillRepository;

    @Autowired
    private MySkillService mySkillService;

    // GET - My Skills
    @RequestMapping(value = "/my_skills/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<String> getMySkills(@PathVariable String user_id) {

        JSONObject mySkills = mySkillService.findOne(user_id);

        if (mySkills == null) {
            System.out.println("User with id " + user_id + " not found");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(mySkills.toString(), HttpStatus.OK);
    }

    // PATCH - My Skills
    @RequestMapping(value = "/my_skills/{user_id}", method = RequestMethod.PATCH)
    public ResponseEntity<MySkill> updateMySkills(@PathVariable("user_id") String user_id, @RequestBody MySkillController my_skills) {

        MySkill currentMySkill = mySkillRepository.findOne(user_id);

        if (currentMySkill == null) {
            System.out.println("User with id " + user_id + " not found");
            return new ResponseEntity<MySkill>(HttpStatus.NOT_FOUND);
        }

        if(my_skills.getMy_skills() != null){
            currentMySkill.setMy_skills(Arrays.toString(my_skills.getMy_skills()));
        }

        mySkillRepository.save(currentMySkill);

        return new ResponseEntity<MySkill>(currentMySkill, HttpStatus.OK);
    }

    private String[] my_skills;

    public String[] getMy_skills() {
        return my_skills;
    }
}
