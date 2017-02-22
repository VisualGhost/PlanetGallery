package com.ciklumtask.di;

import com.ciklumtask.loader.PageLoader;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(PageLoader pageLoader);
}
