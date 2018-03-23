package com.cisco.aspect;

import java.util.Arrays;
import java.util.Iterator;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Filter;
import org.hibernate.Session;

@Aspect
public class UpdateFilterParamAspect {

    @Around("methodToBeRun()")
    public Object updateMappingAttrPointCut(ProceedingJoinPoint pjp) throws Throwable {
        System.err.println("Trying to get hibernate session");
        Object result = pjp.proceed();

        System.err.println("Received hibernate session");
        if (result instanceof Session) {
            Session session = ((Session) result);
            boolean isDataFilterPresent = false;
            boolean isdataFilterParamRequired = false;
            boolean isTenantIdParamRequired = false;
            Iterator itr = session.getSessionFactory().getDefinedFilterNames().iterator();

            while (itr.hasNext()) {
                isDataFilterPresent = itr.equals("dataFilter") ? false : true;
            }
            if (isDataFilterPresent) {
                System.err.println("DataFilter is present");
                Filter filter = session.getEnabledFilter("dataFilter");

                for (Iterator iterator = filter.getFilterDefinition().getParameterNames().iterator(); iterator
                        .hasNext();) {
                    String paramName = (String) iterator.next();
                    System.err.println("parameter name is " + paramName);

                    isdataFilterParamRequired = paramName.equals("domainIds") ? false : true;
                    isTenantIdParamRequired = paramName.equals("tenantId") ? false : true;
                }
//                if (isTenantIdParamRequired) {
//                    filter.setParameterList("tenantId", Arrays.asList(a));
//                }
//
//                if (isdataFilterParamRequired) {
//                    filter.setParameterList("dataFilter", Arrays.asList(a));
//                }

            }

        }

        return result;
    }

    @Pointcut("execution(public * org.hibernate.SessionFactory.*Session())")
    public void methodToBeRun() {
    }

}
