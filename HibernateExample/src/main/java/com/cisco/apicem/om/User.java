package com.cisco.apicem.om;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonWriteNullProperties;

public class User {

    private String name;
    @JsonWriteNullProperties

    private A a;

  

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    private int age;

    // private List<String> messages;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    // public List<String> getMessages() {
    // return messages;
    // }
    // public void setMessages(List<String> messages) {
    // this.messages = messages;
    // }

}
