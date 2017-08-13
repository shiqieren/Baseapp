package com.lyw.app.ui.bean.base;


/**
 * Created by huanghaibin
 * on 16-5-23.
 */
public class ResultBean<T> {
    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_UNKNOW = 0;
    public static final int RESULT_ERROR = -1;
    public static final int RESULT_NOT_FIND = 404;
    public static final int RESULT_NOT_LOGIN = 201;
    public static final int RESULT_TOKEN_EXPRIED = 202;
    public static final int RESULT_NOT_PERMISSION = 203;
    public static final int RESULT_TOKEN_ERROR = 204;

    private T result;
    private int code;
    private String message;
    private String time;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isOk() {
        return code == RESULT_SUCCESS;
    }

    public boolean isSuccess() {
        return code == RESULT_SUCCESS && result != null;
    }



    @Override
    public String toString() {
        return "code:" + code
                + " + message:" + message
                + " + time:" + time
                + " + result:" + (result != null ? result.toString() : null);
    }
}
