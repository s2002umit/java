package com.javatpoint.mypackage;

import java.io.Serializable;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.WrongClassException;
import org.hibernate.persister.entity.Loadable;

@Aspect
public class XMPClassInstanciatorAspect {

	@Around("methodToBeIntercepted()")
	public Object getInstanceClass(ProceedingJoinPoint pjp) throws Throwable {
		Object obj = null;
		try {
			obj = pjp.proceed();
		} catch (WrongClassException e) {
			// private String getInstanceClass( final ResultSet rs,final int i,
			// final Loadable persister,
			// final Serializable id,
			// final SessionImplementor session)

			Loadable persister = (Loadable) pjp.getArgs()[2];
			obj = persister.getEntityName();
		}
		return obj;
	}

	@Around("methodToBeIntercepted1()")
	public Object getRunTimeInstanceClass(ProceedingJoinPoint pjp)
			throws Throwable {
		Object obj = null;
		try {
			obj = pjp.proceed();
		} catch (org.hibernate.InstantiationException e) {
			if (pjp.getArgs()[0] instanceof String
					&& pjp.getArgs()[1] instanceof Serializable) {
			// public Object instantiate(
			// String entityName,
			// Serializable id)

			obj = getProxyRuntimeInstance((String) pjp.getArgs()[0]);
			}

		}
		return obj;
	}

	@Pointcut("call(* org.hibernate.loader.Loader.getInstanceClass(..))")
	// @Pointcut("execution(* SerializeDemo.deSerializeObject())")
	public void methodToBeIntercepted() {
	}

	@Pointcut("call(* org.hibernate.engine.SessionImplementor.instantiate(java.lang.String,java.io.Serializable))")
	// @Pointcut("execution(* SerializeDemo.deSerializeObject())")
	public void methodToBeIntercepted1() {
	}

	private Object getProxyRuntimeInstance(final String instanceClass) {
		// instantiate a new instance
		try {
			ClassPool pool = ClassPool.getDefault();
			CtClass instance = pool.get(instanceClass);

			String proxyClassToBeCreated = "XMPFrameworkProxy" + instanceClass;
			CtClass newClass = null;
			try {

				newClass = pool.get(proxyClassToBeCreated);
				System.err.println("getProxyRuntimeInstance: Found Class");
				return Class.forName(proxyClassToBeCreated).newInstance();

			} catch (NotFoundException nfe) {
				System.err
						.println("getProxyRuntimeInstance: NotFoundException. Making the class");
				newClass = pool.makeClass(proxyClassToBeCreated);
				newClass.setSuperclass(instance);
				Class clazz = newClass.toClass();
				return clazz.newInstance();
			} finally {
				if (newClass == null) {
					System.err
							.println("getProxyRuntimeInstance: newClass == null");
					// throw new
					// RuntimeException("Exception while creating Runtime Class");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Exception while creating Runtime Class");
		}
	}

}
