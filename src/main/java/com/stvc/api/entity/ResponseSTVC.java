package com.stvc.api.entity;

import javax.ws.rs.core.Response;
import java.util.Calendar;

public class ResponseSTVC<T> {

    public ResponseSTVC(String _path, Response.Status _status, T _data) {
        timestamp = Calendar.getInstance().getTimeInMillis();
        path = _path;
        status = _status;
        data = _data;
    }

    private long timestamp;

    private String path;

    private Response.Status status;

    private T data;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Response.Status getStatus() {
        return status;
    }

    public void setStatus(Response.Status status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
