package com.apogee.trackarea.dtoapi.dto;

import com.apogee.trackarea.config.JwtTokenProvider;
import com.apogee.trackarea.db.pojo.UserProfilePojo;
import com.apogee.trackarea.dtoapi.api.UserApi;
import com.apogee.trackarea.helpers.constant.Authorities;
import com.apogee.trackarea.helpers.constant.UserType;
import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.model.data.JwtAuthenticationResponse;
import com.apogee.trackarea.model.form.AdminForm;
import com.apogee.trackarea.model.form.LoginForm;
import com.apogee.trackarea.model.form.UserForm;
import com.apogee.trackarea.db.pojo.UserPojo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class LoginDto {

    @Autowired
    private UserApi userApi;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;


    public JwtAuthenticationResponse loginUser(LoginForm form){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                form.getUsername(),
                form.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(jwt);
    }


    public void registerUser(UserForm form) throws ApiException {
        UserPojo newUser = new UserPojo();
        newUser.setUsername(form.getUsername());
        newUser.setPassword(passwordEncoder.encode(form.getPassword()));
        newUser.setAuthorities(Authorities.USER);
        UserProfilePojo userProfile = new UserProfilePojo();
        BeanUtils.copyProperties(form.getUserProfile(), userProfile);
//        userProfile.setUser(newUser);
        newUser.setUserProfile(userProfile);
        newUser.setPwdplain(form.getPassword());
        newUser.setUserType(UserType.USER);
        userApi.saveEntity(newUser);
    }

    public void createAdmin(AdminForm adminForm) throws ApiException {
        UserPojo newAdmin = new UserPojo();
        newAdmin.setUsername(adminForm.getUsername());
        newAdmin.setAuthorities(Authorities.ADMIN);
        newAdmin.setPwdplain(adminForm.getPassword());
        newAdmin.setPassword(passwordEncoder.encode(adminForm.getPassword()));
        newAdmin.setUserType(UserType.ADMIN);
        userApi.saveEntity(newAdmin);
    }
}
