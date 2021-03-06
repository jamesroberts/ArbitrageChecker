package com.jamesroberts.arbitrage.checker;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchRates extends AsyncTask<String, Void, ArrayList> {
    protected ArrayList doInBackground(String... urls) {
        ArrayList prices = new ArrayList();
        try {
            prices.add(krakenPrice());
            prices.add(lunoPrice());
            prices.add(valrPrice());
        } catch (Exception e) {
            System.out.println("There was a problem fetching the rates");
        }
        return prices;
    }

    protected void onPostExecute(ArrayList feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }

    private float krakenPrice() throws Exception {
        String url = "https://api.kraken.com/0/public/Ticker?pair=XBTEUR";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject myResponse = new JSONObject(response.toString());

        JSONObject result = new JSONObject(myResponse.getString("result"));

        JSONObject pair = new JSONObject(result.getString("XXBTZEUR"));

        JSONArray list = pair.getJSONArray("a");

        float price = Float.parseFloat((String)list.get(0));
        return price;
    }

    private float lunoPrice() throws Exception {
        String url = "https://api.mybitx.com/api/1/ticker?pair=XBTZAR";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject myResponse = new JSONObject(response.toString());
        float price = Float.parseFloat(myResponse.getString("bid"));
        return price;
    }

    private float valrPrice() throws Exception {
        String url = "https://api.valr.com/v1/public/BTCZAR/marketsummary";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject myResponse = new JSONObject(response.toString());
        float price = Float.parseFloat(myResponse.getString("bidPrice"));
        return price;
    }
}
