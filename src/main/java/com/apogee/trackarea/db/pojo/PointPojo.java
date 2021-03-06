package com.apogee.trackarea.db.pojo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="points")
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

}
