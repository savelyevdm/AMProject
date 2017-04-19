package ru.developsdm.amproject.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import lombok.NonNull;
import ru.developsdm.amproject.BuildConfig;

/**
 * Logging utils.
 * <p/>
 * Created by Daniil Savelyev in 2016.
 */
public class UtilLogging {

    /**
     * Debug constant. Must be disabled in release mode.
     */
    private static final boolean DEBUG = BuildConfig.DEBUG;

    /**
     * App log tag.
     */
    public static final String LOG_TAG = "DEVELOPSDM";

    /**
     * Debug levels.
     */
    public enum Lvl {
        D, I, W, E
    }

    ;

    public static void log(@NonNull Lvl l, @NonNull String msg) {
        if (!DEBUG)
            return;
        switch (l) {
            case D:
                Log.d(LOG_TAG, msg);
                break;
            case I:
                Log.i(LOG_TAG, msg);
                break;
            case W:
                Log.w(LOG_TAG, msg);
                break;
            case E:
                Log.e(LOG_TAG, msg);
                break;
            default:
                Log.d(LOG_TAG, msg);
                break;
        }
    }

    private static void longLog(@NonNull Lvl l, @NonNull String msg) {
        if (msg.length() < 4000) { // console limit for too long strings.
            log(l, msg);
        } else {
            for (String line : msg.split("\n")) {
                log(l, line);
            }
        }
    }

    public static void logD(@NonNull String msg) {
        longLog(Lvl.D, msg);
    }

    public static void logI(@NonNull String msg) {
        longLog(Lvl.I, msg);
    }

    public static void logW(@NonNull String msg) {
        longLog(Lvl.W, msg);
    }

    public static void logE(@NonNull String msg) {
        longLog(Lvl.E, msg);
    }

    public static void logE(@NonNull String msg, @NonNull Throwable e) {
        longLog(Lvl.E, msg);
        printStackTrace(e);
    }

    public static void logE(@NonNull Throwable e) {
        printStackTrace(e);
    }

    /**
     * Prints Android exceptions in Eclipse console style.
     *
     * @param e Exception raised.
     */
    public static void printStackTrace(@NonNull Throwable e) {
        logE("Caught exception: [ " + e.getClass().getName() + " ] with message: [ " + e.getMessage() + " ]");
        logE("my stack trace:");
        StackTraceElement[] elems = e.getStackTrace();
        for (StackTraceElement el : elems) {
            String s = String.format(Locale.US, "    at %s.%s(%s:%d)",
                    el.getClassName(), el.getMethodName(), el.getFileName(), el.getLineNumber());
            Log.e(LOG_TAG, s);
        }
    }

    public static void log(@NonNull Context context, @NonNull Lvl l, int resId) {
        if (resId < 0)
            return;
        String msg = context.getResources().getString(resId);
        longLog(l, msg);
    }

    public static void toast(@NonNull Context context, @NonNull String msg) {
        @SuppressLint("ShowToast") Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        showToast(toast);
    }

    public static void toast(@NonNull Context context, int resId) {
        if (resId < 0)
            return;
        @SuppressLint("ShowToast") Toast toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        showToast(toast);
    }

    public static void toastLong(@NonNull Context context, @NonNull String msg) {
        @SuppressLint("ShowToast") Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        showToast(toast);
    }

    public static void toastLong(@NonNull Context context, int resId) {
        if (resId < 0)
            return;
        @SuppressLint("ShowToast") Toast toast = Toast.makeText(context, resId, Toast.LENGTH_LONG);
        showToast(toast);
    }

    private static void showToast(Toast toast) {
        try {
            TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);
            tv.setGravity(Gravity.CENTER);
            //toast.setGravity(Gravity.CENTER, 0, 0);
        } catch (Exception ignored) {
        }
        toast.show();
    }

}
