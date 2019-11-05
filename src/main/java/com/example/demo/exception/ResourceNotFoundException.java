package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author praveen.verma
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    /**
     * ResourceNotFoundException method - If Resources are not found.
     * @param message
     */
    public ResourceNotFoundException(String message){
        super(message);
    }
    
    
    
}