package com.mkyong;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cisco.xmp.group.model.Group1;
import com.cisco.xmp.group.model.Story;
import com.mkyong.util.HibernateUtil;

public class AppGroupTest1 {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction t = session.beginTransaction();

//        t = session.beginTransaction();
//        Story g2 = new Story();
//        g2.setInfo("34432234");
//        session.save(g2);
//        t.commit();
//
//        t = session.beginTransaction();
//        DetachedCriteria cr = DetachedCriteria.forClass(Group1.class);
//        cr.add(Restrictions.eq("name", "Group Name"));
//        List results = cr.getExecutableCriteria(session).list();
//        Group1 parentGroup = (Group1) results.get(0);
//        parentGroup.getStories().add(g2);
//
//        session.save(g2);
//        t.commit();

         Group1 sp = new Group1("Group Name");
        
         ArrayList list = new ArrayList();
         list.add(new Story("Story Name 1"));
         list.add(new Story("Story Name 2"));
         sp.setStories(list);
        
         Transaction transaction = null;
        
         try {
         transaction = session.beginTransaction();
         session.save(sp);
         transaction.commit();
         } catch (Exception e) {
         if (transaction != null) {
         transaction.rollback();
        
         }
         } finally {
         session.close();
         }

    }

}
