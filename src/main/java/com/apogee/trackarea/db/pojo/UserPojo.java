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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user", indexes={ @Index(name = "user_username" , columnList = "username", unique = true)})
public class UserPojo extends AbstractVersionedPojo implements UserDetails  {

    @Id
    @GeneratedValue(strategy =  GenerationType.TABLE, generator="user_seq_generator")
    @SequenceGenerator(name = "user_seq_generator",  initialValue = 1001, allocationSize = 100)

    private Long userId;

    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String pwdplain;

    @JsonIgnore
    private String phone;

    @JsonIgnore
    private boolean isActive=true;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Authorities authorities;


    @Enumerated(EnumType.STRING)
    private UserType userType;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
//    @JoinColumn(name = "user", referencedColumnName = "userId")
    private List<DevicePojo> devices = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //        return authorities.stream().map(lat->new SimpleGrantedAuthority(lat)).collect(Collectors.toList());
        return Arrays.asList(new SimpleGrantedAuthority(this.authorities.toString()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}