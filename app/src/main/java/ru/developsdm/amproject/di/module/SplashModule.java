package ru.developsdm.amproject.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import ru.developsdm.amproject.app.RuntimeStorage;
import ru.developsdm.amproject.di.scope.PerActivity;
import ru.developsdm.amproject.model.web.RestAPI;
import ru.developsdm.amproject.presenter.SplashPresenter;
import ru.developsdm.amproject.view.SplashActivity;
import ru.developsdm.amproject.view.contract.ISplashView;

/**
 * Module. Can provide objects for scopes.
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

@Module
public class SplashModule {

    private final SplashActivity activity;

    public SplashModule(SplashActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    ISplashView provideView() {
        return this.activity;
    }

    @Provides
    @PerActivity
    SplashPresenter providePresenter(ISplashView view, Context context,
                                     RuntimeStorage runtimeStorage, SharedPreferences sp,
                                     RestAPI restAPI, Gson gson) {
        return new SplashPresenter(view, context, runtimeStorage, sp, restAPI, gson);
    }

}
