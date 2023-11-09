package com.example.demo.error;

import com.example.demo.error.exceptions.NotFoundException;
import com.example.demo.error.exceptions.NotFoundProduct;
import com.example.demo.error.exceptions.ProductExist;
import org.springframework.dao.DataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        errorResponse.setCode(HttpStatus.NOT_FOUND);
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {NotFoundProduct.class})
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        errorResponse.setCode(HttpStatus.NOT_FOUND);
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Comprobar si un producto existe antes de crearlo. Si existe devolver un error de producto existente.
    @ExceptionHandler(value = {ProductExist.class})
    public ResponseEntity<ErrorResponse> handleProductExist(ProductExist ex){
        ErrorResponse errorResponse = new ErrorResponse( HttpStatus.CONFLICT, ex.getMessage());
        errorResponse.setCode(HttpStatus.CONFLICT);
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}




