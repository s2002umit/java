package com.mkyong;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.javatpoint.mypackage.Company;
import com.mkyong.stock.Employee;
import com.mkyong.stock.Stock;
import com.mkyong.stock.StockDailyRecord;
import com.mkyong.util.HibernateUtil;

public class Locking {

	public static void main(String[] args) {

		try {
		System.out.println("Hibernate one to many (XML Mapping)");
		Session session = HibernateUtil.getSessionFactory().openSession();
			Session session1 = HibernateUtil.getSessionFactory().openSession();


		// Transaction t3 = session.beginTransaction();
		// createRecord(session);
		// t3.commit();

		Stock stock2 = getStock(session);
			Stock stock3 = getStock(session1);
			Transaction t = session.beginTransaction();
            stock2.setStockName("test15");
			System.err.println("============" + stock2.getVersion());

			System.err.println("============" + stock3.getVersion());
			session.save(stock2);
			
			Transaction t1 = session1.beginTransaction();

			System.err.println("============" + stock3.getVersion());
            stock3.setStockCode("test16");
			session1.save(stock3);

			t1.commit();
			System.err.println("============" + stock2.getVersion());
			System.err.println("=====8888888=======" + stock3.getVersion());
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param session
	 * @return
	 */
	private static Stock getStock(Session session) {
		Criteria cr = session.createCriteria(Stock.class);

		// To get records having salary more than 2000
		cr.add(Restrictions.eq("stockCode", "7052"));
		List results = cr.list();
		// Stock stock2 = (Stock) results.get(0);
		return (Stock) session.get(Stock.class, 1);

	}

	private static void createRecord(Session session) {
		Stock stock = new Stock();
		stock.setStockCode("7052");
		stock.setStockName("PADINI1");

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


	}

}
