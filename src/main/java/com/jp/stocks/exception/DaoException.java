package com.jp.stocks.exception;

/**
 * Simple exception when persisting the instrument
 **/
public class DaoException  extends Exception {

    public DaoException(String message){
        super(message);
    }

    public DaoException(String message, Throwable cause){
        super(message, cause);
    }

}
