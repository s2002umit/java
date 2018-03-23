/**
 * 
 */
package com.cisco.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author sumgupt2
 *
 */

@Aspect
public class LoggingAspect {

        private Object ret;
        
        public static boolean doOverride = false;

        //@Pointcut("execution(* DOMConfigurator.parseAppender(..))")
        public void logging() {
        }

//      //@Around("logging()")
//      @Around("call(* org.apache.log4j.xml.DOMConfigurator.parseAppender(..))")
//      public Object logging(ProceedingJoinPoint thisJoinPoint) throws Throwable {
//              /*System.out.println("Before " + thisJoinPoint.getSignature());
//
//              if (ret != null) {
//                      Object[] args = thisJoinPoint.getArgs();
//                      for (int i = 0; i < args.length; i++) {
//                              System.err.println("arguments are :" + args[i]);
//                      }
//                      Object ret = thisJoinPoint.proceed(
//                                      new Object[] { EnableDefaultLogger.class.getClassLoader().getResource("default_np_log4j.xml") });
//                      System.out.println("After " + thisJoinPoint.getSignature());
//              } else {
//                      System.out.println("Logger already has been intialized");
//                      Object[] args = thisJoinPoint.getArgs();
//                      for (int i = 0; i < args.length; i++) {
//                              System.err.println("arguments are :" + args[i]);
//                      }
//              }
//              return ret;*/
//              System.err.println("****************sdasfdasfa2*****************");
//              
//              if(doOverride)
//              return SetLogAppendersToSysout.targetAppender;
//              else 
//                      return thisJoinPoint.proceed();
//      }
//      
//      
//      @Around("call(* org.apache.log4j.xml.DOMConfigurator.findAppenderByReference(..))")
//      public Object logging1(ProceedingJoinPoint thisJoinPoint) throws Throwable {
//              System.err.println("****************sdasfdasfa1*****************");
//              
//              
//              if(doOverride)
//                      return SetLogAppendersToSysout.targetAppender;
//                      else 
//                              return thisJoinPoint.proceed();
//      }
        
        
        @Around("call(* org.apache.log4j.Category.removeAllAppenders(..))")
        public void logging2(ProceedingJoinPoint thisJoinPoint) throws Throwable {
                
                if(doOverride){
                        System.err.println("****************sdasfdasfa12*****************");
                        return;
                }
                        else {
                                System.err.println("****************sdasfdasfa*****************");
                        thisJoinPoint.proceed();
                        }
        
                
                
        }

}
