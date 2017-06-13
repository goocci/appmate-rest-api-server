package com.appmate.model.profile;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Created by uujc0207 on 2017. 3. 20..
 */
@Entity
@Table(name = "IMAGE")
public class Image {

    @Id
    private String user_id;

    @Size(max = 1000)
    private String image;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
