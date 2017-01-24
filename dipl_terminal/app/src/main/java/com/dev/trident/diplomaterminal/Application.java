package com.dev.trident.diplomaterminal;

/**
 * trident 17.05.16.
 */
public class Application extends android.app.Application {
    public static String PREFS_NAME = "com.dev.trident.diplomaterminal.prefs";
    public static String PREF_IPADDR = "com.dev.trident.diplomaterminal.prefs.ipaddr";
    public static String PREF_IPPORT = "com.dev.trident.diplomaterminal.prefs.ipport";

    public static final int UNSUCCESSFUL_BEFORE_BLOCK = 5;
    public static final int BLOCK_INTERVAL_IN_MILLIS = 60000;
    public static final int ACCESS_TIME_IN_MILLIS = 4500;
    public static final int RECONNECT_TIME_IN_MILLIS = 1000;

    @Override
    public void onCreate() {
        // workaround for http://code.google.com/p/android/issues/detail?id=20915
        try {
            Class.forName("android.os.AsyncTask");
        } catch (ClassNotFoundException e) {}
        super.onCreate();
    }
}
