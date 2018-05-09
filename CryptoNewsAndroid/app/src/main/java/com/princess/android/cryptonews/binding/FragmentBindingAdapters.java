package com.princess.android.cryptonews.binding;

import android.databinding.BindingAdapter;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.model.Embedded;
import com.princess.android.cryptonews.util.GlideApp;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by numb3rs on 3/7/18.
 */

public class FragmentBindingAdapters {
    final Fragment fragment;

    @Inject
    public FragmentBindingAdapters(Fragment fragment) {
        this.fragment = fragment;
    }

    @BindingAdapter("imageUrl")
    public void bindImage(ImageView imageView, Embedded embedded) {

        if (embedded.getWpFeaturedmedia().get(0).getMediaDetails() != null) {
            if (embedded.getWpFeaturedmedia().get(0)
                    .getMediaDetails().getSizes().getMediumLarge() != null) {

                String thumbnail_url = embedded.getWpFeaturedmedia().get(0)
                        .getMediaDetails().getSizes().getMediumLarge().getSourceUrl();
                GlideApp.with(fragment.getContext())
                        .load(thumbnail_url)
                        .placeholder(R.mipmap.placeholder)
                        .into(imageView);




            } else {
                if (embedded.getWpFeaturedmedia().get(0)
                        .getMediaDetails().getSizes().getMedium() != null) {

                    String thumbnail_url = embedded.getWpFeaturedmedia().get(0)
                            .getMediaDetails().getSizes().getMedium().getSourceUrl();
                    GlideApp.with(fragment.getContext())
                            .load(thumbnail_url)
                            .placeholder(R.mipmap.placeholder)
                            .into(imageView);
                }
            }
        } else {
            Glide.with(fragment.getContext())
                    .load(R.mipmap.placeholder)
                    .into(imageView);
        }

    }


    @BindingAdapter("bindDate")
    public void bindDate(RelativeTimeTextView textView, String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss");
            Date mDate = null;
            long timeInMilliseconds = 0;
            try {
                mDate = simpleDateFormat.parse(date);

                timeInMilliseconds = mDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            textView.setReferenceTime(timeInMilliseconds);

        } catch (NumberFormatException nfe) {

        }


    }


    @BindingAdapter("bindTitle")
    public void bindTitle(TextView textView, String title) {
        //Replace ASCII codes with proper Characters
        String formatTitle = String.valueOf(Html.fromHtml(title));
        textView.setText(formatTitle);
    }


    @BindingAdapter("bindWebsite")
    public void bindWebsite(TextView textView, String websiteName) {
        /** Set the website
         //         *
         //         */
        try {
            URL url = new URL(websiteName);
            String host = url.getHost();
            String[] array = host.split("\\.");
            if (array[0].equals("www")) {
                textView.setText(array[1].toLowerCase());
            } else
                textView.setText(array[0].toLowerCase());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}




