package com.pany.ejb;

import java.io.IOException;
import java.util.List;

import javax.ejb.Local;

import org.json.JSONException;

import com.pany.model.Record;
import com.pany.model.User;

@Local
public interface RecordsEjbInterface {
    /**
     * Return a List of records given by number of records and page
     *
     * @param size number of records to return
     * @param page page of records
     * @return a list of records
     * @throws IOException
     * @throws JSONException
     */
    public List<Record> getRecords(Integer size, Integer page) throws JSONException, IOException;

    public User getUserDetails(String userId);

    /**
     * broadcast update to all listeners
     */
    public void update();

}
