package com.sdt.fossilhometest.di;

import com.sdt.fossilhometest.ui.sof.UsersActivity;
import com.sdt.fossilhometest.ui.sof.UsersModule;
import com.sdt.fossilhometest.ui.sof.reputation.ReputationActivity;
import com.sdt.fossilhometest.ui.sof.reputation.ReputationModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {UsersModule.class})
    abstract UsersActivity bindUsersActivity();

    @ContributesAndroidInjector(modules = {ReputationModule.class})
    abstract ReputationActivity bindReputationHistoryActivity();

}
