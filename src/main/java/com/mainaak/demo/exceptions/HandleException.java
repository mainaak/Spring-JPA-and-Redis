package com.mainaak.demo.exceptions;

import com.mainaak.demo.configuration.Everything;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

@RestControllerAdvice
public class HandleException {

    private final Everything configuration;
    private final ZonedDateTime zonedDateTime;

    @Autowired
    public HandleException(Everything e) {
        this.configuration = e;
        zonedDateTime = ZonedDateTime.now(ZoneId.of(configuration.timezone));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ExceptionDto javaxValidationHandling(MethodArgumentNotValidException e) {

        return new ExceptionDto(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                zonedDateTime
        );
    }

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<ExceptionDto> serverErrorException(ServerErrorException e) {

        e.printStackTrace();

        ExceptionDto response = new ExceptionDto(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                zonedDateTime
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserErrorException.class)
    public ResponseEntity<ExceptionDto> userErrorException(UserErrorException e) {

        e.printStackTrace();

        ExceptionDto response = new ExceptionDto(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                zonedDateTime
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
