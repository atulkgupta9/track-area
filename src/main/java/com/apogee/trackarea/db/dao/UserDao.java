package com.apogee.trackarea.db.dao;

import com.apogee.trackarea.db.pojo.UserPojo;
import com.apogee.trackarea.helpers.constant.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<UserPojo, Long> {

    UserPojo findByUsername(String username);

    String userTypeQuery = "select p from UserPojo p where p.userType = :userType";

    @Query(userTypeQuery)
    List<UserPojo> getByUserType(@Param("userType") UserType userType);
}