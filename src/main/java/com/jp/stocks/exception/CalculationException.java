package com.jp.stocks.exception;

/**
 * Simple exception when calculation can not be performed
 **/
public class CalculationException extends Exception {

    public CalculationException(String message) {
        super(message);
    }

    public CalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}
