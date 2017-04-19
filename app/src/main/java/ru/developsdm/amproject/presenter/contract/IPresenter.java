package ru.developsdm.amproject.presenter.contract;

/**
 * Base interface for presenter classes.
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

public interface IPresenter {

    /**
     * Called when presenter is created.
     */
    void initialiseResources();

    /**
     * Called when presenter is going to be destructed.
     */
    void releaseResources();

    /**
     * Called, when presenter's view is shown on screen.
     */
    void attachedViewIsShown();

    /**
     * Called, when presenter's view is going to be hidden from screen.
     */
    void attachedViewIsHidden();

}
