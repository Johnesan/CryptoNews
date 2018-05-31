package com.princess.android.cryptonews.newslist;

import android.support.annotation.DrawableRes;

import com.princess.android.cryptonews.R;

import java.util.Comparator;

/**
 * Created by numb3rs on 5/30/18.
 */

public class Source {


    public  final String key;
    public  final int sortOrder;
    public final String name ;
    public  final @DrawableRes int iconRes;
    public  boolean active;


    public Source(String key,
                  int sortOrder,
                  String name,
                  int iconRes,
                  boolean active) {
        this.key = key;
        this.sortOrder = sortOrder;
        this.name = name;
        this.iconRes = iconRes;
        this.active = active;
    }

    public boolean isSwipeDismissable() {
        return false;
    }

    public  static  class DateSource extends  Source {
        public DateSource(String key,
                          int sortOrder,
                          String nam,
                          boolean active) {
            super(key, sortOrder, nam, R.drawable.ic_noun_1745778_cc, active);
        }
    }

    public  static  class  NewsSource extends  Source {
        public NewsSource(String key,
                          int sortOrder,
                          String nam,
                          boolean active) {
            super(key, sortOrder, nam, R.drawable.ic_news_read, active);
        }
    }

    public static class SourceComparator implements Comparator<Source> {

        @Override
        public int compare(Source lhs, Source rhs) {
            return lhs.sortOrder - rhs.sortOrder;
        }
    }
}
