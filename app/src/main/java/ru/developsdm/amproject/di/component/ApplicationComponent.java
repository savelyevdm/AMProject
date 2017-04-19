package ru.developsdm.amproject.di.component;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import dagger.Component;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import ru.developsdm.amproject.app.RuntimeStorage;
import ru.developsdm.amproject.di.module.ApplicationModule;
import ru.developsdm.amproject.di.scope.PerApplication;
import ru.developsdm.amproject.model.web.RestAPI;

/**
 * Application component.
 * <p/>
 * Created by Daniil Savelyev in 2017.
 */
@PerApplication
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context context();

    RuntimeStorage runtimeStorage();

    SharedPreferences sharedPreferences();

//    RetrofitProvider retrofitProvider();

    Gson gson();

    HttpLoggingInterceptor httpLoggingInterceptor();

    OkHttpClient okHttpClient();

    Retrofit retrofit();

    RestAPI restAPI();

}