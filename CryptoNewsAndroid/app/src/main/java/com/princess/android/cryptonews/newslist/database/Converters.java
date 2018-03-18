package com.princess.android.cryptonews.newslist.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.princess.android.cryptonews.model.Embedded;
import com.princess.android.cryptonews.model.Guid;
import com.princess.android.cryptonews.model.Title;

import java.lang.reflect.Type;

/**
 * Created by Princess on 3/18/2018.
 */

public class Converters {

    static Gson gson = new Gson();

    //TypeConverter for Title
    @TypeConverter
    public static Title stringToTitle(String titleValue) {
        Type titleListType = new TypeToken<Title>() {}.getType();
        return gson.fromJson(titleValue, titleListType);
    }

    @TypeConverter
    public static String titleToString(Title titleList) {
        String titleJson = gson.toJson(titleList);
        return titleJson;
    }

    //TypeConverter for Guid
    @TypeConverter
    public static Guid stringToGuid(String guidValue) {
        Type guidListType = new TypeToken<Guid>() {}.getType();
        return gson.fromJson(guidValue, guidListType);
    }

    @TypeConverter
    public static String guidToString(Guid guidList) {
        String guidJson = gson.toJson(guidList);
        return guidJson;
    }

    //TypeConverter for Embedded
    @TypeConverter
    public static Embedded stringToEmbedded(String embeddedValue) {
        Type embeddedListType = new TypeToken<Embedded>() {}.getType();
        return gson.fromJson(embeddedValue, embeddedListType);
    }

    @TypeConverter
    public static String embeddedToString(Embedded embeddedList) {
        String embeddedJson = gson.toJson(embeddedList);
        return embeddedJson;
    }
}
