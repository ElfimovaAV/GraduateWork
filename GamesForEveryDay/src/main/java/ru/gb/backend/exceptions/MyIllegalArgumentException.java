package ru.gb.backend.exceptions;

public class MyIllegalArgumentException extends IllegalArgumentException{
    public MyIllegalArgumentException (String message) {
        super(message);
    }
}
