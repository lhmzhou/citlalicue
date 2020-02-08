package com.citlalicue.messaging.msg;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private Integer status;
    private String error;
    private T data;
    private Map<String, String> additionalProperties;

    public ApiResponse() {
        this.status = 200;
    }

    public ApiResponse(T data) {
        this.status = 200;
        this.data = data;
    }

    public ApiResponse(Integer status, String error) {
        this.status = status;
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, String> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, String> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    @Override
    public String toString() {
        return new StringBuilder("ApiResponse")
        .append("status=").append(status)
        .append("error=").append(error)
        .append("data=").append(data).toString();
    }
}