package com.jiangwei.aop_utils.clockhelper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * author: jiangwei18 on 17/5/2 17:20 email: jiangwei18@baidu.com Hi: jwill金牛
 */

@Aspect
public class ClockAspect {
    // onMethod
    private static final String POINTCUT_METHOD = "execution(@com.jiangwei.aop_utils.clockhelper.Clock * *(..))";
    // onConstructor
    private static final String POINTCUT_CONSTRUCTOR = "execution(@com.jiangwei.aop_utils.clockhelper.Clock *.new(..))";

    @Pointcut(POINTCUT_METHOD)
    public void onPointCutMethod() {
    }

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void onPointCutConstructor() {
    }

    @Around("onPointCutMethod() || onPointCutConstructor()")
    public void joinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        String className = signature.getDeclaringType().getName();
        WatchClock.reset();
        WatchClock.start();
        joinPoint.proceed();
        WatchClock.stop();
        long totalTimeMillis = WatchClock.getTotalTimeMillis();
        System.out.println(
                "ClassName:" + className + "-->" + "MethodName:" + methodName + "-->" + "Duration:" + totalTimeMillis);
    }
}
