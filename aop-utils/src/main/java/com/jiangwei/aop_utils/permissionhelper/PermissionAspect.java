package com.jiangwei.aop_utils.permissionhelper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import android.text.TextUtils;

/**
 * author: jiangwei18 on 17/5/2 23:23 email: jiangwei18@baidu.com Hi: jwill金牛
 */
@Aspect
public class PermissionAspect {
    private static final String CUT_POINT_METHOD =
            "execution(@com.jiangwei.aop_utils.permissionhelper.PermissionCheck * *(..)) && @annotation(ann)";

    @Pointcut(CUT_POINT_METHOD)
    public void onMethod(PermissionCheck ann) {
    }

    @Before("onMethod(ann)")
    public void joinPoint(ProceedingJoinPoint point, PermissionCheck ann) throws Throwable {
        if (TextUtils.isEmpty(ann.permission())) {
            throw new IllegalStateException("you should declare permission on the method");
        }
       CheckPermission.getInstance().checkPermission(ann.permission());
    }
}
