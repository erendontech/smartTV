package com.stvc.api.entity;

import javax.ws.rs.core.Response;

public class ResponseSTVC<T> {

    public ResponseSTVC(Response.Status _status, T _data) {
        status = _status.getStatusCode();
        message = _status.getReasonPhrase();
        data = _data;
    }

    public ResponseSTVC(Response.Status _status,String customMessage, T _data) {
        status = _status.getStatusCode();
        message = customMessage;
        data = _data;
    }

    private int status;

    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
