package com.apogee.trackarea.dao;

import com.apogee.trackarea.algo.Point;
import com.apogee.trackarea.pojo.PointPojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointDao extends JpaRepository<PointPojo, Integer> {
}
