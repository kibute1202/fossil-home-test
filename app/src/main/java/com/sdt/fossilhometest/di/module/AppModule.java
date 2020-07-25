package com.sdt.fossilhometest.di.module;

import com.sdt.fossilhometest.utils.rx.AppSchedulerProvider;
import com.sdt.fossilhometest.utils.rx.SchedulerProvider;

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

}
