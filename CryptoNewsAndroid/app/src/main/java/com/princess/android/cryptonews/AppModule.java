package com.princess.android.cryptonews;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

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


     @Singleton @Provides
     AppExecutors providesExecutors () {
         return appExecutors;
     }

     @Singleton
     @Provides
     static Context providesContext() {
         return AppController.getContextInstance();
     }


    /**
   * provide the database to Use
   * */
    @Provides @Singleton
    NewsDatabase provideDb(Context  context){
        return Room.databaseBuilder(
                context,
                NewsDatabase.class,
                "newsDatabase")
                .addMigrations(MIGRATION_1_2)
                .build();
    }

    //class to get access to DAO
    @Singleton  @Provides
    NewsDao provideNewsDao(NewsDatabase newsDatabase){
        return newsDatabase.newsDao();
    }


    static  final Migration MIGRATION_1_2 =
            new Migration(1, 2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                database.execSQL(" ALTER TABLE news " + " ADD COLUMN favorite INTEGER NOT NULL DEFAULT 0 ");
                }
            };
}



