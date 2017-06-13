package com.appmate.controller.recommended.list;

import com.appmate.model.login.User;
import com.appmate.model.recommended.list.RecommendedList;
import com.appmate.repository.login.UserRepository;
import com.appmate.service.recommended.list.RecommendedListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by uujc0207 on 2017. 3. 29..
 */
@RestController
public class RecommendedListController {

    @Autowired
    private RecommendedListService recommendedListService;

    @Autowired
    private UserRepository userRepository;

    // GET - 나를 위한 추천리스트
    @RequestMapping(value = "/recommended_list/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<List<RecommendedList>> getAboutMe(@PathVariable String user_id) {

        try{
            User check_user_id = userRepository.findOne(user_id);
            if(check_user_id.getUser_id().isEmpty());
        } catch (NullPointerException e){
            System.out.println("User with id " + user_id + " not found");
            return new ResponseEntity<List<RecommendedList>>(HttpStatus.NOT_FOUND);
        }

        List<RecommendedList> recommendedList = recommendedListService.matchPeople(user_id);

        return new ResponseEntity<List<RecommendedList>>(recommendedList, HttpStatus.OK);
    }
}
