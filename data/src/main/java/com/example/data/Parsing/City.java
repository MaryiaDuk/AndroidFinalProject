package com.example.data.Parsing;

public class City {
    private Integer id;
    private String name;
    private String country;
    private Double lat;
    private Double lon;

    public City(){}
    public City(Integer id, String name, String country, Double lat, Double lon) {
        this.id=id;
        this.name = name;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }



    public void setLon(Double lon) {
        this.lon = lon;
    }

    public static class CityBuilder {
        private City city = new City();

        public CityBuilder addId(Integer id){
            city.id=id;
            return this;
        }

        public CityBuilder addName(String name) {
            city.name = name;
            return this;
        }

        public CityBuilder addCountry(String surname) {
            city.country = surname;
            return this;
        }

        public CityBuilder addALat(Double lat) {
            city.lat = lat;
            return this;
        }

        public CityBuilder addLon(Double lon) {
            city.lon = lon;
            return this;
        }


        public City create() {
            return city;
        }

    }
}
