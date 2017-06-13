package com.appmate.model.profile;

import javax.persistence.*;

/**
 * Created by uujc0207 on 2017. 3. 20..
 */
@Entity
@Table(name = "I_LIKE_YOU")
public class ILikeYou {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String i_user_id;
    private String you_user_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getI_user_id() {
        return i_user_id;
    }

    public void setI_user_id(String i_user_id) {
        this.i_user_id = i_user_id;
    }

    public String getYou_user_id() {
        return you_user_id;
    }

    public void setYou_user_id(String you_user_id) {
        this.you_user_id = you_user_id;
    }
}
