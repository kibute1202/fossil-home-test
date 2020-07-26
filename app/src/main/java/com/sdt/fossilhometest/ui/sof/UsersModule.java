package com.sdt.fossilhometest.ui.sof;

import androidx.lifecycle.ViewModel;

import com.sdt.fossilhometest.di.ViewModelBuilder;
import com.sdt.fossilhometest.di.ViewModelKey;

import dagger.Binds;
import dagger.MapKey;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module(includes = {
    ViewModelBuilder.class
})
public abstract class UsersModule {

    @Binds
    @IntoMap
    @ViewModelKey(UsersViewModel.class)
    abstract ViewModel bindUsersViewModel(UsersViewModel viewModel);

}
