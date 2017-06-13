package com.appmate.model.profile;

import javax.persistence.*;

/**
 * Created by uujc0207 on 2017. 3. 20..
 */
@Entity
@Table(name = "MY_FAVORITE_APP")
public class MyFavoriteApp {

    @Id
    private String user_id;
    private String my_favorite_apps;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMy_favorite_apps() {
        return my_favorite_apps;
    }

    public void setMy_favorite_apps(String my_favorite_apps) {
        this.my_favorite_apps = my_favorite_apps;
    }
}