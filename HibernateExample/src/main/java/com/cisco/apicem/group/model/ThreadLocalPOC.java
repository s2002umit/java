package com.cisco.apicem.group.model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalPOC {

    public static InheritableThreadLocal<Integer> threadLocalOfSecurityContext = new InheritableThreadLocal<Integer>() {
        protected Integer initialValue() {
            return 2;
        }
    };

    public static void main(String args[]) {
        
        System.err.println(" Thread local before setting the values " + threadLocalOfSecurityContext.get());
        threadLocalOfSecurityContext.set(1);
        MyThread test = new MyThread();
        test.start();
        System.err.println(" Thread local value " + threadLocalOfSecurityContext.get());

        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            Runnable worker = new MyThread();
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");

    }

}
