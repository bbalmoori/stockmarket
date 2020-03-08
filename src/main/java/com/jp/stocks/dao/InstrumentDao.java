package com.jp.stocks.dao;

import com.jp.stocks.entity.Instrument;
import com.jp.stocks.entity.Stock;
import com.jp.stocks.exception.DaoException;
import com.jp.stocks.exception.InstrumentNotFoundException;

import java.util.List;

/**
 * This is persistence class for the given instrument.
 * The implementors of these shall consider whether to store in filesystem, Database or keep them in memory.
 * This can be extended to any type of financial instrument
 *
 **/
public interface InstrumentDao<T extends Instrument> {

    /**
     * Persists a given instrument
     *
     * @param instrument
     *      Instrument to save, this can be stock, fx or any other financial instrument
     *
     * @return boolean if saved successfully
     *
     * @throws DaoException
     *      in case of any connection issues or cache issues (if it is stored in memory)
     **/
    boolean saveInstrument(T instrument) throws DaoException;

    /**
     * Delete a given instrument
     *
     * @param instrument
     *      Instrument to delete, this can be stock, fx or any other financial instrument
     *
     *  @return boolean if removed successfully
     *
     * @throws DaoException
     *      in case of any connection issues or cache issues (if it is stored in memory)
     *
     * @throws InstrumentNotFoundException
     *      if the given instrument is not found
     **/
    boolean deleteInstrument(T instrument) throws DaoException, InstrumentNotFoundException;


    /**
     * Find a instrument for given ticker
     *
     * @param ticker
     *      Instrument to find, this can be stock, fx or any other financial instrument
     *
     *  @return T if found
     *
     * @throws DaoException
     *      in case of any connection issues or cache issues (if it is stored in memory)
     *
     * @throws InstrumentNotFoundException
     *      if the given instrument is not found
     **/
    T getInstrument(String ticker) throws DaoException, InstrumentNotFoundException;


    /**
     * Find all instruments
     *
     *  @return List if found
     *
     * @throws DaoException
     *      in case of any connection issues or cache issues (if it is stored in memory)
     *
     * @throws InstrumentNotFoundException
     *      if the given instrument is not found
     **/
    List<T> getInstruments() throws DaoException, InstrumentNotFoundException;
}
