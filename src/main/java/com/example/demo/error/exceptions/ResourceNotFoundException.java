package com.example.demo.error.exceptions;

import java.util.Scanner;

public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }


}