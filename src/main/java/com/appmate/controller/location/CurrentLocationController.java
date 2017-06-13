package com.appmate.controller.location;

import com.appmate.model.location.CurrentLocation;
import com.appmate.repository.location.CurrentLocationRepository;
import com.appmate.service.location.CurrentLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.appmate.SEED256.Seed256.Encrypt;

/**
 * Created by uujc0207 on 2017. 3. 25..
 */
@RestController
@RequestMapping("/location")
public class CurrentLocationController {

    @Autowired
    private CurrentLocationService currentLocationService;

    @Autowired
    private CurrentLocationRepository currentLocationRepository;

    // GET - All User's Current Location
    @RequestMapping(value = "/current_location/all", method = RequestMethod.GET)
    public ResponseEntity<List<CurrentLocation>> getAllFavoriteZones() {

        List<CurrentLocation> currentLocationList = currentLocationService.findAllCurrentLocation();

        if(currentLocationList.isEmpty()){
            return new ResponseEntity<List<CurrentLocation>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<CurrentLocation>>(currentLocationList, HttpStatus.OK);
    }

    // GET - My Current Location
    @RequestMapping(value = "/current_location/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<CurrentLocation> getAboutMe(@PathVariable String user_id) {

        CurrentLocation currentLocation = currentLocationService.findMyCurrentLocation(user_id);

        if (currentLocation == null) {
            System.out.println("User with id " + user_id + " not found");
            return new ResponseEntity<CurrentLocation>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<CurrentLocation>(currentLocation, HttpStatus.OK);
    }

    // PATCH
    @RequestMapping(value = "/current_location/{user_id}", method = RequestMethod.PATCH)
    public ResponseEntity<CurrentLocation> updateCurrentLocation(@PathVariable("user_id") String user_id,
                                                                 @RequestBody CurrentLocation currentLocation) {

        CurrentLocation currentCurrentLocation = currentLocationRepository.findOne(user_id);

        if (currentCurrentLocation == null) {
            System.out.println("User with id " + user_id + " not found");
            return new ResponseEntity<CurrentLocation>(HttpStatus.NOT_FOUND);
        }

        String latitude, longitude;

        if(currentLocation.getLatitude() != null){
            latitude = Encrypt(currentLocation.getLatitude());
            currentCurrentLocation.setLatitude(latitude);
        }
        if(currentLocation.getLongitude() != null){
            longitude = Encrypt(currentLocation.getLongitude());
            currentCurrentLocation.setLongitude(longitude);
        }

        currentLocationRepository.save(currentCurrentLocation);

        return new ResponseEntity<CurrentLocation>(currentCurrentLocation, HttpStatus.OK);
    }

}
