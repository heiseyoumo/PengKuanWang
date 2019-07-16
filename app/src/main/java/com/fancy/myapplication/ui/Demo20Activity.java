package com.fancy.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.fancy.myapplication.R;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author pengkuanwang
 * @date 2019-07-16
 */
public class Demo20Activity extends Activity {
    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo20);
        /**
         * 核心线程3个 非核心线程3个,无超时机制
         * LinkedBlockingQueue
         */
        Executors.newFixedThreadPool(3);
        /**
         * 核心线程3个 非核心线程不限量,没有超时机制  闲置时立马回收
         * DelayedWorkQueue
         */
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        /**
         * 没有核心线程,大量非核心线程,非核心线程闲置60s回收,适合大量,耗时短的任务
         * SynchronousQueue 任务队列相当于一个空的集合,这将导致任何任务都会被立即执行,因为这种场景下
         * SynchronousQueue是无法插入任务的。SynchronousQueue是一个特殊的队列,在很多情况下可以把
         * 它理解为一个无法存储的队列，所以这类线程池比较适合执行大量耗时少的任务。
         */
        Executors.newCachedThreadPool();
        Executors.newSingleThreadExecutor();
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(10);
        //拒绝策略1：将抛出 RejectedExecutionException.
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(10), handler);
        for (int i = 0; i < 125; i++) {
            poolExecutor.execute(new Worker());
        }
        poolExecutor.shutdown();
    }

    private static class Worker implements Runnable {
        @Override
        public void run() {
            Log.d("Demo20Activity", Thread.currentThread().getName() + " is running  " + atomicInteger.getAndIncrement());
        }
    }
}