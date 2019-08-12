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
            prices.add(kraken_price());
            prices.add(luno_price());


        } catch (Exception e) {

        }
        return prices;
    }

    protected void onPostExecute(ArrayList feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }

    private float kraken_price() throws Exception {
        String url = "https://api.kraken.com/0/public/Ticker?pair=XBTEUR";
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
        //print in String
        System.out.println(response.toString());
        JSONObject myResponse = new JSONObject(response.toString());
        System.out.println(myResponse.toString());
        JSONObject result = new JSONObject(myResponse.getString("result"));
        System.out.println(result.toString());
        JSONObject pair = new JSONObject(result.getString("XXBTZEUR"));
        System.out.println(pair.toString());
        JSONArray list = pair.getJSONArray("a");
        System.out.println(list.toString());
        float price = Float.parseFloat((String)list.get(0));
        return price;
        //return 10000.00f;
    }

    private float luno_price() throws Exception {
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
        //print in String
        System.out.println(response.toString());
        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response.toString());
        float price = Float.parseFloat(myResponse.getString("bid"));
        return price;
        //return 180000.00f;
    }
}
