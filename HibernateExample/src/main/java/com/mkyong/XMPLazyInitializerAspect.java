package com.mkyong;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.collection.AbstractPersistentCollection;
import org.hibernate.engine.SessionImplementor;

import com.mkyong.util.HibernateUtil;

//@Aspect
public class XMPLazyInitializerAspect {

	static private Method m = null;

	@Around("methodToBeIntercepted()")
	public Object createSessionIfNeeded(ProceedingJoinPoint pjp)
			throws Throwable {

		if (m == null) {
			m = AbstractPersistentCollection.class.getDeclaredMethod(
					"isConnectedToSession", new Class[] {});
			m.setAccessible(true);
		}
		// if (lazyInit == null && XMPLazyInitializer.ready) {
		// try {
		// lazyInit = XMPAppContextGetter.getContext().getBean(
		// XMPLazyInitializer.class);
		// } catch (Exception e) {
		// // Ignore
		// }
		// }
		// Object retVal = null;
		// Object target = pjp.getTarget();
		// AbstractPersistentCollection theColl = (AbstractPersistentCollection)
		// target;
		// Boolean wasInit = theColl.wasInitialized();

		// SessionImplementor session = null;
		// try {
		// if (!wasInit && lazyInit != null
		// && !((Boolean) m.invoke(target, new Object[] {}))) {
		// if (InstanceImpl.isQuieteOperation.get())
		// throw new IgnoreLazyAspectDuringQuiteOperation();
		// session = lazyInit.getSession(theColl);
		// }
		// retVal = pjp.proceed();
		// } finally {
		// if (session != null) {
		// ((PersistenceSession) session).close();
		// }
		// }
		System.err.println("Sumit123+++++++++++++++++");
		// return (SessionImplementor) HibernateUtil.getSessionFactory()
		// .openSession();
		Object retVal = null;
		SessionImplementor session = null;
		try {

			session = (SessionImplementor) HibernateUtil.getSessionFactory()
					.openSession();
			((Session) session)
					.lock(((AbstractPersistentCollection) pjp.getTarget())
							.getOwner(), LockMode.NONE);
			// try {
			// ((Session) session).lock(collection.getOwner(), LockMode.NONE);
			// } catch (Exception e) {
			// ((Session) session).close();
			// session = null;
			// }

			retVal = pjp.proceed();
		} finally {
			if (session != null) {
				((Session) session).close();
			}
		}
		return retVal;
	}

	 @Pointcut("call(* org.hibernate.collection.AbstractPersistentCollection.initialize*(..)) || "
	 +
	 "call(* org.hibernate.collection.AbstractPersistentCollection.readSize()) || "
	 +
	 "call(* org.hibernate.collection.AbstractPersistentCollection.readIndexExistence(..)) || "
	 +
	 "call(* org.hibernate.collection.AbstractPersistentCollection.readElementByIndex(..)) || "
			+

	 "call(* org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer.invoke(..)) || "
	 +
	 "call(* org.hibernate.collection.AbstractPersistentCollection.readElementExistence(..))")
	// @Pointcut("execution(* SerializeDemo.deSerializeObject())")
	public void methodToBeIntercepted() {
	}

}
