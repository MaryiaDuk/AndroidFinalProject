package com.example.masha.weatherfroject.my;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


public class Geolocation implements LocationListener {

    static Location imHere; // здесь будет всегда доступна самая последняя информация о местоположении пользователя.


    @SuppressLint("MissingPermission")
    public static void SetUpLocationListener(Context context) // это нужно запустить в самом начале работы программы
    {
        context.getApplicationContext();
        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);

        final LocationListener locationListener = new Geolocation();

        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000,
                10,
                locationListener);

        imHere = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

        @Override
        public void onLocationChanged (Location location){
        imHere = location;

    }

        @Override
        public void onStatusChanged (String s,int i, Bundle bundle){

    }

        @Override
        public void onProviderEnabled (String s){

    }

        @Override
        public void onProviderDisabled (String s){

    }
    }
