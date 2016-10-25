package com.bupt.chengde.entity;

/**
 * 故障类型
 * 
 * @author wyf
 * @类 :
 * @version 1.0
 */
public class ResponseQuestionReasonMemo {
	/*
	 * 故障类型描述
	 */
	private String typeIntro;

	/*
	 * 故障类型编号
	 */
	private int typeId;
	/*
	 * 故障类型
	 */
	private int breakType;

	public String getTypeIntro() {
		return typeIntro;
	}

	public void setTypeIntro(String typeIntro) {
		this.typeIntro = typeIntro;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getBreakType() {
		return breakType;
	}

	public void setBreakType(int breakType) {
		this.breakType = breakType;
	}

	@Override
	public String toString() {
		return "ResponseQuestionReasonMemo [typeIntro=" + typeIntro
				+ ", typeId=" + typeId + ", breakType=" + breakType + "]";
	}

}
