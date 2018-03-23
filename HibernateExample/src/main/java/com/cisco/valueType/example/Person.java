package com.cisco.valueType.example;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class Person {
    private java.util.Date birthday;
    private Name name;
    private String key;
    
    private Map paramNamesAndValue;
    
    private Set<PropertyNameAndStringValue> additionalInfo;

   

    public Set<PropertyNameAndStringValue> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(Set<PropertyNameAndStringValue> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Map getParamNamesAndValue() {
        return paramNamesAndValue;
    }

    public void setParamNamesAndValue(Map paramNamesAndValue) {
        this.paramNamesAndValue = paramNamesAndValue;
    }

}
