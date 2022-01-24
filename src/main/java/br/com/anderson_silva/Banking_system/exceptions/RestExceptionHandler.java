package br.com.anderson_silva.Banking_system.exceptions;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception){
    ResourceNotFoundDetails resourceNotFoundDetails=new ResourceNotFoundDetails()
            .setTimestamp(new Date().getTime())
            .setStatus(HttpStatus.NOT_FOUND.value())
            .setTitle("Resource not Found")
            .setDetail(exception.getMessage())
            .setDeveloperMessage(exception.getClass().getName());

    return new ResponseEntity<>(resourceNotFoundDetails,HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> MethodArgumentNotValidExceptionException(MethodArgumentNotValidException exception){
        List<FieldError> fieldErrors=exception.getBindingResult().getFieldErrors();

        String fields= fieldErrors.stream().map(FieldError::getField).distinct().collect(Collectors.joining(", "));

        String fieldMessage=fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        ValidationErrorDetails validationErrorDetails =  new ValidationErrorDetails();
        validationErrorDetails.setTimestamp(new Date().getTime());
        validationErrorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        validationErrorDetails.setTitle("Field Validation Error");
        validationErrorDetails.setDetail("Field Validation Error");
        validationErrorDetails.setDeveloperMessage(exception.getClass().getName());
        validationErrorDetails.setField(fields);
        validationErrorDetails.setFieldMessage(fieldMessage);

        return new ResponseEntity<>(validationErrorDetails,HttpStatus.BAD_REQUEST);

    }

}
