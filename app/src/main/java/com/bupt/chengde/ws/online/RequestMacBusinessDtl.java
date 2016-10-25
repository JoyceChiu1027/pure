package com.bupt.chengde.ws.online;

import java.util.Hashtable;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

/**
 * @author wyf
 * @version 1.0
 * @类 :宽带业务详情
 */
public class RequestMacBusinessDtl extends SoapObject {

    private String mbId;


    public RequestMacBusinessDtl() {
        super("", "");
    }

    public void setMbId(String mbId) {
        this.mbId = mbId;
    }

    public String getMbId(String mbId) {
        return this.mbId;
    }


    public int getPropertyCount() {
        return 1;
    }

    public Object getProperty(int __index) {
        switch (__index) {
            case 0:
                return mbId;


        }
        return null;
    }

    public void setProperty(int __index, Object __obj) {
        switch (__index) {
            case 0:
                mbId = (String) __obj;
                break;


        }
    }

    @SuppressWarnings("rawtypes")
    public void getPropertyInfo(int __index, Hashtable __table, PropertyInfo __info) {
        switch (__index) {
            case 0:
                __info.name = "mbId";
                __info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
                __info.type = String.class;
                break;


        }
    }
}
