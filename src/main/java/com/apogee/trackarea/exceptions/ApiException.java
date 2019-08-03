package com.apogee.trackarea.exceptions;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ApiException extends Exception {
    private ApiStatus status;

    public ApiException(ApiStatus status, String message){
        super(message);
        this.status = status;
    }

}
