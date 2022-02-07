package br.com.anderson_silva.Banking_system.exceptions;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> internalException(NullPointerException exception, HttpServletRequest request) {
        ResponseNotFoundDetails internalError = new ResponseNotFoundDetails()
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setError("INTERNAL_SERVER_ERROR")
                .setMessage(exception.getMessage())
                .setPath(request.getRequestURI());

        return new ResponseEntity<>(internalError, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @ExceptionHandler(StatusInternalException.class)
    public ResponseEntity<?> statusInternalException(StatusInternalException exception, HttpServletRequest request) {
        ResponseNotFoundDetails internalError = new ResponseNotFoundDetails()
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setError("INTERNAL_SERVER_ERROR")
                .setMessage(exception.getMessage())
                .setPath(request.getRequestURI());

        return new ResponseEntity<>(internalError, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @ExceptionHandler(StatusNotFoundException.class)
    public ResponseEntity<?> statusNotFoundException(StatusNotFoundException exception, HttpServletRequest request) {
        ErrorDetail NotFoundError = new ErrorDetail()
                .setStatus(HttpStatus.NOT_FOUND.value())
                .setError("Resource Not Found")
                .setMessage(exception.getMessage())
                .setPath(request.getRequestURI());

        return new ResponseEntity<>(NotFoundError, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) {
        ErrorDetail NotFoundError = new ErrorDetail()
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setError("INTERNAL_SERVER_ERROR")
                .setMessage(exception.getMessage())
                .setPath(request.getRequestURI());

        return new ResponseEntity<>(NotFoundError, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> notReadableException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        ResponseNotFoundDetails resourceNotFoundDetails = new ResponseNotFoundDetails()
                .setStatus(HttpStatus.BAD_REQUEST.value())
                .setError("Bad Request")
                .setMessage("Required request body is missing")
                .setPath(request.getRequestURI());
        return new ResponseEntity<>(resourceNotFoundDetails, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<?> SQLException(PSQLException exception, HttpServletRequest request) {

        exception.getServerErrorMessage();
        //
        String fields = exception.getServerErrorMessage().getDetail().replaceAll("Key", "");
        fields = fields.replaceAll("already exists", "");

        String fieldsMessage = exception.getServerErrorMessage().getDetail().replaceAll("Key", "o campo");
        fieldsMessage = fieldsMessage.replaceAll("already exists", "já existe");
        ValidationErrorDetails validationErrorDetails = new ValidationErrorDetails()
                .setStatus(HttpStatus.BAD_REQUEST.value())
                .setDetail("Field Validation Error")
                .setField(fields)
                .setFieldMessage(fieldsMessage);

        return new ResponseEntity<>(validationErrorDetails, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExceptionException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        String fields = fieldErrors.stream().map(FieldError::getField).distinct().collect(Collectors.joining(", "));

        String fieldMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        ValidationErrorDetails validationErrorDetails = new ValidationErrorDetails()
                .setStatus(HttpStatus.BAD_REQUEST.value())
                .setDetail("Field Validation Error")
                .setField(fields)
                .setFieldMessage(fieldMessage);


        return new ResponseEntity<>(validationErrorDetails, HttpStatus.BAD_REQUEST);

    }

}
