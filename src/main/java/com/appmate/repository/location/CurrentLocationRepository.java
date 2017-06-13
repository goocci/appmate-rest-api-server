package com.appmate.repository.location;

import com.appmate.model.location.CurrentLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by uujc0207 on 2017. 3. 24..
 */
@RepositoryRestResource
public interface CurrentLocationRepository extends JpaRepository<CurrentLocation, String>{
}
