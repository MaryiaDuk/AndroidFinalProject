package com.example.data.Parsing;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

class CityListDeserializer implements JsonDeserializer<CityList> {

    @Override
    public CityList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        JsonArray array = object.getAsJsonArray();
        return new CityList.CityListBuilder().addPeople(makeList(array)).create();
    }
    private List<City> makeList(JsonArray array) {
        JsonDeserializationContext jsonDeserializationContext = null;
        List<City> cities = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonElement element = array.get(i);
            cities.add(new CityDeserializer().deserialize(element, City.class, jsonDeserializationContext));
        }

        return cities;
    }
}
