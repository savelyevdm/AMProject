package ru.developsdm.amproject.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import ru.developsdm.amproject.app.RuntimeStorage;
import ru.developsdm.amproject.di.scope.PerActivity;
import ru.developsdm.amproject.model.web.RestAPI;
import ru.developsdm.amproject.presenter.MainPresenter;
import ru.developsdm.amproject.view.MainActivity;
import ru.developsdm.amproject.view.contract.IMainView;

/**
 * Module. Can provide objects for scopes.
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

@Module
public class MainModule {

    private final MainActivity activity;

    public MainModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    IMainView provideView() {
        return this.activity;
    }

    @Provides
    @PerActivity
    MainPresenter providePresenter(IMainView view, Context context,
                                   RuntimeStorage runtimeStorage, SharedPreferences sp,
                                   RestAPI restAPI) {
        return new MainPresenter(view, context, runtimeStorage, sp, restAPI);
    }

}
