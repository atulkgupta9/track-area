package com.apogee.trackarea.db.dao;

import com.apogee.trackarea.db.pojo.UserPojo;
import com.apogee.trackarea.helpers.constant.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<UserPojo, Long> {

    UserPojo findByUsername(String username);

    String userTypeQuery = "select p from UserPojo p where p.userType = :userType order by p.createdAt desc";

    @Query(userTypeQuery)
    List<UserPojo> getByUserType(@Param("userType") UserType userType);

    String getCountOfUsersByType = "select count(p) from UserPojo p where p.userType = :userType";

    @Query(getCountOfUsersByType)
    public long getCountByType(@Param("userType") UserType userType);

    UserPojo findByPhone(String phone);


    //WHERE UPPER(u.username) LIKE CONCAT('%',UPPER(:username),'%')
    String searchUser = "select distinct(p) from UserPojo p JOIN p.devices d where p.userType = :userType and" +
            " (:username is null or upper(p.username) like concat('%', upper(:username), '%')) and (:deviceNo is null or upper(d.deviceImei) like concat ('%', upper(:deviceNo), '%'))" +
            "order by p.createdAt desc";
    @Query(searchUser)
    List<UserPojo> getByForm(@Param("userType") UserType userType, @Param("deviceNo") String deviceNo, @Param("username") String username);
}