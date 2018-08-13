package com.intretech.app.base;

/**
 * Created by yq06171 on 2018/7/4.
 */

public class GlobalConfig {
    public static final class RequestCode {
        public static int GOTO_IMAGE = 13;
        public static int GOTO_CAMERA = 14;
        public static int GOTO_ALBUM = 15;
        public static int GOTO_CROP = 16;
        public static int RQ_WIFI_5G_HINT = 17;
        public static int SCAN_RESULT = 18;
    }


    public static final class Path {
        public static final String APP_PATH = "/Mongmen";
        public static final String APK_PATH = "/apkfile";
        public static final String PIC_PATH = "/picture";
        public static final String IMAGE_AVATER_NAME = "aero_avater.jpg";
        public static final String SYSTEM_PICTURE ="/DCIM";
        public static final String PREFIX_MONGMEN = "mongmen_";
    }

    public static final class BundleKey{
        public static final String INTERCEPTOR_LOGIN = "INTERCEPTOR_LOGIN";
    }


}
