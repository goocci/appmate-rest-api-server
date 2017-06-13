package com.appmate.controller.profile;

import com.appmate.model.profile.SkillWanted;
import com.appmate.repository.profile.SkillWantedRepository;
import com.appmate.service.profile.SkillWantedService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * Created by uujc0207 on 2017. 3. 29..
 */
@RestController
@RequestMapping("/profiles")
public class SkillWantedController {

    @Autowired
    private SkillWantedRepository skillWantedRepository;

    @Autowired
    private SkillWantedService skillWantedService;

    // GET - Skills I Wanted
    @RequestMapping(value = "/skills_wanted/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<String> getSkillsWanted(@PathVariable String user_id) {

        JSONObject skillsWanted = skillWantedService.findOne(user_id);

        if (skillsWanted == null) {
            System.out.println("User with id " + user_id + " not found");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(skillsWanted.toString(), HttpStatus.OK);
    }

    // PATCH - Skills I Wanted
    @RequestMapping(value = "/skills_wanted/{user_id}", method = RequestMethod.PATCH)
    public ResponseEntity<SkillWanted> updateSkillsWanted(@PathVariable("user_id") String user_id, @RequestBody SkillWantedController skills_wanted) {

        SkillWanted currentSkillWanted = skillWantedRepository.findOne(user_id);

        if (currentSkillWanted == null) {
            System.out.println("User with id " + user_id + " not found");
            return new ResponseEntity<SkillWanted>(HttpStatus.NOT_FOUND);
        }

        if(skills_wanted.getSkills_wanted() != null){
            currentSkillWanted.setSkills_wanted(Arrays.toString(skills_wanted.getSkills_wanted()));
        }

        skillWantedRepository.save(currentSkillWanted);

        return new ResponseEntity<SkillWanted>(currentSkillWanted, HttpStatus.OK);
    }

    private String[] skills_wanted;

    public String[] getSkills_wanted() {
        return skills_wanted;
    }
}
