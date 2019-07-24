package com.apogee.trackarea.dao;

import com.apogee.trackarea.pojo.PointPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PointDao extends JpaRepository<PointPojo, Integer> {

    String pointsQuery = "select p from PointPojo p where p.receivedAt >= :starting and p.receivedAt <= :ending order by p.receivedAt";

    @Query(pointsQuery)
    List<PointPojo> getPointsForDate(@Param("starting") LocalDateTime starting,@Param("ending") LocalDateTime ending);
}
