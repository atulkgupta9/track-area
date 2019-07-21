package com.apogee.trackarea.api;

import com.apogee.trackarea.config.JwtTokenProvider;
import com.apogee.trackarea.constant.Authorities;
import com.apogee.trackarea.constant.UserType;
import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.model.JwtAuthenticationResponse;
import com.apogee.trackarea.model.LoginForm;
import com.apogee.trackarea.model.RegularUserSignUpForm;
import com.apogee.trackarea.pojo.CustomUserDetails;
import lombok.extern.log4j.Log4j2;
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
    private CustomUserDetailsService userApi;

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


    public void registerUser(RegularUserSignUpForm form) throws ApiException {
        CustomUserDetails newUser = new CustomUserDetails();
        newUser.setUsername(form.getUsername());
        newUser.setPassword(passwordEncoder.encode(form.getPassword()));
        newUser.setAuthorities(Authorities.USER);
        newUser.setPhone(form.getPhone());
        newUser.setPwdplain(form.getPassword());
        newUser.setUserType(UserType.USER);
        userApi.saveEntity(newUser);
    }

}
