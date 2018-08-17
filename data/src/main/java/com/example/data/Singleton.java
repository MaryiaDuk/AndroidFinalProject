package com.example.data;

import com.example.data.Parsing.CityList;
import com.example.data.Parsing.ParsingJSON;

public class Singleton {
    private static Singleton instance;
    private CityList list;

    private Singleton() {
        start();
        list = new CityList();
    }

    public CityList getList() {
        return list;
    }

    private void start() {
        new Thread(() -> list = new ParsingJSON().makeList()).start();
    }

    public static Singleton getState() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
