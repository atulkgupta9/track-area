package com.apogee.trackarea.db.pojo;

import com.apogee.trackarea.helpers.constant.Authorities;
import com.apogee.trackarea.helpers.constant.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user_table")
public class UserPojo extends AbstractVersionedPojo implements UserDetails  {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @NotNull
    @Column(unique = true)
    private String phone;

    @JsonIgnore
    private String pwdplain;

    @JsonIgnore
    private boolean isActive=true;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Authorities authorities;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="user_profile")
    private UserProfilePojo userProfile;

    @Enumerated(EnumType.STRING)
    private UserType userType;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
//    @JoinColumn(name = "user", referencedColumnName = "userId")
    private List<DevicePojo> devices = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //        return authorities.stream().map(lat->new SimpleGrantedAuthority(lat)).collect(Collectors.toList());
        return Arrays.asList(new SimpleGrantedAuthority(this.authorities.toString()));
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return isActive;
    }
}