package com.bh.wechat.exception;

import java.util.ArrayList;
import java.util.List;

import com.bh.wechat.constant.ResponseCode;

public class BhException extends Exception {

    private static final long serialVersionUID = -3843875618738029785L;

    private List<String> errorMessages;

    private ResponseCode code;

    public BhException() {}

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void setCode(ResponseCode code) {
        this.code = code;
    }

    public ResponseCode getCode() {
        return code;
    }

    public void addErrorMessage(String message) {
        if (null == errorMessages) {
            errorMessages = new ArrayList<String>();
        }
        errorMessages.add(message);
    }

	@Override
	public String getMessage() {
		return code + " - " + errorMessages;
	}

	@Override
	public String getLocalizedMessage() {
		return getMessage();
	}
}
