/**
 * 
 */
package com.mkyong;

import java.net.URL;
import java.net.URLClassLoader;

import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

/**
 * @author sumgupt2
 *
 */
public class ProxyClassGenerator {

    private static ClassPool pool = null;

    /**
     * @param classObj
     * @param instanceClass
     * @return
     */
    public static Object getProxyRuntimeInstance(final String parentClass, final String proxyClassToBeCreated) {
        try {

            initializeClassPool();

            CtClass instance = pool.get(parentClass);

            CtClass newClass = null;
            try {

                newClass = pool.get(proxyClassToBeCreated);
                System.err.println("getProxyRuntimeInstance: Found Class");
                return Class.forName(proxyClassToBeCreated).newInstance();

            } catch (NotFoundException nfe) {
                System.err.println("getProxyRuntimeInstance: NotFoundException. Making the class");
                newClass = pool.makeClass(proxyClassToBeCreated);
                newClass.setSuperclass(instance);
                Class clazz = newClass.toClass();
                return clazz.newInstance();
            } finally {
                if (newClass == null) {
                    System.err.println("getProxyRuntimeInstance: newClass == null");
                    // throw new
                    // RuntimeException("Exception while creating Runtime Class");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Exception while creating Runtime Class");
        }
    }

    /**
     * @param classObj
     */
    private static void initializeClassPool() {
        if (pool == null) {
            ClassLoader cl = ProxyClassGenerator.class.getClassLoader();

            URL[] urls = ((URLClassLoader) cl).getURLs();

            for (URL url : urls) {
                System.out.println("+++++" + url.getFile());
            }

            ClassPath path = new LoaderClassPath(cl);

            pool = ClassPool.getDefault();
            pool.appendClassPath(path);
        }
    }

}
