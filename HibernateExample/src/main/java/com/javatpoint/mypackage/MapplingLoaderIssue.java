package com.javatpoint.mypackage;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mkyong.util.HibernateUtil;

public class MapplingLoaderIssue {

    public static void main(String[] args) {

        System.out.println("Hibernate one to many (XML Mapping)");
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction t = session.beginTransaction();
        Criteria cr = session.createCriteria(Regular_Employee.class);
        List results = cr.list();
        System.err.println(results);
        Iterator<Employee> iterator = results.iterator();
        while (iterator.hasNext()) {
            Employee employee = (Employee) iterator.next();
            System.err.println("test123" + employee.getName());
        }

        System.out.println("Done");
        session.close();
    }

}
