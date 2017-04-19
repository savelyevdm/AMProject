package ru.developsdm.amproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.developsdm.amproject.R;
import ru.developsdm.amproject.app.AMApp;
import ru.developsdm.amproject.di.component.ApplicationComponent;
import ru.developsdm.amproject.di.component.DaggerSplashComponent;
import ru.developsdm.amproject.di.component.SplashComponent;
import ru.developsdm.amproject.di.module.SplashModule;
import ru.developsdm.amproject.presenter.SplashPresenter;
import ru.developsdm.amproject.view.contract.ISplashView;

/**
 * Splash screen implementation.
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

public class SplashActivity extends AppCompatActivity implements ISplashView {

    /**
     * Screen presenter
     */
    @Inject
    SplashPresenter presenter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        unbinder = ButterKnife.bind(this);

        // activity init
        ApplicationComponent applicationComponent = ((AMApp) getApplication()).getApplicationComponent();
        SplashComponent component = DaggerSplashComponent.builder()
                .applicationComponent(applicationComponent)
                .splashModule(new SplashModule(this))
                .build();
        component.inject(this);
    }

    @Override
    protected void onDestroy() {
        presenter.releaseResources();
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachedViewIsShown();
    }

    @Override
    protected void onStop() {
        presenter.attachedViewIsHidden();
        super.onStop();
    }

    @Override
    public void startScreen(Intent intent) {
        if (intent != null)
            startActivity(intent);
    }
}
