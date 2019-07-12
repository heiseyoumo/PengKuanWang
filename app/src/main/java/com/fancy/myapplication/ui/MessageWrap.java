package com.fancy.myapplication.ui;

/**
 * @author pengkuanwang
 * @date 2019-07-12
 */
class MessageWrap {
    public final String message;

    public static MessageWrap getInstance(String message) {
        return new MessageWrap(message);
    }

    private MessageWrap(String message) {
        this.message = message;
    }
}
