package com.jp.stocks.exception;

public class StockServiceException extends RuntimeException {

    public StockServiceException(String message){
        super(message);
    }

    public StockServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
