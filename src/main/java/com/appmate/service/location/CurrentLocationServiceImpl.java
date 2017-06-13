package com.appmate.service.location;

import com.appmate.model.location.CurrentLocation;
import com.appmate.repository.location.CurrentLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

import static com.appmate.SEED256.Seed256.Decrypt;

/**
 * Created by uujc0207 on 2017. 3. 26..
 */
@Service("currentLocationService")
@Transactional
public class CurrentLocationServiceImpl implements CurrentLocationService{

    @Autowired
    private CurrentLocationRepository currentLocationRepository;

    // 나의 현재위치 GET
    public CurrentLocation findMyCurrentLocation(String user_id) {

        CurrentLocation currentLocation = currentLocationRepository.findOne(user_id);
        CurrentLocation myCurrentLocation = new CurrentLocation();

        String latitude, longitude;
        latitude = Decrypt(currentLocation.getLatitude());
        longitude = Decrypt(currentLocation.getLongitude());

        myCurrentLocation.setUser_id(user_id);
        myCurrentLocation.setLatitude(latitude);
        myCurrentLocation.setLongitude(longitude);

        return myCurrentLocation;
    }

    // 모든 사용자의 현재위치 GET
    public List<CurrentLocation> findAllCurrentLocation() {

        List<CurrentLocation> currentLocations = currentLocationRepository.findAll();

        LinkedList<CurrentLocation> currentLocationList = new LinkedList<>();

        String user_id, latitude, longitude;

        for(int i = 0 ; i < currentLocations.size() ; i++){

            CurrentLocation currentLocation = new CurrentLocation();

            user_id = currentLocations.get(i).getUser_id();
            latitude = Decrypt(currentLocations.get(i).getLatitude());
            longitude = Decrypt(currentLocations.get(i).getLongitude());

            currentLocation.setUser_id(user_id);
            currentLocation.setLatitude(latitude);
            currentLocation.setLongitude(longitude);

            currentLocationList.add(currentLocation);

        }

        return currentLocationList;

    }
}
