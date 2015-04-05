package glengine.yan.glengine.util.loggers;

import android.util.Log;

/**
 * Created by Yan-Home on 10/4/2014.
 */
public class YANLogger {

    private static final String LOG_TAG = "YanLogger";

    public static final void log(String msg) {
        Log.i(LOG_TAG, msg);
    }

    public static void error(String msg) {
        Log.e(LOG_TAG, msg);
    }
    public static void warn(String msg) {
        Log.w(LOG_TAG, msg);
    }
}
