package com.cisco.aspect;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainTEST {

    public static Date lastRequestedTime;
    public static List<String> allNetworkDevices;

    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorld();
        System.err.println(getNetworkDevices().size());
        System.err.println(getNetworkDevices().size());
        System.err.println(getNetworkDevices().size());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.err.println(getNetworkDevices().size());
        
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.err.println(getNetworkDevices().size());

        helloWorld.sayHello("Hello World!");
        helloWorld.sayHello("Bye World!");
    }

    private static List<String> getNetworkDevices() {
        if (lastRequestedTime != null) {
            long duration = new Date().getTime() - lastRequestedTime.getTime();
            long diffSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
            System.err.println("last call for DB was {} seconds before" + diffSeconds);
            if (diffSeconds < 15) {
               return allNetworkDevices;
            }
        }

            System.err.println("Doing DB call");
            allNetworkDevices = Arrays.asList("sumit");
            lastRequestedTime = new Date();
        return allNetworkDevices;
    }
}
