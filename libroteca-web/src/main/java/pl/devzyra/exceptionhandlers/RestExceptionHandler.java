package pl.devzyra.exceptionhandlers;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.devzyra.exceptions.BookServiceException;
import pl.devzyra.exceptions.ErrorDetails;
import pl.devzyra.exceptions.UserServiceException;
import pl.devzyra.restcontrollers.BookRestController;
import pl.devzyra.restcontrollers.SearchController;
import pl.devzyra.restcontrollers.UserRestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice(assignableTypes = {BookRestController.class, UserRestController.class, SearchController.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {


    @ExceptionHandler(value = {UserServiceException.class, BookServiceException.class})
    protected ResponseEntity<ErrorDetails> handleException(Exception e, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getRequestURI());


        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtherExceptions(Exception e, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getRequestURI());

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}