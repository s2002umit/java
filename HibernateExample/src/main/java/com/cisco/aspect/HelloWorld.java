package com.cisco.aspect;

import java.util.Date;

public class HelloWorld {
    
public static Date currentTime;
    
	public void sayHello(String message) {
		System.out.println(message);
		
	}
	
	public void getValues (){
	    currentTime=new Date();
	}
}
