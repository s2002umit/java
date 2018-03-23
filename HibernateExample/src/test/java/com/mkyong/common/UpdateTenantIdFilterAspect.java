package com.mkyong.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;


public class UpdateTenantIdFilterAspect {

    @Around("methodToBeRun()")
    public Object updateMappingAttrPointCut(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        if (args.length > 0) {
            Object target = args[0];
//            if (target != null && target instanceof InstanceImpl) {
//                System.err.println("Going to updaye TenantId");
//                ((InstanceImpl)target).set
//            }
        }

        return pjp.proceed();
    }

    @Pointcut("execution(* com.cisco.xmp.persistence.impl.PersistenceServiceImpl.createInstance(java.util.Object))")
    public void methodToBeRun() {
    }
    
}
