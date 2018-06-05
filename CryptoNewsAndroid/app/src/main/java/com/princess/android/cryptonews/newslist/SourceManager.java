package com.princess.android.cryptonews.newslist;


import android.content.Context;
import android.content.SharedPreferences;

import com.princess.android.cryptonews.AppController;
import com.princess.android.cryptonews.util.PreferenceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

/**
 * Manage saving and retrieving data sources from disk
 * */

public class SourceManager {


    static PreferenceUtils preferenceUtils = new PreferenceUtils(AppController.getContextInstance());

    public static final String SOURCE_DATE_ASCENDING = "SOURCE_DATE_ASCENDING";
    public static final String SOURCE_DATE_DESCENDING = "SOURCE_DATE_DESCENDING";
    public static final String SOURCE_FIRST_NEWS = "SOURCE_FIRST_NEWS";
    public static final String SOURCE_SECOND_NEWS = "SOURCE_SECOND_NEWS";
    public static final String SOURCE_THIRD_NEWS = "SOURCE_THIRD_NEWS";
    public static final String SOURCE_FOURTH_NEWS = "SOURCE_FOURTH_NEWS";


    private static final String SOURCES_PREF = "SOURCES_PREF";
    private static final String KEY_SOURCES = "KEY_SOURCES";


    public static List<Source> getSources (Context context) {

        SharedPreferences prefs = context.getSharedPreferences(SOURCES_PREF, Context.MODE_PRIVATE);

        Set<String> sourceKeys = prefs.getStringSet(KEY_SOURCES, null);
        if (sourceKeys == null) {
            setupDefaultSources(context, prefs.edit());
            return getDefaultSources();
        }

        List<Source> sources = new ArrayList<>(sourceKeys.size());
        for (String sourceKey : sourceKeys){
            sources.add(getSource(context, sourceKey, prefs.getBoolean(sourceKey, false)));
        }

        Collections.sort(sources, new Source.SourceComparator());
        return  sources;
    }

    public static void updateSource(Source source, Context context){
        SharedPreferences.Editor editor =
                context.getSharedPreferences(SOURCES_PREF, Context.MODE_PRIVATE).edit();
        editor.putBoolean(source.key, source.active);
        editor.apply();
    }


    private static void setupDefaultSources(Context context, SharedPreferences.Editor edit) {

        ArrayList<Source> defaultSources = getDefaultSources();
        Set<String> keys = new HashSet<>(defaultSources.size());
        for (Source source : defaultSources){
            keys.add(source.key);
            edit.putBoolean(source.key, source.active);
        }

        edit.putStringSet(KEY_SOURCES, keys);
        edit.commit();
    }


    private  static  ArrayList<Source> getDefaultSources(){
        ArrayList<Source> defaultSources = new ArrayList<>(6);
        defaultSources.add(new Source.DateSource(SOURCE_DATE_ASCENDING, 100, "Date Ascending",true ));
        defaultSources.add(new Source.DateSource(SOURCE_DATE_DESCENDING, 101, "Date Descending", false));
        defaultSources.add(new Source.NewsSource(SOURCE_FIRST_NEWS, 102, preferenceUtils.getFirstTitle(), false));
        defaultSources.add(new Source.NewsSource(SOURCE_SECOND_NEWS, 103, preferenceUtils.getSecondTitle(), false));
        defaultSources.add(new Source.NewsSource(SOURCE_THIRD_NEWS, 104, preferenceUtils.getThirdTitle(), false));
        defaultSources.add(new Source.NewsSource(SOURCE_FOURTH_NEWS, 105, preferenceUtils.getFourthTitle(), false));

        return defaultSources;
    }


    private static @Nullable Source getSource (Context context, String key, boolean active){
        for (Source source : getDefaultSources()){
            if (source.key.equals(key)){
                source.active = active;
                return  source;
            }
        }
        return  null;
    }

    public static void addSource(Source toAdd, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SOURCES_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> sourceKeys = prefs.getStringSet(KEY_SOURCES, null);
        sourceKeys.add(toAdd.key);
        editor.putStringSet(KEY_SOURCES, sourceKeys);
        editor.putBoolean(toAdd.key, toAdd.active);
        editor.apply();
    }

    public static void removeSource(Source removing, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SOURCES_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> sourceKeys = prefs.getStringSet(KEY_SOURCES, null);
        sourceKeys.remove(removing.key);
        editor.putStringSet(KEY_SOURCES, sourceKeys);
        editor.remove(removing.key);
        editor.apply();
    }
}
