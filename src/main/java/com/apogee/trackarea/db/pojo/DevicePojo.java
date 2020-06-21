package com.apogee.trackarea.db.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "devices")
public class DevicePojo extends AbstractVersionedPojo {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long deviceId;

    private String deviceImei;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="device_id")
    private List<PointPojo> points = new ArrayList<>();

    @Fetch(FetchMode.SELECT)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="device_id")
    @OrderBy("reportId desc")
    private List<ReportPojo> reports = new ArrayList<>();
}
