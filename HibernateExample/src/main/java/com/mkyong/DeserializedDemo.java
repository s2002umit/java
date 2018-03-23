package com.mkyong;



public class DeserializedDemo {

	public static void main(String[] args) {
		// Session session = HibernateUtil.getSessionFactory().openSession();
		// Stock stock2 = (Stock) session.get(Stock.class, 1);
		SerializeDemo.deSerializeObject();

	}

}
