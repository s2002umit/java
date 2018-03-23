package com.mkyong;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.javatpoint.mypackage.Company;
import com.javatpoint.mypackage.Contract_Employee;
import com.javatpoint.mypackage.Employee;
import com.javatpoint.mypackage.Regular_Employee;
import com.mkyong.stock.Stock;
import com.mkyong.stock.StockDailyRecord;
import com.mkyong.util.HibernateUtil;

public class App {
    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        
        Transaction t = session.beginTransaction();

        // session.getNamedQuery("findStuff").setString("likeWhat", value);

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
        // createRecordsforAbstarctHierarchyIssue(session, t);

        // tablePerHierachyISSUE(session);

       //createRecord(session);
        //createMultipleRecord(session);
        long startTime = System.currentTimeMillis();
        
        DetachedCriteria cr = DetachedCriteria.forClass(Stock.class);

        //Criteria cr = session.createCriteria(Stock.class);
       // cr.add(Restrictions.eq("stockCode", "7052"));
        cr.add(Restrictions.sqlRestriction("stock_name ~ '^11\\.100\\.(1\\.(1([0-9][0-9])|2([0-4][0-9]|5[0-5]))|(([2-9]|[1-9][0-9])\\.([0-9]|[1-9][0-9]|1([0-9][0-9])|2([0-4][0-9]|5[0-5])))|100\\.([0-9]|[1-9][0-9]|1([0-9][0-9])|200))$'"));
        
        
        List results= cr.getExecutableCriteria(session).list();
        // List results = cr.list();
      //  Long resultCount = (Long) cr.uniqueResult();

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Total Time" + elapsedTime);
        System.err.println("resultCount" + results.size());
//
//        Criteria cr = session.createCriteria(StockDailyRecord.class);
//        cr.setProjection(Projections.count("priceClose"));
//        cr.add(Restrictions.eq("priceClose", new Float("1.1")));
//        // List results = cr.list();
//        Long resultCount = (Long) cr.uniqueResult();
//
//        long stopTime = System.currentTimeMillis();
//        long elapsedTime = stopTime - startTime;
//        System.out.println("Total Time" + elapsedTime);
//        System.err.println("resultCount" + resultCount);
        // while (iterator.hasNext()) {
        // System.err.println("Before loading stockDailyRecord");
        // StockDailyRecord stockDailyRecotd = iterator.next();
        // System.err.println("Fetching stockDailyRecord");
        // System.err.println("version " + stockDailyRecotd.getRecordId());
        // System.err.println("Going to fetch stock id");
        // System.err.println("stock id " +
        // stockDailyRecotd.getStock().getStockId());
        // System.err.println("=======Going to load stock code========");
        // System.err.println("test123" +
        // stockDailyRecotd.getStock().getStockCode());
        // }
        System.out.println("Done");
    }

    private static void tablePerHierachyISSUE(Session session) {
        Criteria cr = session.createCriteria(Employee.class);
        //
        // // To get records having salary more than 2000
        // cr.add(Restrictions.eq("companyName", "cisco"));
        cr.add(Restrictions.eq("class", "reg_emp"));
        List<Regular_Employee> results = cr.list();
        // Company company = (Company) results.get(0);
        // Set<Employee> employees = company.getEmployee();
        Iterator<Regular_Employee> iterator = results.iterator();
        while (iterator.hasNext()) {
            Regular_Employee employee = iterator.next();
            if (employee instanceof Regular_Employee) {
                System.err.println(employee.getSalary());
            }
            // if (employee instanceof Contract_Employee) {
            // System.err.println("contract Employee");
            // }
            System.err.println("Emplyee Name::" + employee.getName());
        }
        session.close();
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
        Set<Employee> employeeSet = new HashSet<Employee>();
        // Employee e1 = new Employee();
        // e1.setName("sonoo");

        Regular_Employee e2 = new Regular_Employee();
        e2.setName("Vivek Kumar");
        e2.setSalary(50000);
        e2.setBonus(5);

        Contract_Employee e3 = new Contract_Employee();
        e3.setName("Arjun Kumar");
        e3.setPay_per_hour(1000);
        e3.setContract_duration("15 hours");

        // employeeSet.add(e1);
        employeeSet.add(e2);
        employeeSet.add(e3);
        company.setEmployee(employeeSet);
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

    private static void createMultipleRecord(Session session) {

        for (int i = 0; i < 10000; i++) {
//            Stock stock = new Stock();
//            stock.setStockCode("7052");
//            stock.setStockName("PADINI1");
//            // // // Set emploteeSet = new HashSet();
//            // // // Employee emp = new Employee();
//            // // // emp.setFirstName("sumit");
//            // // // emploteeSet.add(emp);
//            // // // stock.setEmployee(emploteeSet);
//            session.save(stock);
            session.getTransaction().begin();
            StockDailyRecord stockDailyRecords = new StockDailyRecord();
            stockDailyRecords.setPriceOpen(new Float("1.2"));
            stockDailyRecords.setPriceClose(new Float("1.1"));
            stockDailyRecords.setPriceChange(new Float("10.0"));
            stockDailyRecords.setVolume(3000000L);
            stockDailyRecords.setDate(new Date());

//            stockDailyRecords.setStock(stock);
//            stock.getStockDailyRecords().add(stockDailyRecords);
//
//            stockDailyRecords.setStock(stock);

            session.save(stockDailyRecords);

            session.getTransaction().commit();
        }
    }
}
