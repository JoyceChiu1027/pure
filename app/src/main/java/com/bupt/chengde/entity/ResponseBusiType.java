package com.bupt.chengde.entity;

/**
 * @author wyf
 * @类 :获取业务类型
 * @version 1.0
 */
public class ResponseBusiType {
	private String typeId;//业务类型ID
	private String typeName;// 业务名称
	private String typeValue;// 业务描述
	private String typeImage;
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeValue() {
		return typeValue;
	}
	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
	public String getTypeImage() {
		return typeImage;
	}
	public void setTypeImage(String typeImage) {
		this.typeImage = typeImage;
	}
	

	
}
