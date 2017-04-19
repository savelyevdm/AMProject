package ru.developsdm.amproject.app;

import android.app.Application;
import android.graphics.Bitmap;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import lombok.Getter;
import ru.developsdm.amproject.di.component.ApplicationComponent;
import ru.developsdm.amproject.di.component.DaggerApplicationComponent;
import ru.developsdm.amproject.di.module.ApplicationModule;

/**
 * Android custom application class.
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

public class AMApp extends Application {

    @Getter
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize Dagger 2.
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        // create Picasso.Builder object
        Picasso.Builder picassoBuilder = new Picasso.Builder(this)
                .memoryCache(new LruCache(20 * 1024 * 1024)) // 20 Mb
                .indicatorsEnabled(true)
                .loggingEnabled(true);
        // Picasso.Builder creates the Picasso object to do the actual requests
        Picasso picasso = picassoBuilder.build();
        picasso.setIndicatorsEnabled(true);
        picasso.setLoggingEnabled(true);
        // set the global instance to use this Picasso object
        // all following Picasso (with Picasso.with(Context context) requests will use this Picasso object
        // you can only use the setSingletonInstance() method once!
        try {
            Picasso.setSingletonInstance(picasso);
        } catch (IllegalStateException ignored) {
            // Picasso instance was already set
            // cannot set it after Picasso.with(Context) was already in use
        }
    }

}
