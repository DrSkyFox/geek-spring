package ru.server;

public class AuthTimeOutException extends Exception{

    private int timeOut;
    private static final String msg = "Authentication timed out: ";

    public AuthTimeOutException(String message, int timeOut) {
        super(message);
        this.timeOut = timeOut;
    }

    public AuthTimeOutException(String message, Throwable cause, int timeOut) {
        super(message, cause);
        this.timeOut =timeOut;
    }

    public String getErrorMsg() {
        return  msg +  timeOut;
    }

    @Override
    public String getMessage() {
        return super.getMessage()  + "... " + getErrorMsg();
    }


}
