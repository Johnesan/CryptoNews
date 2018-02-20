package com.princess.android.cryptonews.NewWebsite.view.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.settings.SettingsActivity;

public class NewsWebPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web_page);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_latest_news, menu);
        return  true;
       }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings){

            return  true;

        }

        return  super.onOptionsItemSelected(item);
    }


}
