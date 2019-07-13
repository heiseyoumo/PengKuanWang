package com.fancy.myapplication.event;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public class OrderEvent {
    private int status;

    public void setStatus(int status) {
        this.status = status;
    }

    public OrderEvent(int status) {
        this.status = status;
    }
}
