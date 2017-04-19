package ru.developsdm.amproject.di.component;

import dagger.Component;
import ru.developsdm.amproject.di.module.MainModule;
import ru.developsdm.amproject.di.module.SplashModule;
import ru.developsdm.amproject.di.scope.PerActivity;
import ru.developsdm.amproject.presenter.MainPresenter;
import ru.developsdm.amproject.presenter.SplashPresenter;
import ru.developsdm.amproject.view.MainActivity;
import ru.developsdm.amproject.view.SplashActivity;
import ru.developsdm.amproject.view.contract.IMainView;
import ru.developsdm.amproject.view.contract.ISplashView;

/**
 * Component.
 *
 * Created by Daniil Savelyev in 2017.
 */
@PerActivity
@Component(modules = MainModule.class, dependencies = ApplicationComponent.class)
public interface MainComponent {

    void inject(MainActivity activity);

    IMainView activity();

    MainPresenter presenter();

}
