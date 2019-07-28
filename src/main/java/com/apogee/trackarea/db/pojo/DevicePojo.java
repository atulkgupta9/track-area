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
@Table(name = "device")
public class DevicePojo extends AbstractVersionedPojo {
    @Id
    @GeneratedValue(strategy =  GenerationType.TABLE, generator="device_seq_generator")
    @SequenceGenerator(name = "device_seq_generator",  initialValue = 100001, allocationSize = 100)
    private Long deviceId;

    private String deviceImei;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private UserPojo user;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "device")
//        @JoinColumn(name = "device", referencedColumnName = "deviceId")
    @Fetch(FetchMode.SELECT)
    private List<PointPojo> points = new ArrayList<>();

    @Fetch(FetchMode.SELECT)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "device")
    private List<ReportPojo> reports = new ArrayList<>();
}
