package com.mkyong;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import org.hibernate.HibernateException;

import com.mkyong.stock.Stock;
import com.mkyong.stock.StockDailyRecord;
import com.mysql.jdbc.PreparedStatement;
import com.thoughtworks.xstream.XStream;

public class SerializeDemo {
	public static void serializeObject(Object e) {

		// _handleNonOracleClob(null, e, 0);
		try {
			FileOutputStream fileOut = new FileOutputStream("/tmp/employee.xml");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(e);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in /tmp/employee.xml");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public static void deSerializeObject() {

		// Stock e = (Stock) get();
		Stock e = null;
		try {

			FileInputStream fileIn = new FileInputStream("/tmp/employee.xml");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			e = (Stock) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Employee class not found");
			c.printStackTrace();
			return;
		}
		System.err.println("Deserialized Employee111..." + e.getStockName());

		System.err.println("Name:1111 " + e.getStockDailyRecords());

		System.out.println("Name: " + e.getStockDailyRecords().size());

		Set<StockDailyRecord> st = e.getStockDailyRecords();
		System.out.println("Name: " + st.iterator().next().getPriceChange());
	}

	private static Object get() {
		FileInputStream fileIn;
		try {

			// String test1 =
			// "<com.mkyong.stock.Stock> <stockId>1</stockId> <stockCode>7052</stockCode> <stockName>PADINI1</stockName> <stockDailyRecords class=\"org.hibernate.collection.PersistentSet\"> <initialized>false</initialized> <owner class=\"com.mkyong.stock.Stock\" reference=\"../..\"/> <cachedSize>-1</cachedSize> <role>com.mkyong.stock.Stock.stockDailyRecords</role> <key class=\"int\">1</key> <dirty>false</dirty> </stockDailyRecords> </com.mkyong.stock.Stock>";

			// fileIn = new FileInputStream("/tmp/employee.xml");
			// ObjectInputStream in = new ObjectInputStream(fileIn);
			String strValue = readFile("/tmp/employee.xml",
					StandardCharsets.UTF_8);
			System.err.println("=====deserialize ======" + strValue);
			if (strValue == null || strValue.length() == 0) {
				return null;
			}
			try {

				// XStream xstream = new XStream() {
				// @Override
				// protected MapperWrapper wrapMapper(MapperWrapper next) {
				// MapperWrapper mapper = new JavassistMapper(next);
				// return mapper;
				// }
				// };
				// xstream.registerConverter(new
				// CustomJavassistEnhancedConverter(
				// xstream.getMapper(), xstream.getConverterLookup()));
				// Return Object representation of the XML
				return (Object) ((new XStream()).fromXML(strValue));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;

	}

	private static void _handleNonOracleClob(PreparedStatement st,
			Object value, int index) throws HibernateException {
		Writer outWriter = null;
		ByteArrayOutputStream baos = null;
		InputStream is = null;
		try {
			baos = new ByteArrayOutputStream();
			outWriter = new BufferedWriter(new PrintWriter("/tmp/employee.xml"));
			// Get XML representation of the Object
			if (value != null) {
				// String test = (new XStream()).toXML(value);
				// System.err.println("serialized output" + test);
				(new XStream()).toXML(value, baos);
			}
			outWriter.write((baos.size() != 0) ? (String) baos.toString() : "");
			outWriter.flush();
			is = new ByteArrayInputStream(baos.toByteArray());
			// st.setAsciiStream(index, is, is.available());
		} catch (Exception e) {
			throw new HibernateException(e);
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException ioe) {
					// ok
				}
			}
			if (outWriter != null) {
				try {
					outWriter.close();
				} catch (IOException ioe) {
					// ok
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException ioe) {
					// ok;
				}
			}
		}
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}