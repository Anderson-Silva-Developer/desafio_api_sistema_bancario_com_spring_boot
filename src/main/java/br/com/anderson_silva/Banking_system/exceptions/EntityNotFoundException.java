package br.com.anderson_silva.Banking_system.exceptions;


public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String msm) {
        super(msm);
    }

}