package com.javatpoint.mypackage;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.mkyong.SerializeDemo;
import com.mkyong.stock.Stock;
import com.mkyong.stock.StockDailyRecord;
import com.mkyong.util.HibernateUtil;

public class BlobIssue {
    public static void main(String[] args) {

        System.out.println("Hibernate one to many (XML Mapping)");
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction t = session.beginTransaction();

        // Criteria cr = session.createCriteria(Employee.class);
        // List results = cr.list();
        //
        // Iterator<Employee> iterator = results.iterator();
        // while (iterator.hasNext()) {
        // Employee employee = (Employee) iterator.next();
        // System.err.println("test123" + employee.getName());
        // }
        //
        // createRecordsforHierarchyIssue(session, t);
        createRecordsforAbstarctHierarchyIssue(session, t);
        // Criteria cr = session.createCriteria(Company.class);
        // //
        // // // To get records having salary more than 2000
        // cr.add(Restrictions.eq("companyName", "cisco"));
        // List results = cr.list();
        // Company company = (Company) results.get(0);
        // Set<Employee> employees = company.getEmployee();
        // Iterator<Employee> iterator = employees.iterator();
        // while (iterator.hasNext()) {
        // Employee employee = (Employee) iterator.next();
        // if (employee instanceof Regular_Employee) {
        // System.err.println(((Regular_Employee) employee).getSalary());
        // }
        // if (employee instanceof Contract_Employee) {
        // System.err.println("contract Employee");
        // }
        // System.err.println("Emplyee Name::" + employee.getName());
        // }
        session.close();

        // createRecord(session);

        // serializationIssue(session);
        System.out.println("Done");
    }

    // private static void createRecordsforHierarchyIssue(Session session,
    // Transaction t) {
    // Company company = new Company();
    // company.setCompanyName("cisco");
    // Set<Employee> employeeSet = new HashSet<Employee>();
    // Employee e1 = new Employee();
    // e1.setName("sonoo");
    //
    // Regular_Employee e2 = new Regular_Employee();
    // e2.setName("Vivek Kumar");
    // e2.setSalary(50000);
    // e2.setBonus(5);
    //
    // Contract_Employee e3 = new Contract_Employee();
    // e3.setName("Arjun Kumar");
    // e3.setPay_per_hour(1000);
    // e3.setContract_duration("15 hours");
    //
    // employeeSet.add(e1);
    // employeeSet.add(e2);
    // employeeSet.add(e3);
    // company.setEmployee(employeeSet);
    // session.persist(company);
    // // session.persist(e2);
    // // session.persist(e3);

    // t.commit();
    // }

    private static void createRecordsforAbstarctHierarchyIssue(Session session, Transaction t) {
        Company company = new Company();
        company.setCompanyName("cisco");
        company.setXmlConfigFile("sumit".getBytes());
        session.persist(company);
        // session.persist(e2);
        // session.persist(e3);

        t.commit();
    }

    private static void serializationIssue(Session session) {
        Criteria cr = session.createCriteria(Stock.class);

        // To get records having salary more than 2000
        cr.add(Restrictions.eq("stockCode", "7052"));
        List results = cr.list();
        Stock stock2 = (Stock) results.get(0);
        // Stock stock2 = (Stock) session.get(Stock.class, 1);

        SerializeDemo.serializeObject(stock2);
        SerializeDemo.deSerializeObject();
    }

    private static void createRecord(Session session) {
        Stock stock = new Stock();
        stock.setStockCode("7052");
        stock.setStockName("PADINI1");
        // // // Set emploteeSet = new HashSet();
        // // // Employee emp = new Employee();
        // // // emp.setFirstName("sumit");
        // // // emploteeSet.add(emp);
        // // // stock.setEmployee(emploteeSet);
        session.save(stock);

        StockDailyRecord stockDailyRecords = new StockDailyRecord();
        stockDailyRecords.setPriceOpen(new Float("1.2"));
        stockDailyRecords.setPriceClose(new Float("1.1"));
        stockDailyRecords.setPriceChange(new Float("10.0"));
        stockDailyRecords.setVolume(3000000L);
        stockDailyRecords.setDate(new Date());

        stockDailyRecords.setStock(stock);
        stock.getStockDailyRecords().add(stockDailyRecords);

        stockDailyRecords.setStock(stock);

        session.save(stockDailyRecords);

        session.getTransaction().commit();
    }
}
