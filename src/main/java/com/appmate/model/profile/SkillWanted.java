package com.appmate.model.profile;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by uujc0207 on 2017. 3. 29..
 */
@Entity
@Table(name = "SKILL_WANTED")
public class SkillWanted {

    @Id
    private String user_id;
    private String skills_wanted;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSkills_wanted() {
        return skills_wanted;
    }

    public void setSkills_wanted(String skills_wanted) {
        this.skills_wanted = skills_wanted;
    }
}
