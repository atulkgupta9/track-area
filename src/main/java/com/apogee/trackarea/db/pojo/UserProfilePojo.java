package com.apogee.trackarea.db.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_profiles")
public class UserProfilePojo extends AbstractVersionedPojo{

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long userProfileId;
    private String district;
    private String block;
    private String village;
    private String tractor;
    private String address;
    private String name;

}
