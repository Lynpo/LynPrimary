package com.lynpo.util;

/**
 * Created by fujw on 2017/2/16.
 *
 * jin methods.
 */

public class JniMethods {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public static native String stringFromJNI(/*Context context*/);
}
