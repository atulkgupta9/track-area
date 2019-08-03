package com.apogee.trackarea.dtoapi.api;

import com.apogee.trackarea.db.dao.PointDao;
import com.apogee.trackarea.db.pojo.PointPojo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PointApi extends AbstractApi<PointPojo, Long, PointDao> {

    @Transactional
    public void deleteAllPoints(List<PointPojo> points){
        dao.deleteInBatch(points);
    }
}
