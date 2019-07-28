package com.apogee.trackarea.db.dao;

import com.apogee.trackarea.db.pojo.DevicePojo;
import com.apogee.trackarea.db.pojo.PointPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DeviceDao extends JpaRepository<DevicePojo, Long> {

    String pointsQuery = "select p from DevicePojo p where p.createdAt >= :starting and p.createdAt <= :ending order by p.createdAt";

    @Query(pointsQuery)
    List<PointPojo> getPointsForDate(@Param("starting") LocalDateTime starting,@Param("ending") LocalDateTime ending);
}
