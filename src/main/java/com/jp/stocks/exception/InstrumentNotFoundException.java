package com.jp.stocks.exception;

/**
 * Simple exception when given instrument is not found
 **/
public class InstrumentNotFoundException extends Exception {

    public InstrumentNotFoundException(String message){
        super(message);
    }

    public InstrumentNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
