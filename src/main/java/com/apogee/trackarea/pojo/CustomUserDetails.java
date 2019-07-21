package com.apogee.trackarea.pojo;

import com.apogee.trackarea.constant.Authorities;
import com.apogee.trackarea.constant.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Arrays;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@Table(name = "user", indexes={ @Index(name = "user_username" , columnList = "username", unique = true)})
public class CustomUserDetails implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String pwdplain;

    @JsonIgnore
    private String phone;

    private boolean isActive=true;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Authorities authorities;

    private Integer entityId;

    @Enumerated(EnumType.STRING)
    private UserType userType;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //        return authorities.stream().map(x->new SimpleGrantedAuthority(x)).collect(Collectors.toList());
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