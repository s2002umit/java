package com.mkyong;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.mkyong.stock.Employee;
import com.mkyong.util.HibernateUtil;

public class OptimizeView_Issue {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        // Transaction t = session.beginTransaction();
        //
        // Employee employee=new Employee();
        // employee.setId(1001);
        // employee.setFirstName("sumit");
        // employee.setLastName("gupta");
        // employee.setSalary(121);
        // session.save(employee);
        // t.commit();

        Criteria cr = session.createCriteria(Employee.class);
        List results = cr.list();

        Iterator<Employee> iterator = results.iterator();
        while (iterator.hasNext()) {
            Employee employee = (Employee) iterator.next();
            System.err.println("test123" + employee.getFirstName());
        }
    }

}
