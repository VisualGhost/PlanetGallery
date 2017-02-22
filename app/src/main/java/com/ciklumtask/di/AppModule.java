package com.ciklumtask.di;


import com.ciklumtask.networking.ApiClient;
import com.ciklumtask.networking.ApiClientImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    public ApiClient provideApiClient() {
        return new ApiClientImpl();
    }


}
