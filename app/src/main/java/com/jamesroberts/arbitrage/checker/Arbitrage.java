package com.jamesroberts.arbitrage.checker;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Arbitrage {

    public AsyncTask<String, Void, ArrayList> task;
    public ArrayList prices;
    public ArrayList getData(float exchangeRate) throws Exception{
        task = new FetchRates().execute();
        prices = task.get();
        return get_arb_info(exchangeRate);
    }

    private float getCapital() {
        return 50000.00f;
    }

    private float get_absa_fees() {
        return 376.00f;
    }

    private float kraken_price() throws Exception {
        return (float) prices.get(0);
    }

    private float luno_price() throws Exception {
        return (float) prices.get(1);
    }

    private ArrayList get_arb_info(float exchange_rate) throws Exception{
        float kraken = kraken_price();
        float luno = luno_price();

        float percentage = luno / (exchange_rate * kraken);
        percentage -= 1;
        percentage *= 100;

        // Convert to Euros
        float euros = getCapital() / exchange_rate;

        // Kraken deposit fees
        euros -= 15.00;

        // Buy BITCOIN (with fees of 0.26%)
        float btc = (euros * 0.9974f) / kraken;

        // Withdraw from kraken
        btc -= 0.0005;
        // deposit to luno
        btc -= 0.0002;
        // 1 % luno trade price
        btc *= 0.99;
        // Sell btc for rand
        float zar = btc * luno;
        // zar withdrawl fee
        zar -= 8.50;

        zar -= get_absa_fees();
        ArrayList data = new ArrayList();
        //data.add(zar);
        // Output data
        data.add(String.format ("%.2f", kraken));
        data.add(String.format ("%.2f", exchange_rate));
        data.add(String.format ("%.2f", luno));
        data.add(String.format ("%.2f", percentage));
        float percent_gain = (((zar/getCapital())-1)*100);
        data.add(String.format ("%.2f", percent_gain));
        if (percent_gain >= 3)
            data.add("GOOD");
        else if (percent_gain >= 2)
            data.add("OKAY");
        else
            data.add("BAD");
        float profit = zar - getCapital();
        data.add(String.format ("%.2f", profit));
        return data;
    }
}

