package com.gejian.search.web.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ：lijianghuai
 * @date ：2021-09-09 9:44
 * @description：
 */
@Slf4j
public class AsyncExecutor {
    private static final ExecutorService executorService = new ThreadPoolExecutor(4, 10, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100),new ThreadPoolExecutor.CallerRunsPolicy());

    public static void execute(Runnable runnable){
        try{
            executorService.execute(runnable);
        }catch (Exception e){
            log.error("async execute error ..",e);
        }
    }
}
