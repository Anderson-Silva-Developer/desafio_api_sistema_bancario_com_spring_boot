package br.com.anderson_silva.Banking_system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class StatusInternalException extends RuntimeException{
    public StatusInternalException(String msm){
        super(msm);

    }
}
