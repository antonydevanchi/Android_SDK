package ru.usedesk.sample;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ServiceLocator.init(this);
    }
}
