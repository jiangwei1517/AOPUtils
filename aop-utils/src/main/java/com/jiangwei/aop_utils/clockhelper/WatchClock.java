package com.jiangwei.aop_utils.clockhelper;

import java.util.concurrent.TimeUnit;

/**
 * author: jiangwei18 on 17/5/2 15:57 email: jiangwei18@baidu.com Hi: jwill金牛
 */

public class WatchClock {
    // 开始时间
    private static long startTime;
    // 结束时间
    private static long endTime;
    // 间隔时间duration
    private static long duration;

    public static void start() {
        startTime = System.nanoTime();
    }

    public static void stop() {
        if (startTime != 0) {
            endTime = System.nanoTime();
            duration = endTime - startTime;
        } else {
            reset();
        }
    }

    public static long getTotalTimeMillis() {
        return (duration != 0) ? TimeUnit.NANOSECONDS.toMillis(endTime - startTime) : 0;
    }

    public static void reset() {
        startTime = 0;
        endTime = 0;
        duration = 0;
    }
}
