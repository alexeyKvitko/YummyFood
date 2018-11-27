package ru.yummy.food.exception;

public class BusinessLogicException extends Exception {
    public BusinessLogicException() {
        super();
    }

    public BusinessLogicException(String message) {
        super( message );
    }

    public BusinessLogicException(String p_arg0, Throwable p_arg1) {
        super( p_arg0, p_arg1 );
    }

    public BusinessLogicException(Throwable p_arg0) {
        super( p_arg0 );
    }
}

