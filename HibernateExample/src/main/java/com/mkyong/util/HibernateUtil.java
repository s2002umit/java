package com.mkyong.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.javatpoint.mypackage.BinaryToBlobUserType;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration conf = new Configuration();
            // conf.registerTypeOverride(new BinaryToBlobUserType(), new
            // String[] { Byte.class.getName() });
            String workingDir = System.getProperty("user.dir");
            System.out.println("Current working directory : " + workingDir);
            // return
            // conf.configure().addFile("src/main/resources/com/mkyong/stock/Company.hbm.xml")
            // .addFile("src/main/resources/com/mkyong/stock/Employee_Hierarchy.hbm.xml").buildSessionFactory();
            return conf.configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

}