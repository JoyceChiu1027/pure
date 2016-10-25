package com.bupt.chengde.entity;

import java.util.ArrayList;
import java.util.List;

public class City {
    private String id;
    private String name;
    private List<Area> list = new ArrayList<Area>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Area> getList() {
        return list;
    }

    public void setList(List<Area> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "City [id=" + id + ", name=" + name + ", list=" + list + "]";
    }

}
