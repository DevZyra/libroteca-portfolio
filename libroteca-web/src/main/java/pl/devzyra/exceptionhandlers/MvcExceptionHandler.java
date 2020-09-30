package pl.devzyra.exceptionhandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import pl.devzyra.exceptions.BookServiceException;
import pl.devzyra.exceptions.ErrorDetails;
import pl.devzyra.exceptions.UserServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class MvcExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ModelAndView handleNotFound(Exception e, HttpServletRequest request) {
        log.error("", e);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getRequestURI());

        ModelAndView modelAndView = new ModelAndView("404error");
        modelAndView.addObject("err", errorDetails);


        return modelAndView;

    }

    @ExceptionHandler(value = {UserServiceException.class, BookServiceException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleUserException(Exception e, HttpServletRequest request) {
        log.error("", e);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getRequestURI());

        ModelAndView modelAndView = new ModelAndView("400error");
        modelAndView.addObject("err", errorDetails);


        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleInternalException(Exception e, HttpServletRequest request) {
        log.error("", e);


        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getRequestURI());


        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("err", errorDetails);


        return modelAndView;
    }

}
