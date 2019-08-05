package com.apogee.trackarea.db.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_profile_table")
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
//    @JsonIgnore
//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "user")
//    private UserPojo user;

}
