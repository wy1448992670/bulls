package com.goochou.p2b.app.model;

public class AppResult {
    private String code; // 1成功0失败

    private String msg;

    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public AppResult(String code, String msg, Object data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public AppResult(String code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public AppResult(String code, Object data) {
        super();
        this.code = code;
        this.data = data;
    }

    public AppResult(String code) {
        super();
        this.code = code;
    }

}
