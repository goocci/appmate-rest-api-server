package com.appmate.model.location;

import javax.persistence.*;

/**
 * Created by uujc0207 on 2017. 3. 24..
 */
@Entity
@Table(name = "MY_FAVORITE_ZONE")
public class MyFavoriteZone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String user_id;
    private String latitude;
    private String longitude;
    private int zone_num;
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getZone_num() {
        return zone_num;
    }

    public void setZone_num(int zone_num) {
        this.zone_num = zone_num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
