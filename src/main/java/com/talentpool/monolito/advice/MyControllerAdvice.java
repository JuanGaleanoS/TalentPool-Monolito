package com.talentpool.monolito.advice;

import com.talentpool.monolito.custom.exceptions.BusinessClienteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(BusinessClienteException.class)
    public ResponseEntity<BusinessClienteException> handlerExceptionClientes(BusinessClienteException businessClienteException, HttpServletRequest request) {
        BusinessClienteException errorInfo = new BusinessClienteException(businessClienteException.getMessage(), businessClienteException.getStatus(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, businessClienteException.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BusinessClienteException> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {

        // get spring errors
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        // convert errors to standard string
        StringBuilder errorMessage = new StringBuilder();
        fieldErrors.forEach(f -> errorMessage.append(f.getField() + " " + f.getDefaultMessage() + " "));

        // return error info object with standard json
        BusinessClienteException errorInfo = new BusinessClienteException(errorMessage.toString(), HttpStatus.BAD_REQUEST, request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BusinessClienteException> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentTypeMismatchException e) {

        // return error info object with standard json
        BusinessClienteException errorInfo = new BusinessClienteException(
                String.format("Discrepancia de tipo de argumento: %s", e.getName()),
                HttpStatus.BAD_REQUEST,
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);

    }
}
