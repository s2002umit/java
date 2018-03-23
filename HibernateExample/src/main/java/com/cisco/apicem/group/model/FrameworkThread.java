package com.cisco.apicem.group.model;

public class FrameworkThread extends Thread{
    
    private Integer tenantId;
    
    public FrameworkThread(){
        System.err.println("======setting tenant id in constructor===="+tenantId);
        tenantId= ThreadLocalPOC.threadLocalOfSecurityContext.get();
       System.err.println("======Thead local while creating new thread====="+tenantId);
    }

    
    public synchronized void start() {
   System.err.println("tenantId is "+tenantId);
     ThreadLocalPOC.threadLocalOfSecurityContext.set(tenantId);
       System.err.println("===============before===="+tenantId);
        super.start();
        
        System.err.println("===============after===="+ThreadLocalPOC.threadLocalOfSecurityContext.get());
    }

}
