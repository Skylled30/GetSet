package com.example.getset;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class GameActivity extends Activity {

    MyTask asyncTask;
    String name;
    MyTaskGetCards myTaskGetCards;
    int token;
    ArrayList<Cards> cardsSet;
    TakeCardsResponse takeCardsResponse;
    MyCanvas myCanvas;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    String[] cardsArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_game);
        Intent intent = getIntent();
        listView = findViewById(R.id.listview);
        name = intent.getStringExtra("name");
        Toast.makeText(this, name, Toast.LENGTH_LONG).show();
        myCanvas = new MyCanvas(this);
        cardsSet = new ArrayList<>();
        token = -1;
        asyncTask = new MyTask();
        try {
            //register
            register();
            //get cards
            String responce = getCards();
            setDataListView();
            //check set
//            if(responce !=  null) {
//                checkSet();
//            }
            Log.d("mylog", "succesfull");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void convertDataFromList(){
        cardsArray = new String[cardsSet.size()];
        for (int i = 0; i < cardsSet.size(); i++) {
            cardsArray[i] = cardsSet.get(i).toString();
        }
    }

    public void setDataListView(){
        convertDataFromList();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, cardsArray);
        listView.setAdapter(arrayAdapter);
    }

    private String checkSet() throws ExecutionException, InterruptedException {
        String result = "error";
        myTaskGetCards = new MyTaskGetCards();
        cardsSet = new ArrayList<>();
        cardsSet.add(new Cards(1, 1, 1, 1));
        cardsSet.add(new Cards(3, 3, 3, 3));
        cardsSet.add(new Cards(1, 2, 3, 4));
        cardsSet.add(new Cards(token, 0, 0, 0));
        myTaskGetCards.execute(cardsSet);
        takeCardsResponse = myTaskGetCards.get();
        if(takeCardsResponse.status.equals("ok")){
            result = "ok";
        }
        Toast.makeText(this, myTaskGetCards.toString(), Toast.LENGTH_LONG).show();
        return result;
    }

    private String register() throws ExecutionException, InterruptedException {
        String result = "error";
        asyncTask.execute(new String[]{name, "register"});
        String responce = asyncTask.get();
        if(responce != null) {
            token = Integer.valueOf(responce);
            result = "ok";
            Toast.makeText(this, responce, Toast.LENGTH_SHORT).show();
        }
        Log.d("mylog", "mainActivity register - " + responce);
        return result;
    }

    private String getCards() throws ExecutionException, InterruptedException {
        String responce = "";
        if(token !=  -1) {
            asyncTask = new MyTask();
            asyncTask.execute(new String[]{String.valueOf(token), "fetch_cards"});
            responce = asyncTask.get();
            myCanvas.setCards(cardsSet);
            myCanvas.invalidate();
        }
        Log.d("mylog", "mainActivity get cards - " + responce);
        return responce;
    }

    public void setCardsSet(ArrayList<Cards> cardsSet) {
        this.cardsSet = cardsSet;
    }

    public ArrayList<Cards> getCardsSet() {
        return cardsSet;
    }

    public class MyTask extends AsyncTask<String[], Void, String> {

        private String data;
        ArrayList<Cards> cards;
        String request;

        @Override
        protected String doInBackground(String[]... strings){
            request = strings[0][1];
            if(strings[0][1].equals("register")) {
                data = "{\"action\": \"register\", \"nickname\":\"" + strings[0][0] + "\" }";
            } else if(strings[0][1].equals("fetch_cards")) {
                data = "{\"action\": \"fetch_cards\", \"token\":" + Integer.valueOf(strings[0][0]) + "}";
            }
            Log.d("mylog before try", data);
            try {
                String set_server_url = "http://194.176.114.21:8050";
                URL url = new URL(set_server_url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                OutputStream out = urlConnection.getOutputStream();
                Log.d("mylog", data);
                out.write(data.getBytes());
                InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                Gson gson = new Gson();
                if(strings[0][1].equals("register")) {
                    RegisterResponse response = gson.fromJson(inputStreamReader, RegisterResponse.class);
                    urlConnection.disconnect();
                    Log.d("mylog", "finish " + response);
                    return String.valueOf(response.token);
                } else if(strings[0][1].equals("fetch_cards")) {
                    GetCards response = gson.fromJson(inputStreamReader, GetCards.class);
                    cards = response.getCards();
                    urlConnection.disconnect();
                    setCardsSet(cards);
                    Log.d("mylog", "finish " + response);
                    return response.toString();
                }
            } catch (Exception e){
                e.printStackTrace ();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }
}

