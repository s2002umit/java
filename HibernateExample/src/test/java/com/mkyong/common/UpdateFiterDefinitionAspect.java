package com.mkyong.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.hibernate.util.DTDEntityResolver;
import org.hibernate.util.xml.MappingReader;
import org.hibernate.util.xml.OriginImpl;
import org.hibernate.util.xml.XmlDocument;
import org.xml.sax.InputSource;
import com.thoughtworks.xstream.mapper.MapperWrapper;

@Aspect
public class UpdateFiterDefinitionAspect {

    @Around("methodToBeRun()")
    public Object updateMappingAttrPointCut(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        if (args.length > 0) {
            Object target = args[0];
            if (target != null && target instanceof XmlDocument) {
                XmlDocument metadataXml = (XmlDocument) target;
                this.processTarget(metadataXml);
            }
        }

        return pjp.proceed();
    }

    private void processTarget(XmlDocument metadataXml) {
        Document doc = metadataXml.getDocumentTree();
        Element root = doc.getRootElement();
        String entityName = null;
        boolean isisInstanceOFInstanceImpl = false;
        for (Iterator i = root.elementIterator("class"); i.hasNext();) {
            Element foo = (Element) i.next();
            System.err.println("Entity name is" + foo.valueOf("@name"));
            entityName = foo.valueOf("@name");
            Class test = null;
            ;
            try {
                test = Class.forName(entityName);
                isisInstanceOFInstanceImpl = true;
            } catch (ClassNotFoundException e) {
                isisInstanceOFInstanceImpl = false;
                e.printStackTrace();
            }

//            if (test.isInstance(InstanceImpl.class)) {
//                System.err.println("inside if");
//            } else {
//                System.err.println("inside else");
//            }
        }

        if (isVersionColumnPresent(doc) || (isInstaceUUIDPresent(doc))) {

            for (Iterator i = root.elementIterator("hibernate-mapping"); i.hasNext();) {
                Element foo = (Element) i.next();
                foo.addElement("property").addAttribute("name", "tenantId").addAttribute("column", "tenantId");
            }
            
            for (Iterator i = root.elementIterator("class"); i.hasNext();) {
                Element foo = (Element) i.next();
                foo.addElement("property").addAttribute("name", "tenantId").addAttribute("column", "tenantId");
            }

            for (Iterator i = root.elementIterator("filter-def"); i.hasNext();) {
                Element foo = (Element) i.next();
                if (foo.attributeValue("name").equals("dataFilter")) {
                    foo.addElement("filter-param").addAttribute("name", "tenantId").addAttribute("type", "long");
                    foo.addAttribute("name", "kapanda");
                    foo.addAttribute("condition",
                            "AuthEntityId in (select rgm.resourceid from resourcegroupmember rgm where rgm.resourcegroup_id in (:scopeIds)) and tenantId in (select rgm.resourceid from resourcegroupmember rgm where rgm.resourcegroup_id in (:tenantId)) ");
                }
            }

            List list = doc.selectNodes("//hibernate-mapping/class/filter");
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                Node node = (Node) iter.next();
                if (node.valueOf("@name").equals("dataFilter")) {
                    ((Element) node).addAttribute("name", "kapanda");
                    ((Element) node).addAttribute("condition",
                            "AuthEntityId in (select rgm.resourceid from resourcegroupmember rgm where rgm.resourcegroup_id in (:scopeIds)) and tenantId in (select rgm.resourceid from resourcegroupmember rgm where rgm.resourcegroup_id in (:tenantId))");
                }
            }

        }

    }

    private boolean isInstaceUUIDPresent(Document doc) {
        boolean isInstaceUUIDPresent = false;
        for (Iterator j = doc.selectNodes("//hibernate-mapping/class/property").iterator(); j.hasNext();) {
            Element foo = (Element) j.next();
            if (foo.valueOf("@name").equals("instanceUuid")) {
                isInstaceUUIDPresent = true;
            }
        }
        System.err.println("isInstaceUUIDPresent ::" + isInstaceUUIDPresent);
        return isInstaceUUIDPresent;
    }

    private boolean isVersionColumnPresent(Document doc) {
        int size = doc.selectNodes("//hibernate-mapping/class/version").size();
        System.err.println("isVersionColumnPresent ::" + (size > 0));
        return size > 0;
    }

    @Pointcut("execution(public void org.hibernate.cfg.Configuration.add(org.hibernate.util.xml.XmlDocument))")
    public void methodToBeRun() {
    }

    private static void printDocument(Document documentTree) throws IOException {
        System.out.println("--------------------------------------------------------");
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(System.out, format);
        writer.write(documentTree);
        System.out.println("--------------------------------------------------------");
    }

    public static void main(String[] args) {
        try {
            Class test = Class.forName("com.mkyong.common.testAbstract");

            if (test.isInstance(MapperWrapper.class)) {
                System.err.println("inside if");
            } else {
                System.err.println("inside else");
            }
            File e = new File("src/main/resources/com/mkyong/stock/template.hbm.xml");
            InputSource inputSource = new InputSource(new FileInputStream(e));
            String originName = e.getAbsolutePath();
            DTDEntityResolver entityResolver = new DTDEntityResolver();
            OriginImpl origin = new OriginImpl("file", originName);
            XmlDocument metadataXml = MappingReader.INSTANCE.readMappingDocument(entityResolver, inputSource, origin);
            UpdateFiterDefinitionAspect customMappingModifier = new UpdateFiterDefinitionAspect();
            customMappingModifier.processTarget(metadataXml);
            printDocument(metadataXml.getDocumentTree());
        } catch (Exception arg7) {
            arg7.printStackTrace();
        }

    }
}
