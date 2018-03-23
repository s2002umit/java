package com.cisco.xmp.group.model;

import java.util.List;

public class Group1 {
    private int id;
    private String name;
    private List stories;

    public Group1(){
    }

    public Group1(String name) {
      this.name = name;
    }

    public void setId(int i) {
      id = i;
    }

    public int getId() {
      return id;
    }

    public void setName(String n) {
      name = n;
    }

    public String getName() {
      return name;
    }

    public void setStories(List l) {
      stories = l;
    }

    public List getStories() {
      return stories;
    }
  }