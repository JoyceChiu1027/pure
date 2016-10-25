package com.bupt.chengde.entity;

public class ResponseMainMenu {
	private int mainMenuId;
	private String mainMenuName;
	private Class<?> clazz;
	private int resId;

	public int getMainMenuId() {
		return mainMenuId;
	}

	public void setMainMenuId(int mainMenuId) {
		this.mainMenuId = mainMenuId;
	}

	public String getMainMenuName() {
		return mainMenuName;
	}

	public void setMainMenuName(String mainMenuName) {
		this.mainMenuName = mainMenuName;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}

}
