package com.cisco.apicem.group.model.enumIssue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cisco.apicem.group.model.GroupType;
import com.cisco.apicem.group.model.ResourceGroup;
import com.cisco.apicem.group.model.ResourceGroupScopeRule;
import com.mkyong.util.HibernateUtil;

public class APP {

    public static void main(String[] args) {
       
        
        String test="file:/" + "/user/sumgupt2" + "/pluginpoc-map-7.1.0-SNAPSHOT.jar";
        System.err.println(test.replace("/", "//"));
        Session session = HibernateUtil.getSessionFactory().openSession();
        //
        // Transaction t = session.beginTransaction();
        //
        // createRecord(session, "test7", 1005L);
        //
        // t.commit();

        // Query query = session.getNamedQuery("findGroupsByName");
        // query.setString("sortBy", "name");
        // List<ResourceGroup> groups = query.list();
        // FlushMode currMode = session.getFlushMode();
        // System.err.println("==========="+currMode);
        // session.setFlushMode(FlushMode.COMMIT);
        // for (int i = 0; i < groups.size(); i++) {
        // System.err.println("grpup name ::" + groups.get(i).getName());
        // session.delete(groups.get(i));
        // }
        // session.setFlushMode(currMode);

        // createRecord(session,"test6",1002L);
        //

        // Query query1 = session.getNamedQuery("findGroupsByName");
        // query1.setString("sortBy", "name");
        // query1.setString("groupType",Integer.toString(GroupType.POLICY.getValue()));
        // query1.setString("groupType1",Integer.toString(GroupType.RBAC.getValue()));

        // ResourceGroup resourceGroup= (ResourceGroup)
        // session.load(ResourceGroup.class, 1003L);
        // System.err.println("grup name is"+resourceGroup.getName());

        // Criteria query1 = session.createCriteria(ResourceGroup.class);
        // List<ResourceGroup> groups = query1.list();
        //
        Query query1 = session.getNamedQuery("getGroup");
        List<ResourceGroup> groups = query1.list();

        // Filter filter1= session.enableFilter("dataFilter1");
        // filter1.setParameter("groupType",
        // Integer.toString(GroupType.RBAC.getValue()));

        System.out.println("Groups fetched Successfully");
        for (int i = 0; i < groups.size(); i++) {

            if (groups.get(i).getName().equals("2323")) {
                System.out.println("Printing groups after delete" + groups.get(i).getName());
                System.out.println("Printing groups after delete" + groups.get(i).getResourceGroupScopeRule());
                groups.get(i).getResourceGroupScopeRule().setGroupType(GroupType.SITE);
                Transaction t = session.beginTransaction();
                session.update(groups.get(i));
                t.commit();
            }
            // session.delete(groups.get(i));
        }

        // System.err.println("creating another group");
        // createRecord(session,"test4",1002L);
        // t.rollback();

        // Filter filter = session.enableFilter("dataFilter");
        //
        // Iterator
        // itr=session.getSessionFactory().getDefinedFilterNames().iterator();
        //
        // while (itr.hasNext()) {
        // System.out.println(itr.next());
        // }
        // for (Iterator iterator =
        // filter.getFilterDefinition().getParameterNames().iterator();
        // iterator.hasNext();) {
        // System.err.println(iterator.next());
        //
        // }
        // filter.setParameter("groupName", "suimit");
        // Query query = session.getNamedQuery("findGroupsByName");
        // // query.setString("name", "test1");
        // query.setString("sortBy", "name");

        // query.setString("orderBy", "asc");

        // Criteria cr = session.createCriteria(ResourceGroup.class);
        // cr.add(Restrictions.eq("name", "test"));
        //
        //
        // Disjunction or = Restrictions.disjunction();
        //
        // for (GroupType type : GroupType.values()) {
        // or.add(Restrictions.eq("groupTypeList", type));
        // }
        // cr.add(or);
        // List results = cr.list();
        // System.err.println("size of the list is " + employees.size());

    }

    private static void createRecord(Session session, String name, Long instanceId) {
        ResourceGroup group = new ResourceGroup();
        Set<GroupType> groupTypeList = new HashSet<GroupType>();
        groupTypeList.add(GroupType.POLICY);
        // groupTypeList.add(GroupType.POLICY);
        // groupTypeList.add(GroupType.SITE);
        // group.setInstanceId(101l);
        group.setInstanceId(instanceId);
        group.setName(name);
        group.setGroupTypeList(groupTypeList);

        ResourceGroupScopeRule scopeRule = new ResourceGroupScopeRule();
        scopeRule.setGroupType(GroupType.POLICY);
        scopeRule.setResourceGroup(group);
        group.setResourceGroupScopeRule(scopeRule);
        session.save(group);

    }

}
