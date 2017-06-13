package com.appmate.controller.chatting;

import com.appmate.model.login.User;
import com.appmate.repository.login.UserRepository;
import com.appmate.repository.profile.AboutMeRepository;
import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by uujc0207 on 2017. 4. 12..
 */
@RestController
public class NotificationController extends PushNotificationPayload{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AboutMeRepository aboutMeRepository;

    // POST - PUSH 요청
    @RequestMapping(value = "/message/send/{user_id}", method = RequestMethod.POST)
    public ResponseEntity<String> requestPush(HttpServletRequest request,
                                              @PathVariable("user_id") String i_user_id,
                                              @RequestBody com.appmate.model.chatting.Push push) throws CommunicationException, KeystoreException {

        String nick_name = aboutMeRepository.findOne(i_user_id).getNick_name();
        String device_token = userRepository.findOne(push.getOther_user_id()).getDevice_token();
        String message = push.getMsg();

        // 기기 토큰 없음 처리
        if(device_token.equals("")){
            return new ResponseEntity<String>("Device Token Not Found", HttpStatus.NOT_FOUND);
        }

        PushNotificationPayload payload = PushNotificationPayload.complex();
        payload.addCustomAlertTitle(nick_name);
        payload.addCustomAlertBody(message);
        payload.addCustomDictionary("user_id", i_user_id);
        PushedNotifications notifications = Push.payload(payload, "dev.p12", "1234", false, device_token);

        return new ResponseEntity<String>("Success PUSH Request", HttpStatus.OK);
    }

    // PATCH - 사용자 기기 토큰 등록
    @RequestMapping("/device_token/add/{user_id}")
    public ResponseEntity<User> addDeviceToken(@PathVariable("user_id") String user_id, @RequestBody User user) {

        User currentUser = userRepository.findOne(user_id);

        if (currentUser == null) {
            System.out.println("User with id " + user_id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        if(user.getDevice_token() != null){
            currentUser.setDevice_token(user.getDevice_token());
        }

        userRepository.save(currentUser);

        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

}

