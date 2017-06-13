package com.appmate.model.location;

/**
 * Created by uujc0207 on 2017. 3. 24..
 */

import javax.persistence.*;

@Entity
@Table(name = "CURRENT_LOCATION")
public class CurrentLocation {

    @Id
    private String user_id;
    private String latitude;
    private String longitude;

    public CurrentLocation(){

    }

    public CurrentLocation(String user_id, String latitude, String longitude){
        this.user_id = user_id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
