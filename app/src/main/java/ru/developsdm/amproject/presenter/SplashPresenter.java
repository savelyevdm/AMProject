package ru.developsdm.amproject.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.developsdm.amproject.R;
import ru.developsdm.amproject.app.App;
import ru.developsdm.amproject.app.RuntimeStorage;
import ru.developsdm.amproject.model.DataRoot;
import ru.developsdm.amproject.model.web.RestAPI;
import ru.developsdm.amproject.presenter.contract.Presenter;
import ru.developsdm.amproject.utils.UtilInternet;
import ru.developsdm.amproject.utils.UtilLogging;
import ru.developsdm.amproject.view.MainActivity;
import ru.developsdm.amproject.view.contract.ISplashView;

/**
 * Presenter for {@link ru.developsdm.amproject.view.SplashActivity}
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

public class SplashPresenter extends Presenter {

    WeakReference<ISplashView> viewRef;
    Context context;
    SharedPreferences sp;
    RuntimeStorage runtimeStorage;
    RestAPI restAPI;
    Gson gson;
    SplashHandler handler;

    ISplashView getView() {
        if (viewRef != null && viewRef.get() != null)
            return viewRef.get();
        return null;
    }

    private static class SplashHandler extends Handler {

        private long started;
        private Context context;
        private RuntimeStorage runtimeStorage;
        private WeakReference<ISplashView> viewRef;

        SplashHandler(ISplashView view, Context context, RuntimeStorage runtimeStorage) {
            this.runtimeStorage = runtimeStorage;
            this.context = context;
            this.viewRef = new WeakReference<>(view);
            this.started = System.currentTimeMillis();
        }

        @Override
        public void handleMessage(Message msg) {
            boolean oneSecondPassed = (System.currentTimeMillis() - started) > 1000;
            boolean dataLoaded = runtimeStorage.getCatalogue() != null;
            if (oneSecondPassed && dataLoaded) {
                Intent intent = new Intent(context, MainActivity.class);
                viewRef.get().startScreen(intent);
                viewRef.get().finish();
            } else {
                sendEmptyMessageDelayed(0, 50);
            }
        }

        void cleanup() {
            this.removeMessages(0);
            this.viewRef.clear();
            this.viewRef = null;
            this.runtimeStorage = null;
        }
    }

    public SplashPresenter(ISplashView view, Context context,
                           RuntimeStorage runtimeStorage, SharedPreferences sp,
                           RestAPI restAPI, Gson gson) {

        this.viewRef = new WeakReference<>(view);
        this.context = context;
        this.runtimeStorage = runtimeStorage;
        this.sp = sp;
        this.restAPI = restAPI;
        this.gson = gson;

        initialiseResources();
    }

    @Override
    public void initialiseResources() {

        this.handler = new SplashHandler(getView(), context, runtimeStorage);
        this.handler.sendEmptyMessage(0); // any message to start

        /* Imitation of very simple cache on SharedPreferences */

        if (UtilInternet.isConnected(context)) { // ==>> load from web, renew in cache and load to memory from cache
            Call<DataRoot> call = restAPI.getData();
            call.enqueue(new Callback<DataRoot>() {
                @Override
                public void onResponse(Call<DataRoot> call, Response<DataRoot> response) {
                    if (response.isSuccessful()) {
                        DataRoot dataRoot = response.body();
                        setDataToCache(sp, dataRoot);
                        loadDataToRuntimeMemory(runtimeStorage, dataRoot);
                    } else {
                        // get data from cache and load to memory
                        loadFromCache(context, sp, runtimeStorage);
                    }
                }

                @Override
                public void onFailure(Call<DataRoot> call, Throwable t) {
                    UtilLogging.toast(context, context.getResources().getString(R.string.error_internet_error) + ":\n\n" + t.getMessage());
                }
            });
        } else {
            UtilLogging.toast(context, R.string.error_no_internet);
            // get data from cache and load to memory
            loadFromCache(context, sp, runtimeStorage);
        }
    }

    @Override
    public void releaseResources() {
        if (handler != null) {
            handler.cleanup();
            handler = null;
        }
        viewRef.clear();
        viewRef = null;
    }

    /**
     * IMITATION OF A SIMPLE CACHE ON SHARED_PREFERENCES:
     */

    /**
     * Get data from cache and load to memory
     */
    private void loadFromCache(Context context, SharedPreferences sp, RuntimeStorage runtimeStorage) {
        if (hasDataInCache(sp)) {
            DataRoot dataRoot = getDataFromCache(sp);
            loadDataToRuntimeMemory(runtimeStorage, dataRoot);
        } else {
            UtilLogging.toastLong(context, R.string.error_no_cache);
            getView().finish();
        }
    }

    private boolean hasDataInCache(SharedPreferences sp) {
        return sp.contains(App.DATA_KEY);
    }

    private void setDataToCache(SharedPreferences sp, DataRoot dataRoot) {
        String jsonInString = gson.toJson(dataRoot);
        sp.edit().putString(App.DATA_KEY, jsonInString).apply();
    }

    private DataRoot getDataFromCache(SharedPreferences sp) {
        if (!hasDataInCache(sp))
            return null;
        String jsonInString = sp.getString(App.DATA_KEY, App.EMPTY);
        DataRoot dataRoot = gson.fromJson(jsonInString, DataRoot.class);
        return dataRoot;
    }

    private void loadDataToRuntimeMemory(RuntimeStorage rs, DataRoot dataRoot) {
        if (rs == null || dataRoot == null || dataRoot.getCatalogue() == null)
            return;
        rs.setCatalogue(dataRoot.getCatalogue());
    }

}
