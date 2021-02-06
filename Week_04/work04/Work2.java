package com.geek.work04;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.Executors.*;

/**
 * 本周作业2：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，    程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */

public class Work2 {
    public static void main(String[] args) throws Exception {

        long startTime = System.currentTimeMillis();

        //实例代码：启动线程
        int sum = sum();
        System.out.println("result : " + sum);
        long endTime = System.currentTimeMillis();
        System.out.println("耗时 : " + (endTime - startTime) + "ms");
        //推出main线程

        //创建线程的方式
        /**
         *  1.继承Thread类，重新run方法
         */
        Method1 method1 = new Method1();
        int i = method1.method2();
        System.out.println(i);
        method1.start();

        /**
         *
        2.实现Runnable接口
         */
        new Method2().run();

        /**
         * 3.lambda
         */
        new Thread(() -> System.out.println("this is method3")).start();

        /**
         * 4.FutureTask + Callable
         *
        */
        Thread thread = new Thread(new FutureTask<String>(new Method4()));
        String call = new Method4().call();
        System.out.println(call);

        /**
         *  5.Executors.newCachedThreadPool()
         */
        ExecutorService executorService = newCachedThreadPool();
        executorService.execute(() ->{
            System.out.println("this is method5!");
        });
        executorService.shutdown();

        /**
         *  6.Callable
         */
        Callable <Integer> callable = Work2::sum;
        try {
            System.out.println("this is method6:" + callable.call());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 7.ExecutorService
         */
        int count = executorService();
        System.out.println("this is method7: " + count);

        /**
         * 8.FutureTask
         */
        FutureTask<Integer> integerFutureTask = new FutureTask<>(Work2::sum);
        new Thread(integerFutureTask).start();
        System.out.println("this is method8:" + integerFutureTask.get());

        /**
         * 9 AtomicInteger
         */
        AtomicInteger atomicInteger = new AtomicInteger();
        new Thread(()-> atomicInteger.set(Work2.sum())).start();
        TimeUnit.MILLISECONDS.sleep(1000);
        System.out.println("this is method9: " + atomicInteger.get());

    }

    public static int sum() {
        return fibo(36);
    }

    public static int fibo(int i) {
        if (i < 2) {
            return 1;
        }
        return fibo(i - 1) + fibo(i - 2);
    }

    //

    //7.ExecutorService
    public static int executorService(){
        ExecutorService executorService = newSingleThreadExecutor();
        Future<Integer> f = executorService.submit(Work2::sum);
        executorService.shutdown();
        Integer count = null;
        try {
            count = f.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return count;
    }

    //1.thread方式
    static class Method1 extends Thread {
        @Override
        public void run() {
            System.out.println("this is method1");
            method2();
        }
        public int method2(){
            return sum();
        }
    }

    //2.实现Runnable接口
    static class Method2 implements Runnable {
        @Override
        public void run() {
            System.out.println("this is method2");
            sum();
        }
    }

    //4FutureTask + Callable

    static class Method4 implements Callable<String>{

        @Override
        public String call() throws Exception {
            System.out.print("this is method4 : ");
            int sum = sum();
            String s = String.valueOf(sum);
            return s;
        }
    }
}
