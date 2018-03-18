package com.princess.android.cryptonews;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;
import com.princess.android.cryptonews.di.viewModelModule;
import com.princess.android.cryptonews.newslist.database.NewsDao;
import com.princess.android.cryptonews.newslist.database.NewsDatabase;

import dagger.Module;
import dagger.Provides;

/**
 * A generic class to Provide Our Databse instances and Dao instances
 */


@Module (includes = viewModelModule.class  )
@Singleton
public class AppModule {
    AppExecutors appExecutors;

    public AppModule() {
        appExecutors = new AppExecutors();
    }


     /**
    * provide the database to Use
    * */

     @Singleton @Provides
     AppExecutors providesExecutors () {
         return appExecutors;
     }

     @Singleton
     @Provides
     static Context providesContext() {
         return AppController.getContextInstance();
     }

    @Provides @Singleton
    NewsDatabase provideDb(Context  context){
        return Room.databaseBuilder(
                context,
                NewsDatabase.class,
                "newsDatabase.db")
                .build();
    }

    //class to get access to DAO
    @Singleton  @Provides
    NewsDao provideUserClubsDao (NewsDatabase newsDatabase){
        return newsDatabase.newsDao();
    }
}



