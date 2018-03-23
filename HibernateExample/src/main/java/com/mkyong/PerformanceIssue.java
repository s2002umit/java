package com.mkyong;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.mkyong.stock.Stock;
import com.mkyong.stock.StockDailyRecord;
import com.mkyong.util.HibernateUtil;

public class PerformanceIssue {

    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        long startTime = System.currentTimeMillis();
        Transaction t = session.beginTransaction();
        StockDailyRecord sdr1 = (StockDailyRecord) session.get(StockDailyRecord.class, new Integer(347));
        session.delete(sdr1);
        t.commit();
        // createRecord(session);

        // usingHql(session);
        // usingNamedQuery(session);
        // Criteria cr = session.createCriteria(Stock.class);
        //
        // // To get records having salary more than 2000
        // cr.add(Restrictions.eq("stockCode", "7052"));
        // List results = cr.list();
        // Stock stock2 = (Stock) results.get(0);
        //
        // // for (int i = 0; i < 1000; i++) {
        // // Transaction t = session.beginTransaction();
        // // createStockDailyRecotds(session, stock2);
        // // }
        // StockDailyRecord sdr1 = (StockDailyRecord)
        // session.get(StockDailyRecord.class, new Integer(347));
        // stock2.getStockDailyRecords().remove(sdr1);
        // deleteUsingIterator(stock2);
        //
        //
        // session.save(stock2);
        // session.getTransaction().commit();
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Toal Time" + elapsedTime);
    }

    private static void usingNamedQuery(Session session) {
        Transaction t = session.beginTransaction();
        Query query = session.getNamedQuery("deleteStockDailyRecord");
        query.setInteger("name", 2);
        query.executeUpdate();
        t.commit();
    }

    private static void usingHql(Session session) {
        Query query = session.createQuery("delete from StockDailyRecord e where e.recordId in (:name)");
        query.setParameter("name", 545);
        int result = query.executeUpdate();
    }

    private static void deleteUsingIterator(Stock stock2) {
        Iterator<StockDailyRecord> iterator = stock2.getStockDailyRecords().iterator();
        Set deleteRecord = new HashSet();
        int i = 0;
        System.out.println("size before deletion" + stock2.getStockDailyRecords().size());
        // check values
        while (iterator.hasNext()) {
            StockDailyRecord dailyRecord = iterator.next();
            if (dailyRecord.getRecordId().equals(700)) {
                System.out.println("Last record ::" + i);
                deleteRecord.add(dailyRecord);
            }
            i++;
        }
        stock2.getStockDailyRecords().removeAll(deleteRecord);
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

    private static void createStockDailyRecotds(Session session, Stock stock) {

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
