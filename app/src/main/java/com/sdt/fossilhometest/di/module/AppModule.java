package com.sdt.fossilhometest.di.module;

import android.content.Context;

import androidx.room.Room;

import com.sdt.fossilhometest.data.local.db.AppDatabase;
import com.sdt.fossilhometest.utils.Constants;
import com.sdt.fossilhometest.utils.rx.AppSchedulerProvider;
import com.sdt.fossilhometest.utils.rx.SchedulerProvider;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {
    RepositoriesModule.class
})
public class AppModule {

    @Provides
    @Singleton
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    @Named(Constants.DB_NAME)
    String provideDatabaseName() {
        return Constants.DB_NAME;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context, @Named(Constants.DB_NAME) String databaseName) {
        return Room.databaseBuilder(context, AppDatabase.class, databaseName)
            .fallbackToDestructiveMigration()
            .build();
    }

}
