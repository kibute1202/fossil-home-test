package com.sdt.fossilhometest.di.module;


import com.sdt.fossilhometest.data.source.user.UserRepository;
import com.sdt.fossilhometest.data.source.user.UserRepositoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoriesModule {

    @Binds
    @Singleton
    public abstract UserRepository bindUserRepository(UserRepositoryImpl userRepository);

}
