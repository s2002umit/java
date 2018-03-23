package com.mkyong;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cisco.xmp.group.model.Group;
import com.mkyong.util.HibernateUtil;

public class AppGroupTest {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction t = session.beginTransaction();
//        
//        Group g1=new Group();
//        g1.setInstanceId(1L);
//        g1.setInstanceName("G1");
//        session.save(g1);
//        t.commit();
//        
//        t = session.beginTransaction();
        Group g2=new Group();
        g2.setInstanceId(6L);
        g2.setInstanceName("G11");
        session.save(g2);
        t.commit();
        
        t = session.beginTransaction();
        DetachedCriteria cr = DetachedCriteria.forClass(Group.class);
        cr.add(Restrictions.eq("instanceName", "G1"));
        List results= cr.getExecutableCriteria(session).list();
        Group parentGroup=(Group)results.get(0);
        parentGroup.getSubGroups().add(g2);
        
        session.save(g2);
        t.commit();
        
    }

}
