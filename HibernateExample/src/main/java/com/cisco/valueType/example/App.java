package com.cisco.valueType.example;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mkyong.util.HibernateUtil;

public class App {

    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction t = session.beginTransaction();
        createRecord(session);

        // String[] last = {"Gupta"};
        // String hql = "select distinct p from Person p " +
        // "join p.name t " +
        // "where t.last in (:last)";
        // Query query = session.createQuery(hql);
        // query.setParameterList("last", last);

        // Query query = queryForValueObjectCollection(session);

        Query query = queryForMapUseCase(session);

        List<Person> pserson = query.list();
        System.err.println("size" + pserson.size());
        Criteria cr = session.createCriteria(Person.class);
        // cr.add(Restrictions.eq("name.last", "Gupta"));
        // cr.add(Restrictions.eq("additionalInfo.stringValue", "Router"));
        // // cr.add(Restrictions.eq("paramNamesAndValue.key", "123"));
        // // List results = cr.list();
        // List resultCount = cr.list();
        // System.err.println("size is "+resultCount.size());

    }

    private static Query queryForValueObjectCollection(Session session) {
        String hql = "select distinct p from Person p " + "join p.additionalInfo t  where t.stringValue='Router1'";
        Query query = session.createQuery(hql);
        return query;
    }

    private static Query queryForMapUseCase(Session session) {
        String[] stringValue = { "123" };
        String hql1 = "select distinct p from Person p " + "join p.paramNamesAndValue t "
                + "where :value in elements(t) and index(t) like 'deviceId' ";
        Query query = session.createQuery(hql1);
        query.setParameterList("value", stringValue);
        return query;
    }

    private static void createRecord(Session session) {
        Person person = new Person();
        Name name = new Name();
        name.setFirst("sumit");
        char c = 's';
        name.setInitial(c);
        name.setLast("Gupta");
        person.setName(name);
        person.setBirthday(new Date());
        Map parameValueMap = new HashMap();
        parameValueMap.put("deviceId", "123");

        Set<PropertyNameAndStringValue> set = new HashSet<PropertyNameAndStringValue>();
        PropertyNameAndStringValue propertyNameAndStringValue = new PropertyNameAndStringValue();
        propertyNameAndStringValue.setPropertyName("deviceName");
        propertyNameAndStringValue.setStringValue("Router");
        set.add(propertyNameAndStringValue);
        person.setAdditionalInfo(set);
        person.setParamNamesAndValue(parameValueMap);
        session.save(person);
        session.getTransaction().commit();
    }
}
