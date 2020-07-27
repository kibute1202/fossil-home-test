package com.sdt.fossilhometest.ui.sof.reputation;

import androidx.lifecycle.ViewModel;

import com.sdt.fossilhometest.di.ViewModelBuilder;
import com.sdt.fossilhometest.di.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module(includes = {
    ViewModelBuilder.class
})
public abstract class ReputationModule {

    @Binds
    @IntoMap
    @ViewModelKey(ReputationViewModel.class)
    abstract ViewModel bindViewModel(ReputationViewModel viewModel);
}
