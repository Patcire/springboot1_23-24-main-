package com.example.demo.error.exceptions;

public class ProductExist extends RuntimeException {
    public ProductExist (){
        super("This product already exist");
    }
}
