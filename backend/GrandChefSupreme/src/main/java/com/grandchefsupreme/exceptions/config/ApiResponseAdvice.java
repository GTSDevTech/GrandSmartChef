package com.grandchefsupreme.exceptions.config;

import com.grandchefsupreme.dto.ApiResponseDTO;
import com.grandchefsupreme.security.auth.AuthenticationResponseDTO;
import com.grandchefsupreme.utils.ApiResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@RestControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(
            @NonNull MethodParameter returnType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType
    ) {

        if (ApiResponseDTO.class.isAssignableFrom(returnType.getParameterType())) {
            return false;
        }

        return !AuthenticationResponseDTO.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response
    ) {

        if (!MediaType.APPLICATION_JSON.includes(selectedContentType)) {
            return body;
        }

        if (body == null) {
            return ApiResponseDTO.builder()
                    .success(true)
                    .message("OK")
                    .data(null)
                    .build();
        }

        HttpServletRequest servletRequest =
                ((ServletServerHttpRequest) request).getServletRequest();

        String message = (String) servletRequest.getAttribute(
                ApiResponseMessage.MESSAGE_ATTR
        );

        return ApiResponseDTO.builder()
                .success(true)
                .message(message != null ? message : "OK")
                .data(body)
                .build();
    }
}
