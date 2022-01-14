package br.com.anderson_silva.Banking_system.exceptions;

import br.com.anderson_silva.Banking_system.security.JWTauthenticateFilter;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {


    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<StandardError> entityBadRequest(HttpMessageNotReadableException e, HttpServletRequest request) {
       StandardError err=new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.BAD_REQUEST.value());
        err.setError("Bad Request");
        err.setMessage("Required request body is missing");
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<StandardError> ArgumentNotValidBadRequest(MethodArgumentNotValidException e, HttpServletRequest request) {
        StandardError err=new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.BAD_REQUEST.value());
        err.setError("Bad Request");
        e.getAllErrors().forEach(error -> {
          err.setMessage(err.getMessage()+" "+error.getDefaultMessage());
        });

        err.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

    }
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<StandardError> NoSuchElement(PSQLException e, HttpServletRequest request) {
        StandardError err=new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.BAD_REQUEST.value());
        err.setError("Bad Request");
        err.setMessage(e.getServerErrorMessage().getDetail());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

    }

}
