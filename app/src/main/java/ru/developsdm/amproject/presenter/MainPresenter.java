package ru.developsdm.amproject.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.WeakReference;

import ru.developsdm.amproject.R;
import ru.developsdm.amproject.app.RuntimeStorage;
import ru.developsdm.amproject.model.web.RestAPI;
import ru.developsdm.amproject.presenter.contract.Presenter;
import ru.developsdm.amproject.utils.UtilLogging;
import ru.developsdm.amproject.view.contract.IMainView;

/**
 * Presenter for {@link ru.developsdm.amproject.view.MainActivity}
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

public class MainPresenter extends Presenter {

    WeakReference<IMainView> viewRef;
    Context context;
    SharedPreferences sp;
    RuntimeStorage runtimeStorage;
    RestAPI restAPI;

    IMainView getView() {
        if (viewRef != null && viewRef.get() != null)
            return viewRef.get();
        return null;
    }

    public MainPresenter(IMainView view, Context context,
                         RuntimeStorage runtimeStorage, SharedPreferences sp,
                         RestAPI restAPI) {

        this.viewRef = new WeakReference<>(view);
        this.context = context;
        this.runtimeStorage = runtimeStorage;
        this.sp = sp;
        this.restAPI = restAPI;

        initialiseResources();
    }

    @Override
    public void releaseResources() {
        viewRef.clear();
        viewRef = null;
    }

    @Override
    public void attachedViewIsShown() {
        openCatalogue();
    }

    public void openCatalogue() {
        getView().showCatalogueView(runtimeStorage.getCatalogue());
    }

    public void openSets() {
        getView().showMockView(context.getResources().getString(R.string.fragment_title_sets), "sets");
    }

    public void openSales() {
        getView().showMockView(context.getResources().getString(R.string.fragment_title_sales), "sales");
    }

    public void openShops() {
        getView().showMockView(context.getResources().getString(R.string.fragment_title_shops), "shops");
    }

    public void openProfile() {
        getView().showMockView(context.getResources().getString(R.string.fragment_title_profile), "profile");
    }

    public void buy() {
        UtilLogging.toast(context, R.string.toolbar_menu_buy_text);
    }

    public void about() {
        UtilLogging.toast(context, R.string.toolbar_menu_about_text);
    }


}
