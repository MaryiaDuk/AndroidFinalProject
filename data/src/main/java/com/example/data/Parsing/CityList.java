package com.example.data.Parsing;

import java.util.ArrayList;
import java.util.List;

public class CityList {
    private List<City> cities = new ArrayList<>();

    public CityList() {
    }

    public List<City> getCities() {
        return cities;
    }

    public void setPeople(List<City> cities) {
        this.cities = cities;
    }

    public CityList(List<City> cities) {
        this.cities = cities;
    }
    public static class CityListBuilder {
        private CityList cityList = new CityList();

        public CityListBuilder addPeople(List<City> jsonPerson) {
            cityList.setPeople(jsonPerson);
            return this;
        }

        public CityList create() {
            return cityList;
        }
    }

}
