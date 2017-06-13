package com.appmate.model.profile;

import javax.persistence.*;

/**
 * Created by uujc0207 on 2017. 3. 20..
 */
@Entity
@Table(name = "MY_SKILL")
public class MySkill {

    @Id
    private String user_id;
    private String my_skills;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMy_skills() {
        return my_skills;
    }

    public void setMy_skills(String my_skills) {
        this.my_skills = my_skills;
    }

}
