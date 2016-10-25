package com.bupt.chengde.entity;

import java.util.ArrayList;
import java.util.List;

public class Province {
	private String id;
	private String name;
	private List<City> list = new ArrayList<City>();
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
	public List<City> getList() {
		return list;
	}
	public void setList(List<City> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "Province [id=" + id + ", name=" + name + ", list=" + list + "]";
	}
}
