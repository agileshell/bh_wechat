package com.bh.wechat.constant;


public enum ResponseCode {

    UNAUTHORIZED(401),

    SERVER_ERROR(500);

    private int value;

    private ResponseCode(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ResponseCode getCode(int value) {
        if (value == 0) {
            return null;
        }

        ResponseCode[] codes = values();
        for (ResponseCode code : codes) {
            if (value == code.getValue()) {
                return code;
            }
        }

        return null;
    }
}
