package com.example.data.Parsing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ParsingJSON {
    public CityList makeList() {
        URL url = null;
        try {
            url = new URL("https://raw.githubusercontent.com/MaryiaDuk/OnlineBase/master/belarus.json");
            HttpURLConnection connection = null;

            connection = (HttpURLConnection) url.openConnection();

            connection.connect();

            Gson gson = new GsonBuilder().setPrettyPrinting()
                    .registerTypeAdapter(CityList.class, new CityListDeserializer()).create();

            CityList cityList = null;

            cityList = gson.fromJson(new BufferedReader
                    (new InputStreamReader(connection.getInputStream())), CityList.class);
            connection.disconnect();
            return cityList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
