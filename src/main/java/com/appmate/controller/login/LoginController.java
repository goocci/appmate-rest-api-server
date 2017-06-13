package com.appmate.controller.login;

import com.appmate.service.login.LoginService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public String facebook(HttpServletRequest request, @RequestParam(value = "token") String token){

        // GET FACEBOOK INFO
        System.out.println("페이스북 토큰 : "+request.getParameter("token"));
        String facebook_token = request.getParameter("token");

        JSONObject facebook_login_return = new JSONObject();
        try{
            loginService.facebookLogin(facebook_token);
            System.out.println("OK");
            facebook_login_return.put("result", 1);
            return facebook_login_return.toString();
        } catch (Exception e) {
            System.out.println("FAIL");
            e.printStackTrace();
            facebook_login_return.put("result", 0);
            return facebook_login_return.toString();
        }
    }
}