package com.apogee.trackarea.db.pojo;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "reports")
public class ReportPojo extends AbstractVersionedPojo{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long reportId;

    private String startGeoCoordinate;

    private String endGeoCoordinate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String url;

    private Double calculatedArea;

    private Integer actualPointsCaptured;

    private Integer areaPointsCaptured;

}
