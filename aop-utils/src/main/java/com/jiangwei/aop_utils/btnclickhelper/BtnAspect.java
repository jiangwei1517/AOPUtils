package com.jiangwei.aop_utils.btnclickhelper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import android.view.View;

/**
 * author: jiangwei18 on 17/5/2 19:15 email: jiangwei18@baidu.com Hi: jwill金牛
 */
@Aspect
public class BtnAspect {
    private static int PERIOD = 3000;
    // onMethod
    private static final String POINTCUT_METHOD =
            "execution(@com.jiangwei.aop_utils.btnclickhelper.BtnClickTwice * *(..))";

    private long lastClickTime = 0;

    private long currentTime = 0;

    private long duration = 0;

    @Pointcut(POINTCUT_METHOD)
    public void onClick() {
    }

    @Around("onClick()")
    public void joinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        View view = null;
        Object[] args = joinPoint.getArgs();
        if (args.length == 1 && args[0] instanceof View) {
            View v = (View) args[0];
            currentTime = System.currentTimeMillis();
            lastClickTime = v.getTag() != null ? (long) v.getTag() : 0;
            duration = currentTime - lastClickTime;
            if (duration > PERIOD) {
                System.out.println("执行成功" + duration + "ms");
                joinPoint.proceed();
            } else {
                System.out.println("执行失败" + duration + "ms");
            }
            v.setTag(currentTime);
        } else {
            throw new IllegalStateException("BtnClickTwice should be used on onClick(View view) method");
        }
    }
}
