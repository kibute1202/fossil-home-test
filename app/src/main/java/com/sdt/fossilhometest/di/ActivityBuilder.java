package com.sdt.fossilhometest.di;

import com.sdt.fossilhometest.ui.sof.UsersActivity;
import com.sdt.fossilhometest.ui.sof.UsersModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {UsersModule.class})
    abstract UsersActivity bindUsersActivity();

}
