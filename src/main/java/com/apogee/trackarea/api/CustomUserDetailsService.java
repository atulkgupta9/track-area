package com.apogee.trackarea.api;

import com.apogee.trackarea.dao.UserDao;
import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.exceptions.ApiStatus;
import com.apogee.trackarea.pojo.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService extends AbstractApi<CustomUserDetails, Integer, UserDao> implements UserDetailsService {

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUserDetails user =  dao.findByUsername(username);
        if(user != null){
            return user;
        }
        throw new UsernameNotFoundException("Email does not exist");
    }

    public boolean checkEmailAvailability(String username){
        CustomUserDetails user =  dao.findByUsername(username);
        return user==null ? true : false;
    }

    @Override
    public void saveEntity(CustomUserDetails entity) throws ApiException {
        if(!checkEmailAvailability(entity.getUsername())){
            throw new ApiException(ApiStatus.BAD_DATA, "This email is already taken");
        }
        dao.save(entity);
    }
}
