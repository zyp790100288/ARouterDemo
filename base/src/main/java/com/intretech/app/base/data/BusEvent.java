package com.intretech.app.base.data;

/**
 * Created by yq06171 on 2018/7/4.
 * 事件总线传递的数据
 */

public class BusEvent<T> extends BaseData {
    private String type;
    private T data;

    public BusEvent(String type, T data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
