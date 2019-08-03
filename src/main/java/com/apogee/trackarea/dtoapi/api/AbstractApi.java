package com.apogee.trackarea.dtoapi.api;

import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.exceptions.ApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class AbstractApi<Pojo, Identifier, Dao extends JpaRepository> {

    @Autowired
    protected Dao dao;

    @Transactional(readOnly = true)
    public Pojo getById(Identifier id){
        Optional<Pojo> ans = dao.findById(id);
        return ans.isPresent() ? ans.get() : null;
    }

    @Transactional(readOnly = true)
    public Pojo getCheckById(Identifier id) throws ApiException {
        Pojo ans = getById(id);
        if(ans == null){
            String msg = String.format("Object with userId : %s not found", id);
            throw new ApiException(ApiStatus.BAD_DATA, msg);
        }
        return ans;
    }

    @Transactional(readOnly = true)
    public List<Pojo> getAllEntities(){
        return dao.findAll();
    }

    @Transactional
    public void saveEntity(Pojo entity) throws ApiException{
        dao.save(entity);
    }
}

