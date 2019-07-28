package com.apogee.trackarea.db.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "report")
public class ReportPojo extends AbstractVersionedPojo{
    @Id
    @GeneratedValue(strategy =  GenerationType.TABLE, generator="report_seq_generator")
    @SequenceGenerator(name = "report_seq_generator",  initialValue = 100001, allocationSize = 100)
    private Long reportId;

    private String startGeoCordinate;

    private String endGeoCordinate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String url;

    private Double calculatedArea;

    private Integer actualPointsCaptured;

    private Integer areaPointsCaptured;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "device")
    private DevicePojo device;

}
