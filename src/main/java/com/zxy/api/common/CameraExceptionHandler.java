package com.zxy.api.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常类处理类
 */
@RestControllerAdvice
public class CameraExceptionHandler {
    @ExceptionHandler(value = CameraException.class)
    Object handleException(CameraException e, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", e.getCode());
        map.put("message", e.getMessage());
        map.put("url", request.getRequestURL());

        return map;
    }
}
