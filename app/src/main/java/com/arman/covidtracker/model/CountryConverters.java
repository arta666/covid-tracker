package com.arman.covidtracker.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CountryConverters {

    @TypeConverter
    public static List<Country> fromString(String value) {
        Type listType = new TypeToken<List<Country>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<Country> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
