package com.cisco.apicem.group.model;

public class MyThread extends Thread {

    public void run() {

        System.err.println(
                " Threadlocal after running the configuration  " + ThreadLocalPOC.threadLocalOfSecurityContext.get());

    }

}
