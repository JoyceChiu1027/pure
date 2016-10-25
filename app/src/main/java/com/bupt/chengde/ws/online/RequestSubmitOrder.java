package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

/**
 * 业务预定接口
 * 
 * @author wyf
 * 
 */
public class RequestSubmitOrder extends SoapObject {

    private String custId;
    private String custName;// 客户名
	private String phoneNo;// 手机号码
	private String address;// 客户地址
	private String orderType;// 产品类型
	private String orderBusId;// 产品具体类型
	private String idCardNo;// 客户身份证号
    private String caCardNo;

	public RequestSubmitOrder() {
		super("", "");
	}

	public String getCustName() {
		return custName;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderBusId() {
		return orderBusId;
	}

	public void setOrderBusId(String orderBusId) {
		this.orderBusId = orderBusId;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getCaCardNo() {
		return caCardNo;
	}

	public void setCaCardNo(String caCardNo) {
		this.caCardNo = caCardNo;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	public void setProperty(int index, Object obj) {
		switch (index) {
		case 0:
			custId=(String) obj;
			break;
		case 1:
			custName = (String) obj;
			break;
		case 2:
			phoneNo = (String) obj;
			break;
		case 3:
			address = (String) obj;
			break;

		case 4:
			orderType = (String) obj;
			break;

		case 5:
			orderBusId = (String) obj;
			break;

		case 6:
			idCardNo = (String) obj;
			break;
		case 7:
			caCardNo=(String)obj;
			break;
		default:
			break;
		}
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return custId;
		case 1:
			return custName;
		case 2:
			return phoneNo;
		case 3:
			return address;
		case 4:
			return orderType;
		case 5:
			return orderBusId;
		case 6:
			return idCardNo;
		case 7:
			return caCardNo;
		}
		return null;

	}

	@Override
	public void getPropertyInfo(int index, PropertyInfo info) {
		switch (index) {
		case 0:
			info.name = "custId";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 1:
			info.name = "custName";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 2:
			info.name = "phoneNo";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 3:
			info.name = "address";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 4:
			info.name = "orderType";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 5:
			info.name = "orderBusId";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 6:
			info.name = "idCardNo";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 7:
			info.name="caCardNo";
			info.namespace=CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type=String.class;
			break;
		}
	}
}
