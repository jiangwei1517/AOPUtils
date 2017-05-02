package com.jiangwei.aop_utils.memorycachehelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import android.text.TextUtils;

/**
 * author: jiangwei18 on 17/5/2 20:37 email: jiangwei18@baidu.com Hi: jwill金牛
 */
@Aspect
public class MemoryCacheAspect {
    private Map<String, Object> memoryMap = new HashMap<>();
    private static final String POINT_CUT_METHOD =
            "execution(@com.jiangwei.aop_utils.memorycachehelper.MemoryCache * *(..))";

    @Pointcut(POINT_CUT_METHOD)
    public void memoryCache() {
    }

    @Around("memoryCache()")
    public Object joinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        String className = signature.getDeclaringType().getSimpleName();
        Object[] args = joinPoint.getArgs();
        StringBuilder memoryBuilder = new StringBuilder();
        memoryBuilder.append(className + methodName);
        for (Object arg : args) {
            memoryBuilder.append(((Class) arg).getSimpleName());
        }
        String ms = memoryBuilder.toString();
        System.out.println(ms);
        Object obj = memoryMap.get(ms);
        if (obj != null) {
            return obj;
        }
        Object result = joinPoint.proceed();
        if (result instanceof List && result != null && ((List) result).size() > 0 // 列表不为空
                || result instanceof String && !TextUtils.isEmpty((String) result)// 字符不为空
                || result instanceof Object && result != null) {
            memoryMap.put(ms, result);
        }
        return result;
    }
}
