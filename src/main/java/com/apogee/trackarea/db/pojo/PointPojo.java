package com.apogee.trackarea.db.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
@Table(name="point")
public class PointPojo extends AbstractVersionedPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pointId;

    //22.52 N -> latitude
    //72.92 E -> longitude

    //287039.82 -> easting
    //2496790.76 -> northing
    private Double lat;
    private Double lon;
    private Double utmEasting;
    private Double utmNorthing;

    @Type(type = "text")
    private String gpgga;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device")
    private DevicePojo device;
}
