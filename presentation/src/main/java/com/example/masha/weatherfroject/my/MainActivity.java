package com.example.masha.weatherfroject.my;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.data.entity.WeatherDay;
import com.example.data.entity.WeatherForecast;
import com.example.data.net.WeatherAPI;
import com.example.masha.weatherfroject.R;
import com.github.tianma8023.formatter.SunriseSunsetLabelFormatter;
import com.github.tianma8023.model.Time;
import com.github.tianma8023.ssv.SunriseSunsetView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener{
    final Context mainContext = this;
    TextView temp, city, pressure, discr, date, humidity, wind;
    ImageView imageView;
    LinearLayout forecast;
    MainActivityContract.Presenter presenter;
    WeatherAPI.ApiInterface api;
    // WeatherDay data;
    private int hourSunset, hourSunrise, minuteSunset, minuteSunrise;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Geolocation.SetUpLocationListener(this);
        PermissionListener dialogPermissionListener =
                DialogOnDeniedPermissionListener.Builder
                        .withContext(this)
                        .withTitle("Геолокация")
                        .withMessage("Доступ к геолокации необходим для определения погоды.")
                        .withButtonText(android.R.string.ok)
                        .build();

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(dialogPermissionListener)
                .check();
        // presenter = new MainActivityPresenter(this);
        imageView = findViewById(R.id.imageView);
        api = WeatherAPI.getClient().create(WeatherAPI.ApiInterface.class);
        temp = findViewById(R.id.textView);
        city = findViewById(R.id.cityTextView);
        pressure = findViewById(R.id.pressure);
        discr = findViewById(R.id.descriptioTV);
        date = findViewById(R.id.dateTextView);
        humidity = findViewById(R.id.humidity);
        forecast = findViewById(R.id.forecast);
        wind = findViewById(R.id.wind);
        swipeRefreshLayout=findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        getWeather(city);
        //    presenter.onActivityCreated();
        swipeRefreshLayout.setColorSchemeColors(
                  Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void sunriseSunset() {
        SunriseSunsetView sunriseSunsetView = findViewById(R.id.ssv);
        int sunriseHour = hourSunrise;
        int sunriseMinute = minuteSunrise;
        int sunsetHour = hourSunset;
        int sunsetMinute = minuteSunset;
        sunriseSunsetView.setSunriseTime(new Time(sunriseHour, sunriseMinute));

        sunriseSunsetView.setSunsetTime(new Time(sunsetHour, sunsetMinute));
        sunriseSunsetView.startAnimate();
        sunriseSunsetView.setLabelFormatter(new SunriseSunsetLabelFormatter() {
            @Override
            public String formatSunriseLabel(@NonNull Time sunrise) {
                return formatLabel(sunrise);
            }

            @Override
            public String formatSunsetLabel(@NonNull Time sunset) {
                return formatLabel(sunset);
            }

            private String formatLabel(Time time) {
                return String.format(Locale.getDefault(), "%02dh %02dm", time.hour, time.minute);
            }
        });
    }

    public void getWeather(View v) {

        final Double lat = Geolocation.imHere.getLatitude();
        final Double lng = Geolocation.imHere.getLongitude();
        final String units = "metric";
        final String key = WeatherAPI.KEY;


//         get weather for today
        retrofit2.Call<WeatherDay> callToday = api.getToday(lat, lng, units, key);
        callToday.enqueue(new retrofit2.Callback<WeatherDay>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(retrofit2.Call<WeatherDay> call, retrofit2.Response<WeatherDay> response) {
                WeatherDay data = response.body();

                if (response.isSuccessful()) {
                    city.setText(data.getCity() + ", " + data.getCountry());
                    String url = data.getIconUrl();
                    Glide.with(MainActivity.this).load(url).into(imageView);
                    temp.setText(data.getTempWithDegree());
                    pressure.setText(data.getPressureMmHg(data.getPressure())+" мм рт ст");
                    discr.setText(data.getDiscr());
                    minuteSunset = data.getSunset().get(Calendar.MINUTE);
                    hourSunset = data.getSunset().get(Calendar.HOUR_OF_DAY);
                    minuteSunrise = data.getSunrise().get(Calendar.MINUTE);
                    hourSunrise = data.getSunrise().get(Calendar.HOUR);
                    sunriseSunset();
                    humidity.setText(data.getHumidity());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("E dd MMMM yyyy");
                    dateFormat.setCalendar(data.getDate());
                    date.setText(dateFormat.format(data.getDate().getTime()));
                    wind.setText(data.getWind() + " m/s"+" "+data.deg(data.getDeg()));
                }
            }

            @Override
            public void onFailure(retrofit2.Call<WeatherDay> call, Throwable t) {
                temp.setText("-");
                Toast toast = Toast.makeText(getApplicationContext(), "Ошибка получения данных", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        retrofit2.Call<WeatherForecast> callForecast = api.getForecast(lat, lng, units, key);
        callForecast.enqueue(new retrofit2.Callback<WeatherForecast>() {
            @Override
            public void onResponse(retrofit2.Call<WeatherForecast> call, retrofit2.Response<WeatherForecast> response) {
                WeatherForecast data = response.body();

                if (response.isSuccessful()) {
                    Display display = getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int wight = size.x;
                    SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("E");
                    ViewGroup.LayoutParams paramsTextView = new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    ViewGroup.LayoutParams paramsImageView = new ViewGroup.LayoutParams(convertDPtoPX(40, MainActivity.this),
                            convertDPtoPX(40, MainActivity.this));

                    ViewGroup.LayoutParams paramsLinearLayout = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    forecast.removeAllViews();
                    for (WeatherDay day : data.getItems()) {
                        if (day.getDate().get(Calendar.HOUR_OF_DAY) == 15) {
                            @SuppressLint("DefaultLocale")
                            String date = String.format("%d.%d.%d %d:%d",
                                    day.getDate().get(Calendar.DAY_OF_MONTH),
                                    day.getDate().get(Calendar.WEEK_OF_MONTH),
                                    day.getDate().get(Calendar.YEAR),
                                    day.getDate().get(Calendar.HOUR_OF_DAY),
                                    day.getDate().get(Calendar.MINUTE)
                            );

                            LinearLayout childLayout = new LinearLayout(MainActivity.this);
                            childLayout.setLayoutParams(paramsLinearLayout);
                            childLayout.setOrientation(LinearLayout.VERTICAL);

                            TextView tvDay = new TextView(MainActivity.this);
                            tvDay.setTextColor(getResources().getColor(R.color.white));
                            String dayOfWeek = formatDayOfWeek.format(day.getDate().getTime());
                            tvDay.setText(dayOfWeek);

                            tvDay.setLayoutParams(paramsTextView);
                            childLayout.addView(tvDay);

                            // show image
                            ImageView ivIcon = new ImageView(MainActivity.this);
                            ivIcon.setLayoutParams(paramsImageView);
                            Glide.with(MainActivity.this).load(day.getIconUrl()).into(ivIcon);
                            childLayout.addView(ivIcon);

                            // show temp
                            TextView tvTemp = new TextView(MainActivity.this);
                            tvTemp.setTextColor(getResources().getColor(R.color.white));
                            tvTemp.setText(day.getTempWithDegree());
                            tvTemp.setGravity(Gravity.CENTER_HORIZONTAL);
                            tvTemp.setGravity(Gravity.CENTER);
                            tvTemp.setLayoutParams(paramsTextView);
                            childLayout.addView(tvTemp);

                            forecast.addView(childLayout);
                        }
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<WeatherForecast> call, Throwable t) {

            }
        });

    }

    public int convertDPtoPX(int dp, Context ctx) {
        float density = ctx.getResources().getDisplayMetrics().density;
        int px = (int) (dp * density);
        return px;

    }

    public void getList(View view) {
        Intent intent = new Intent(MainActivity.this, CityListActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.alpha);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Отменяем анимацию обновления
                swipeRefreshLayout.setRefreshing(false);
                getWeather(swipeRefreshLayout);
            }
        }, 4000);
    }

    }


//    @Override
//    public void setPresenter(MainActivityContract.Presenter presenter) {
//
//    }
//
//
//    @Override
//    public void getData(WeatherDay data) {
//        city.setText(data.getCity() + ", " + data.getCountry());
//        String url = data.getIconUrl();
//        Glide.with(MainActivity.this).load(url).into(imageView);
//        temp.setText(data.getTempWithDegree());
//        pressure.setText(data.getPressure());
//        discr.setText(data.getDiscr());
//        minuteSunset = data.getSunset().get(Calendar.MINUTE);
//        hourSunset = data.getSunset().get(Calendar.HOUR_OF_DAY);
//        minuteSunrise = data.getSunrise().get(Calendar.MINUTE);
//        hourSunrise = data.getSunrise().get(Calendar.HOUR);
//        sunriseSunset();
//        humidity.setText(data.getHumidity());
//        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd MMMM yyyy");
//        dateFormat.setCalendar(data.getDate());
//        date.setText(dateFormat.format(data.getDate().getTime()));
//        sunriseSunset();
//    }

