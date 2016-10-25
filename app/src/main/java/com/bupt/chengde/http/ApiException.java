package com.bupt.chengde.http;

/**
 * Created by joycezhao on 16/10/24.
 */

public class ApiException extends RuntimeException {

    public final static int CODE_20040 = 20040;

    // public ApiException(int resultCode) {
    // this(getApiExceptionMessage(resultCode));
    // }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    // /**
    // * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解 需要根据错误码对错误信息进行一个转换，在显示给用户
    // *
    // * @param code
    // * @return
    // */
    // private static String getApiExceptionMessage(int code) {
    // String message = null;
    // switch (code) {
    // case CODE_20040:
    // message = "该用户不存在";
    // break;
    // default:
    // message = "未知错误";
    //
    // }
    // return message;
    // }

}
