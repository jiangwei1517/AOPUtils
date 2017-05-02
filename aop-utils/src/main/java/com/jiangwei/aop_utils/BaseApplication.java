package com.jiangwei.aop_utils;

import android.app.Application;

/**
 * author: jiangwei18 on 17/5/2 23:26 email: jiangwei18@baidu.com Hi: jwill金牛
 */

public class BaseApplication extends Application {
    private static final BaseApplication sApplication = new BaseApplication();

    private BaseApplication() {

    }

    public static BaseApplication getInstance() {
        return sApplication;
    }
}
