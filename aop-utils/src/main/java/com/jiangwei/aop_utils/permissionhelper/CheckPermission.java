package com.jiangwei.aop_utils.permissionhelper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.jiangwei.aop_utils.BaseApplication;

/**
 * author:  jiangwei18 on 17/5/2 23:55
 * email:  jiangwei18@baidu.com
 * Hi:   jwill金牛
 */

public class CheckPermission {
    private static final CheckPermission sCheckPermission = new CheckPermission();
    private static Activity mActivity;

    private CheckPermission() {

    }

    public static void init(Activity activity) {
        mActivity = activity;
    }

    public static CheckPermission getInstance() {
        return sCheckPermission;
    }

    public static void checkPermission(String permission) {
        if (mActivity == null) {
            throw new IllegalStateException("should use init() first");
        }
        int callingOrSelfPermission = mActivity.checkCallingOrSelfPermission(permission);
        if (callingOrSelfPermission == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(mActivity, "no permission", Toast.LENGTH_SHORT).show();
        } else if (callingOrSelfPermission == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(mActivity, "have permission", Toast.LENGTH_SHORT).show();
        }
    }
}
