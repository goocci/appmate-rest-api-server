package com.appmate.model.profile;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by uujc0207 on 2017. 3. 20..
 */
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "ABOUT_ME")
public class AboutMe {

    @Id
    private String user_id;

    @Size(max = 25)
    @NotNull
    private String nick_name;

    @Size(max = 50)
    private String greetings;

    private String career;

    @Size(max = 255)
    private String homepage;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getGreetings() {
        return greetings;
    }

    public void setGreetings(String greetings) {
        this.greetings = greetings;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
}
