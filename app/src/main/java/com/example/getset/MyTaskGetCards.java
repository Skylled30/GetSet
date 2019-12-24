package com.example.getset;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MyTaskGetCards extends AsyncTask<ArrayList<Cards>, String, TakeCardsResponse> {

    @Override
    protected TakeCardsResponse doInBackground(ArrayList<Cards>... arrayLists) {
        int token = arrayLists[0].get(3).count;
        String data = getJSONFromList(arrayLists[0], token);
        Log.d("mylog", "convert " + data);
        try {
            String set_server_url = "http://194.176.114.21:8050";
            URL url = new URL(set_server_url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            OutputStream out = urlConnection.getOutputStream();
            out.write(data.getBytes());
            InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
            Gson gson = new Gson();
            TakeCardsResponse takeCardsResponse = gson.fromJson(inputStreamReader, TakeCardsResponse.class);
            Log.d("mylog", "check cards " + takeCardsResponse.toString());
            return takeCardsResponse;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getJSONFromList(ArrayList<Cards> cards, int token){
        String result = "{\"action\": \"take_set\", \"token\": " + token + ", \"cards\":[";
        for (int i = 0; i < 3; i++) {
            if(i != 2) {
                result += "{\"count\": " + cards.get(i).count + ", \"color\": "+ cards.get(i).color + ", \"shape\":" + cards.get(i).shape + ", \"fill\":" + cards.get(i).fill + "},";
            } else {
                result += "{\"count\": " + cards.get(i).count + ", \"color\": "+ cards.get(i).color + ", \"shape\":" + cards.get(i).shape + ", \"fill\":" + cards.get(i).fill + "}";
            }
        }
        result += "]}";
        return result;
    }
}
