package com.tuyendv.web.backend.api.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ApiResponse<T> extends ResponseEntity<Map<String, Object>> {

    private String code;

    private String message;

    private T data;

    private static <T> Map<String, Object> createBody (ApiStatus apiStatus, T data) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", apiStatus.getCode());
        body.put("message", apiStatus.getMessage());
        body.put("data", data);
        return body;
    }

    public ApiResponse(ApiStatus apiStatus, T data) {
        super(createBody(apiStatus, data), apiStatus.getHttpStatus());
        this.code = apiStatus.getCode();
        this.message = apiStatus.getMessage();
        this.data = data;
    }

}
