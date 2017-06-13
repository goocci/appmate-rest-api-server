package com.appmate.controller.location;

import com.appmate.model.location.MyFavoriteZone;
import com.appmate.repository.location.MyFavoriteZoneRepository;
import com.appmate.service.location.MyFavoriteZoneService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by uujc0207 on 2017. 3. 24..
 */
@RestController
@RequestMapping("/location")
public class MyFavoriteZoneController {

    @Autowired
    private MyFavoriteZoneRepository myFavoriteZoneRepository;

    @Autowired
    private MyFavoriteZoneService myFavoriteZoneService;

    // GET - All User's Favorite Zone
    @RequestMapping(value = "/my_favorite_zones/all", method = RequestMethod.GET)
    public ResponseEntity<String> getAllFavoriteZones() {

        JSONArray myFavoriteZone = myFavoriteZoneService.findAll();

        return new ResponseEntity<String>(myFavoriteZone.toString(), HttpStatus.OK);
    }

    // GET - My Favorite Zones
    @RequestMapping(value = "/my_favorite_zones/{user_id}", method = RequestMethod.GET)
    public List<MyFavoriteZone> getMyFavoriteZones(@PathVariable("user_id") String user_id) {

        List<MyFavoriteZone> myFavoriteZones = myFavoriteZoneService.findMyZones(user_id);

        if (user_id == null) {
            System.out.println("User with id " + user_id + " not found");
            return null;
        }

        return myFavoriteZones;
    }

    // PATCH - My Favorite Zones
    @RequestMapping(value = "/my_favorite_zones/{user_id}/{zone_num}", method = RequestMethod.PATCH)
    public ResponseEntity<MyFavoriteZone> updateMyFavoriteZones(@PathVariable("user_id") String user_id,
                                                                @PathVariable("zone_num") int zone_num,
                                                                @RequestBody MyFavoriteZone myFavoriteZone) {

        MyFavoriteZone currentMyFavoriteZone = myFavoriteZoneService.findZone(user_id, zone_num);

        if (currentMyFavoriteZone == null) {
            System.out.println("User with id " + user_id + " not found");
            return new ResponseEntity<MyFavoriteZone>(HttpStatus.NOT_FOUND);
        }

        if(myFavoriteZone.getLatitude() != null){
            currentMyFavoriteZone.setLatitude(myFavoriteZone.getLatitude());
        }
        if(myFavoriteZone.getLongitude() != null){
            currentMyFavoriteZone.setLongitude(myFavoriteZone.getLongitude());
        }
        if(myFavoriteZone.getAddress() != null){
            currentMyFavoriteZone.setAddress(myFavoriteZone.getAddress());
        }

        myFavoriteZoneRepository.save(currentMyFavoriteZone);

        return new ResponseEntity<MyFavoriteZone>(currentMyFavoriteZone, HttpStatus.OK);
    }


}
