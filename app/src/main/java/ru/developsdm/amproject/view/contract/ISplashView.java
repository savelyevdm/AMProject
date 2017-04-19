package ru.developsdm.amproject.view.contract;

import android.content.Intent;

/**
 * Contract for {@link ru.developsdm.amproject.view.SplashActivity}
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

public interface ISplashView {

    /**
     * Start another activity based on data from intent
     *
     * @param intent intent
     */
    void startScreen(Intent intent);

    /**
     * Stops current activity
     */
    void finish();

}
