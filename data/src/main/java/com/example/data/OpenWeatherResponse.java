package com.example.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OpenWeatherResponse {

    @SerializedName("coord")
    public Coord coord;

    @SerializedName("weather")
    public List<Weather> weather = new ArrayList<>();

    @SerializedName("base")
    public String base;

    @SerializedName("main")
    public MainData main;

    @SerializedName("wind")
    public Wind wind;

    @SerializedName("clouds")
    public Clouds clouds;

    @SerializedName("rain")
    public Rain rain;

    @SerializedName("snow")
    public Snow snow;

    @SerializedName("dt")
    public Long dt;

    @SerializedName("sys")
    public Sys sys;

    @SerializedName("visibility")
    public Integer visibility;

    @SerializedName("id")
    public Integer cityId;

    @SerializedName("name")
    public String cityName;

    @SerializedName("cod")
    public int cod;

    public static class Clouds {
        @SerializedName("all")
        public Integer coverage;
    }

    public static class MainData {
        @SerializedName("temp")
        public Double temperature;

        @SerializedName("pressure")
        public Double pressure;

        @SerializedName("humidity")
        public Double humidity;

        @SerializedName("temp_min")
        public Double temperatureMin;

        @SerializedName("temp_max")
        public Double temperatureMax;

        @SerializedName("sea_level")
        public Double seaLevel;

        @SerializedName("grnd_level")
        public Double groundLevel;
    }

    public static class Rain {
        @SerializedName("3h")
        public Integer volume;
    }

    public static class Snow {
        @SerializedName("3h")
        public Integer volume;
    }

    public static class Sys {
        @SerializedName("type")
        public Integer type;

        @SerializedName("id")
        public Integer id;

        @SerializedName("message")
        public Double message;

        @SerializedName("country")
        public String country;

        @SerializedName("sunrise")
        public Long sunrise;

        @SerializedName("sunset")
        public Long sunset;
    }

    public static class Weather {
        @SerializedName("id")
        public Integer id;

        @SerializedName("main")
        public String main;

        @SerializedName("description")
        public String description;

        @SerializedName("icon")
        public String icon;
    }

    public static class Wind {
        @SerializedName("speed")
        public Double speed;

        @SerializedName("deg")
        public Integer deg;
    }

    public static class Coord {

        @SerializedName("lon")
        public Double lon;

        @SerializedName("lat")
        public Double lat;

    }

    public Coord getCoord() {
        return coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }

    public MainData getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Rain getRain() {
        return rain;
    }

    public Snow getSnow() {
        return snow;
    }

    public Long getDt() {
        return dt;
    }

    public Sys getSys() {
        return sys;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public Integer getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public int getCod() {
        return cod;
    }
}
