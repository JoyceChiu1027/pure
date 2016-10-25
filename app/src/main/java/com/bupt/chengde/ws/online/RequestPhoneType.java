package com.bupt.chengde.ws.online;

import java.util.Hashtable;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

/**
 * Created by wyf on 2015/12/15.
 * 检查版本的请求参数
 */
public class RequestPhoneType extends SoapObject {

    private String phoneType;//手机类型标识,0代表Android手机，1代表IOS手机

    public RequestPhoneType() {
        super("", "");
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public int getPropertyCount() {
        return 1;
    }

    public Object getProperty(int __index) {
        switch (__index) {
            case 0:
                return phoneType;
        }
        return null;
    }

    public void setProperty(int __index, Object __obj) {
        switch (__index) {
            case 0:
                phoneType = (String) __obj;
                break;
        }
    }

    @SuppressWarnings("rawtypes")
    public void getPropertyInfo(int __index, Hashtable __table,
                                PropertyInfo __info) {
        switch (__index) {
            case 0:
                __info.name = "phoneType";
                __info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
                __info.type = String.class;
                break;

        }
    }

}
