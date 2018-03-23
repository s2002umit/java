package com.dynamicLoading;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class testDynamicLoadingBean {

    public static void main(String[] args) {
        URL[] classLoaderUrls = null;
        try {
            classLoaderUrls = new URL[] { new URL("file:///Users/sumgupt2/Downloads/testpocservice-0.0.1-SNAPSHOT.jar") };
        } catch (MalformedURLException e) {
             e.printStackTrace();
        }
        
        
        

       
         ApplicationContext pluginApplicationContext = new ClassPathXmlApplicationContext("file:///Users/sumgupt2/Downloads/testpocservice-0.0.1-SNAPSHOT.jar");

        ClassLoader currentThreadClassLoader = Thread.currentThread().getContextClassLoader();

        // Create a new URLClassLoader
        URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls, currentThreadClassLoader);
        Thread.currentThread().setContextClassLoader(urlClassLoader);
 
        try {
            Class<?> beanClass = urlClassLoader.loadClass("com.cisco.TESTPOC");
            System.err.println("==============="+beanClass.getMethods()[1]);
            Constructor<?> c =  beanClass.getConstructor();
            Method  method=beanClass.getMethod("testMethod", String.class);
            method.invoke(c.newInstance(), "wqd");
            System.err.println("file has been loaded");
           
            System.err.println("fiel has been loaded");
        
            Class.forName("com.cisco.TESTPOC",true,urlClassLoader);
          
            
            Thread.currentThread().getContextClassLoader().getClass().forName("com.cisco.TESTPOC");
            
//            Class.forName("com.cisco.TESTPOC");
            System.err.println("fiel has been loaded");
        } catch (Exception e) {
         System.err.println("error in loading");
            e.printStackTrace();
        }
        
    }

}
