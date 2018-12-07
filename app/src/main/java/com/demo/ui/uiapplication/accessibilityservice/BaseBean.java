package com.demo.ui.uiapplication.accessibilityservice;

/**
 * Created by Vincent.Lei on 2018/11/1.
 * Title：
 * Note：
 */
public class BaseBean<T> {
    private String code;
    private String msg;
    public T data;

    public T getData() {
        return data;
    }
}
