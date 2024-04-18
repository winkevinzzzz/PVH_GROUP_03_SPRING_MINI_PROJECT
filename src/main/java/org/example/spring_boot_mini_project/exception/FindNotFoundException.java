package org.example.spring_boot_mini_project.exception;

public class FindNotFoundException extends Exception{

    public FindNotFoundException(String message){
        super(message);
    }
}
