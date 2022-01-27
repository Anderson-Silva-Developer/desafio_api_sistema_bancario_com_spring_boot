package br.com.anderson_silva.Banking_system.exceptions;

import org.postgresql.util.PSQLException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<?> NotFoundException( ChangeSetPersister.NotFoundException exception){
        ResourceNotFoundDetails resourceNotFoundDetails=new ResourceNotFoundDetails()
                .setStatus(HttpStatus.NOT_FOUND.value())
                .setDetail("Not Found");
        return new ResponseEntity<>(resourceNotFoundDetails,HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> NotReadableException(HttpMessageNotReadableException exception){
    ResourceNotFoundDetails resourceNotFoundDetails=new ResourceNotFoundDetails()
            .setStatus(HttpStatus.BAD_REQUEST.value())
            .setDetail("Required request body is missing");
    return new ResponseEntity<>(resourceNotFoundDetails,HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<?> SQLException(PSQLException exception, HttpServletRequest request){

        exception.getServerErrorMessage();
    //
        String fields=exception.getServerErrorMessage().getDetail().replaceAll("Key","");
        fields=fields.replaceAll("already exists","");

        String fieldsMessage=exception.getServerErrorMessage().getDetail().replaceAll("Key","o campo");
        fieldsMessage=fieldsMessage.replaceAll("already exists","já existe");
        ValidationErrorDetails validationErrorDetails=new ValidationErrorDetails();
        validationErrorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        validationErrorDetails.setDetail("Field Validation Error");
        validationErrorDetails.setField(fields);
        validationErrorDetails.setFieldMessage(fieldsMessage);

        return new ResponseEntity<>(validationErrorDetails,HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> MethodArgumentNotValidExceptionException(MethodArgumentNotValidException exception,HttpServletRequest request){
        List<FieldError> fieldErrors=exception.getBindingResult().getFieldErrors();

        String fields= fieldErrors.stream().map(FieldError::getField).distinct().collect(Collectors.joining(", "));

        String fieldMessage=fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        ValidationErrorDetails validationErrorDetails =  new ValidationErrorDetails();
        validationErrorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        validationErrorDetails.setDetail("Field Validation Error");
        validationErrorDetails.setField(fields);
        validationErrorDetails.setFieldMessage(fieldMessage);


        return new ResponseEntity<>(validationErrorDetails,HttpStatus.BAD_REQUEST);

    }

}
