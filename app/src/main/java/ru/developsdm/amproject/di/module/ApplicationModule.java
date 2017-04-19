package ru.developsdm.amproject.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.developsdm.amproject.app.AMApp;
import ru.developsdm.amproject.app.App;
import ru.developsdm.amproject.app.RuntimeStorage;
import ru.developsdm.amproject.di.scope.PerApplication;
import ru.developsdm.amproject.model.web.RestAPI;

/**
 * Module. Can provide objects for scopes.
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

@Module
public class ApplicationModule {

    private final AMApp application;

    public ApplicationModule(AMApp application) {
        this.application = application;
    }

    @Provides
    @PerApplication
    Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    @PerApplication
    RuntimeStorage provideRuntimeStorage() {
        return new RuntimeStorage();
    }

    @Provides
    @PerApplication
    SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    // --->>> Start: Retrofit objects

    @Provides
    @PerApplication
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        //builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        //builder.excludeFieldsWithoutExposeAnnotation();
        return builder.create();
    }

    @Provides
    @PerApplication
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    @Provides
    @PerApplication
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor logging) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                String authorizationValue = encodeCredentialsForBasicAuthorization();

                // Customize the request
                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Authorization", authorizationValue)
                        .method(original.method(), original.body())
                        .build();

                Response response = chain.proceed(request);

                // Customize or return the response
                return response;
            }
        });

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        return httpClient.build();
    }

    private String encodeCredentialsForBasicAuthorization() {
        final String userAndPassword = "loginarea" + ":" + "passarea";
        return "Basic " + Base64.encodeToString(userAndPassword.getBytes(), Base64.NO_WRAP);
    }

    @Provides
    @PerApplication
    Retrofit provideRetrofit(OkHttpClient client, Gson gson) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(App.ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson));
        return retrofitBuilder.build();
    }

    @Provides
    @PerApplication
    RestAPI provideRestAPI(Retrofit retrofit) {
        return retrofit.create(RestAPI.class);
    }

    // --->>> End: Retrofit objects


}
