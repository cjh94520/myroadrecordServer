package com.smartman.myroadrecord.bean;


/**
 * Created by jiahui.chen on 2016/1/25.
 */
public class BaseBean {
    public static final int FAILED = -1;   //失败
    public static final int EXISTED = 0;  //已存在
    public static final int SUCCEED = 1;  //成功

    public int code;
    public String msg;

    public int getCode() {
        return code;
    }

    public BaseBean() {
    }

    public BaseBean(int type) {
        this();
        switch (type) {
            case FAILED:
                code = -1;
                msg = "失败";
                break;
            case EXISTED:
                code = 0;
                msg = "已存在";
                break;
            case SUCCEED:
                code = 1;
                msg = "成功";
                break;
        }
    }

    public void setCode(int code) {
        switch (code) {
            case FAILED:
                this.code = -1;
                msg = "失败";
                break;
            case EXISTED:
                this.code = 0;
                msg = "已存在";
                break;
            case SUCCEED:
                this.code = 1;
                msg = "成功";
                break;
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
