package com.apogee.trackarea.dao;

import com.apogee.trackarea.pojo.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<CustomUserDetails, Integer> {

    CustomUserDetails findByUsername(String username);

}