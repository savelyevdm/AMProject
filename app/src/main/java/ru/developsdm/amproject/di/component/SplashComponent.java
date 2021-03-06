package ru.developsdm.amproject.di.component;

import dagger.Component;
import ru.developsdm.amproject.di.module.SplashModule;
import ru.developsdm.amproject.di.scope.PerActivity;
import ru.developsdm.amproject.presenter.SplashPresenter;
import ru.developsdm.amproject.view.SplashActivity;
import ru.developsdm.amproject.view.contract.ISplashView;

/**
 * Component.
 *
 * Created by Daniil Savelyev in 2017.
 */
@PerActivity
@Component(modules = SplashModule.class, dependencies = ApplicationComponent.class)
public interface SplashComponent {

    void inject(SplashActivity activity);

    ISplashView activity();

    SplashPresenter presenter();

}
