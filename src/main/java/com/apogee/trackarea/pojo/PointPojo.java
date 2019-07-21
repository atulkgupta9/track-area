package com.apogee.trackarea.pojo;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PointPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private double x;
    private double y;
    private LocalDateTime sentAt;

}
