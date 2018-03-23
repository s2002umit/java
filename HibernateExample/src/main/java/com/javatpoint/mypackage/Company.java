package com.javatpoint.mypackage;

import java.util.HashSet;
import java.util.Set;

public class Company implements java.io.Serializable {

    private static final long serialVersionUID = 391801850457484494L;

    private Integer companyId;

    private byte[] xmlConfigFile;

    /**
     * @return the xmlConfigFile
     */
    public byte[] getXmlConfigFile() {
        return xmlConfigFile;
    }

    /**
     * @param xmlConfigFile
     *            the xmlConfigFile to set
     */
    public void setXmlConfigFile(byte[] xmlConfigFile) {
        this.xmlConfigFile = xmlConfigFile;
    }

    /**
     * @return the companyId
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId
     *            the companyId to set
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    private String companyName;

    private Set<Employee> employee = new HashSet<Employee>(0);

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName
     *            the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the employee
     */
    public Set<Employee> getEmployee() {
        return employee;
    }

    /**
     * @param employee
     *            the employee to set
     */
    public void setEmployee(Set<Employee> employee) {
        this.employee = employee;
    }

}
