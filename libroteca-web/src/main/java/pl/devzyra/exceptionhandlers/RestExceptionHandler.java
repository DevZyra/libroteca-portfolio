package pl.devzyra.exceptionhandlers;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.devzyra.exceptions.BookServiceException;
import pl.devzyra.exceptions.ErrorDetails;
import pl.devzyra.exceptions.UserServiceException;
import pl.devzyra.restcontrollers.BookRestController;
import pl.devzyra.restcontrollers.SearchRestController;
import pl.devzyra.restcontrollers.UserRestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestControllerAdvice(assignableTypes = {BookRestController.class, UserRestController.class, SearchRestController.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {


    @ExceptionHandler(value = {UserServiceException.class, BookServiceException.class})
    protected ResponseEntity<ErrorDetails> handleException(Exception e, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getRequestURI());


        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorDetails> handleOtherExceptions(Exception e, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getRequestURI());

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}