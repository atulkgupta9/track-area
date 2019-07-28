package com.apogee.trackarea.dtoapi.api;

import com.apogee.trackarea.db.dao.UserDao;
import com.apogee.trackarea.db.pojo.UserPojo;
import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.exceptions.ApiStatus;
import com.apogee.trackarea.helpers.constant.UserType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserApi extends AbstractApi<UserPojo, Long, UserDao> implements UserDetailsService {

    @Override
    @Transactional(readOnly = true)
    public UserPojo loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPojo user =  dao.findByUsername(username);
        if(user != null){
            return user;
        }
        throw new UsernameNotFoundException("Email does not exist");
    }

    @Transactional(readOnly = true)
    public boolean checkEmailAvailability(String username){
        UserPojo user =  dao.findByUsername(username);
        return user==null ? true : false;
    }

    @Override
    @Transactional
    public void saveEntity(UserPojo entity) throws ApiException {
        if(!checkEmailAvailability(entity.getUsername())){
            throw new ApiException(ApiStatus.BAD_DATA, "This email is already taken");
        }
        dao.save(entity);
    }

    @Transactional(readOnly = true)
    public List<UserPojo> getUsersByType(UserType userType){
        return dao.getByUserType(userType);
    }

    @Transactional(readOnly = true)
    public List<UserPojo> getAllNormalUsers() {
        return getUsersByType(UserType.USER);
    }

    @Transactional(readOnly = true)
    public List<UserPojo> getAllAdmins() {
        return getUsersByType(UserType.ADMIN);
    }

    @Transactional
    public void update(Long userId, UserPojo updated) throws ApiException {
        UserPojo existing = getCheckById(userId);
        existing.setPhone(updated.getPhone());
        //TODO set other properties
        existing.setDevices(updated.getDevices());
    }
}
