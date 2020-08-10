package com.pany.ejb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pany.model.Company;
import com.pany.model.Record;
import com.pany.model.RecordMonitor;
import com.pany.model.User;

@Singleton
@LocalBean
@Startup
@Local(RecordsEjbInterface.class)
public class RecordsEjb implements RecordsEjbInterface {
    @PostConstruct
    private void init() {
        update();
    }

    @Override
    public void update() {
        Broadcaster.broadcast();
    }

    @Override
    public List<Record> getRecords(Integer size, Integer page) throws JSONException, IOException {
        JSONObject json;
        if (size == null || page == null) {
            json = readJsonFromUrl("http://85.93.97.170:7000/lemon/users");
        } else {
            json = readJsonFromUrl("http://85.93.97.170:7000/lemon/users?page=" + page + "&size=" + size);
        }
        List<Record> records = new ArrayList<Record>();
        JSONArray data = json.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            records.add(new Record(data.getJSONObject(i).get("_id").toString(),
                    data.getJSONObject(i).get("first_name").toString(),
                    data.getJSONObject(i).get("last_name").toString(), data.getJSONObject(i).get("avatar").toString()));
        }
        RecordMonitor.setRecords(records);
        RecordMonitor.setTotalRecords(json.getInt("total"));
        update();
        return records;
    }

    @Override
    public User getUserDetails(String userId) {
        try {
            JSONObject json = readJsonFromUrl("http://85.93.97.170:7000/lemon/users/" + userId);
            User u = new User(json.getString("_id"), json.getString("first_name"), json.getString("last_name"),
                    json.getString("avatar"));
            u.setEmail(json.getString("email"));
            JSONObject company = json.getJSONArray("company").getJSONObject(0);
            JSONObject location = company.getJSONArray("location").getJSONObject(0);
            u.setCompany(new Company(location.getFloat("lat"), location.getFloat("long"), company.getString("name"),
                    company.getString("country"), json.getString("ip_address")));
            u.setDetailID(json.getInt("id") + "");
            return u;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

}
