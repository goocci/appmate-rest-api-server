package com.appmate.model.login;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by uujc0207 on 2017. 3. 24..
 */
@Entity
@Table(name = "USER")
public class User {

    @Id
    private String user_id;

    private String device_token;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }
}
