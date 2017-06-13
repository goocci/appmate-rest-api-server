package com.appmate.repository.location;

import com.appmate.model.location.MyFavoriteZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by uujc0207 on 2017. 3. 24..
 */
public interface MyFavoriteZoneRepository extends JpaRepository<MyFavoriteZone, Long>{
}
