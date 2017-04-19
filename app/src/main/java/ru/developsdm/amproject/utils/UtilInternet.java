package ru.developsdm.amproject.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Internet utilities.
 * <p/>
 * Created by Daniil Savelyev in 2016.
 */
public class UtilInternet {

    /**
     * Returns true, if wi-fi internet connection is available.
     *
     * @param context context
     * @return bool.
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();
        // connected to wifi or not
        return activeNetwork != null && (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Returns true, if any internet connection is available.
     *
     * @param context context
     * @return bool.
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnected());
    }

}
