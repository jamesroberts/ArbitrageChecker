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

    float CAPITAL;
    float RATE;
    float FEES;

    float KRAKEN_PRICE;
    float LUNO_PRICE;
    float VALR_PRICE;

    public Arbitrage() {
        this.CAPITAL = 0f;
        this.RATE = 0f;
        this.FEES = 0f;
        this.KRAKEN_PRICE = 0f;
        this.LUNO_PRICE = 0f;
        this.VALR_PRICE = 0f;
    }

    // Getters
    public float getCapital() {
        return CAPITAL;
    }

    public float getRate() {
        return RATE;
    }

    public float getFees() {
        return FEES;
    }

    public float getKrakenPrice() {
        return KRAKEN_PRICE;
    }

    public float getLunoPrice() {
        return LUNO_PRICE;
    }

    public float getValrPrice() {
        return VALR_PRICE;
    }

    // Setters
    public void setCapital(float capital){
        this.CAPITAL = capital;
    }

    public void setRate(float rate){
        this.RATE = rate;
    }

    public void setFees(float fees){
        this.FEES = fees;
    }

    public void setPrices() throws Exception{
        if (KRAKEN_PRICE == 0f) {
            AsyncTask<String, Void, ArrayList> task = new FetchRates().execute();
            ArrayList prices = task.get();
            // Set the prices
            this.KRAKEN_PRICE = (float) prices.get(0);
            this.LUNO_PRICE = (float) prices.get(1);
            this.VALR_PRICE = (float) prices.get(2);
        }
    }

    public ArbResult calculate() {
        float lunoArb = getLunoPrice() / (getRate() * getKrakenPrice());
        lunoArb -= 1;
        lunoArb *= 100;

        float valrArb = getValrPrice() / (getRate() * getKrakenPrice());
        valrArb -= 1;
        valrArb *= 100;

        // Convert Capital to Euros
        float euros = getCapital() / getRate();

        // Minus Kraken euro deposit fees
        euros -= 20.00f;

        // Buy BTC with Euros (fees of 0.26%)
        float BTC = (euros * 0.9974f) / getKrakenPrice();

        // Kraken BTC withdraw fees
        BTC -= 0.00015;

        // Luno : 0.1% trade fee
        double lunoBtc = BTC * 0.999;
        // VALR : 0.2% trade fee
        double valrBtc = BTC * 0.999;

        // Sell BTC for rand on Luno
        double lunoZAR = lunoBtc * getLunoPrice();

        // Sell BTC for rand on VALR
        double valrZAR = valrBtc * getValrPrice();

        // RAND withdrawl fee
        lunoZAR -= 8.50;
        valrZAR -= 8.50;

        lunoZAR -= getFees();
        valrZAR -= getFees();

        // Final profit
        lunoZAR -= getCapital();
        valrZAR -= getCapital();

        ArbResult result = new ArbResult(
            getKrakenPrice(),
            getLunoPrice(),
            getValrPrice(),
            lunoArb,
            valrArb,
            lunoZAR,
            valrZAR
        );

        return result;
    }
}

