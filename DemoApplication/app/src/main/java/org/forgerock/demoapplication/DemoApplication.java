package org.forgerock.demoapplication;

import android.app.Application;

import org.forgerock.android.auth.FRAuth;
import org.forgerock.android.auth.Logger;

public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.set(Logger.Level.DEBUG);
        FRAuth.start(this);
    }
}
