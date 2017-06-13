package com.appmate.repository.login;

import com.appmate.model.login.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by uujc0207 on 2017. 3. 26..
 */
public interface UserRepository extends JpaRepository<User, String>{
}
