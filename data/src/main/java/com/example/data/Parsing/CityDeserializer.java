package com.example.data.Parsing;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class CityDeserializer implements JsonDeserializer<City> {
    @Override
    public City deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        Integer id = object.get("id").getAsInt();
        String name = object.get("name").getAsString();
        String country = object.get("country").getAsString();
        Double lat = object.get("lat").getAsDouble();
        Double lon = object.get("lon").getAsDouble();


        return new City.CityBuilder().addName(name).addId(id).addCountry(country).addALat(lat).addLon(lon).create();
    }
}
