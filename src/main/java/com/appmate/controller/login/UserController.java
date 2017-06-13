package com.appmate.controller.login;

import com.appmate.model.login.User;
import com.appmate.repository.login.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by uujc0207 on 2017. 3. 26..
 */
@RestController
@RequestMapping("/login")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // GET - Check User
    @RequestMapping(value = "/users/check/{user_id}", method = RequestMethod.GET)
    public int checkUser(@PathVariable String user_id) {

        User user = userRepository.findOne(user_id);

        try {

            if(user.getUser_id().isEmpty()){

            }

        } catch (NullPointerException e){
            return 0;
        }

        return 1;
    }
}
