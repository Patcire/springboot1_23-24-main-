package com.example.demo.error.exceptions;


// Es igual que NotFoundException, pero en los controladores del repo clonado se usan una y otra
// Así que he creado esta que no estaba creada
public class NotFoundException extends RuntimeException {
   public NotFoundException(String message){
       super(message);
   }

}
