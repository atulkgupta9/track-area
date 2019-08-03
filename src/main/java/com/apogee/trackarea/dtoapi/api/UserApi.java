package com.apogee.trackarea.dtoapi.api;

import com.apogee.trackarea.db.dao.UserDao;
import com.apogee.trackarea.db.pojo.DevicePojo;
import com.apogee.trackarea.db.pojo.UserPojo;
import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.exceptions.ApiStatus;
import com.apogee.trackarea.helpers.constant.UserType;
import com.apogee.trackarea.helpers.util.Helper;
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
        user = dao.findByPhone(username);
        if(user != null){
            return user;
        }
        throw new UsernameNotFoundException("User ID/Mobile does not exist");
    }

    @Transactional(readOnly = true)
    public boolean checkPhoneAvailability(String phone){
        UserPojo user =  dao.findByPhone(phone);
        return user==null ? true : false;
    }

    @Override
    @Transactional
    public void saveEntity(UserPojo entity) throws ApiException {
        if(!checkPhoneAvailability(entity.getPhone())){
            throw new ApiException(ApiStatus.BAD_DATA, "This mobile no. is already taken");
        }
        if(entity.getUserType().equals(UserType.USER)){
            entity.setUsername(Helper.getUserSequence(getUserCount()+1));
        }else if(entity.getUserType().equals(UserType.ADMIN)){
            entity.setUsername(Helper.getAdminSequence(getAdminCount()+1));
        }
        dao.save(entity);
    }

    @Transactional(readOnly = true)
    public List<UserPojo> getUsersByType(UserType userType){
//        Sort sort = new Sort(Sort.Direction.DESC, "devices.reports.reportId");
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
        existing.setUserProfile(updated.getUserProfile());
        //TODO set other properties
        existing.setDevices(updated.getDevices());
    }

    @Transactional
    public void update(Long userId, DevicePojo updated) throws ApiException {
        UserPojo existing = getCheckById(userId);
        //TODO set other properties
        existing.getDevices().add(updated);
    }

    @Transactional(readOnly = true)
    public long getUserCount(){
        return dao.getCountByType(UserType.USER);
    }
    @Transactional(readOnly = true)
    public long getAdminCount(){
        return dao.getCountByType(UserType.ADMIN);
    }


}
