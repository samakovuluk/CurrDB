package com.example.CurrDB;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component

public class UpdateCurrency {
    private Logger log= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UpdateCurrency updateCurrency;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Scheduled(fixedDelay=10*60000)//10 minutes
    public void update() throws JSONException {
        try
        {
            jdbcTemplate.execute("CREATE TABLE Currencies(currency char(50))");
            jdbcTemplate.execute("ALTER TABLE Currencies ADD COLUMN Date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER currency");
        }
        catch (Exception e){}

        jdbcTemplate.execute("INSERT INTO Currencies(currency) VALUES (\'"+getUSDKZT()+"\')" );

    }

    public String getUSDKZT() throws JSONException {

        String res=new GettingCurrency().get("https://openexchangerates.org/api/latest.json?app_id=70dfe60d467c47e684346dc096a306ea&base=USD&symbols=KZT");
        JSONObject array = null;
        JSONObject jsonObj=null;
        try {
            jsonObj = new JSONObject(res);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            array = jsonObj.getJSONObject("rates");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(String.valueOf(array.get("KZT")));
        return String.valueOf(array.get("KZT"));

    }
}
