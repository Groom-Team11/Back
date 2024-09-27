package com.bloomgroom.domain.user.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class BasicResponse <T>{ //API response 깃에 올라오면 수정봐야함

    private String message;
    private int statusCode;
    private T data;
    private HttpStatus status;

    // 모든 필드를 포함하는 생성자 추가
    public BasicResponse(String message, int statusCode, T data, HttpStatus status) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
        this.status = status;
    }

    public static <T> BasicResponse<T> ofSuccess(T data) {
        return new BasicResponse<>("SUCCESS", HttpStatus.OK.value(), data, HttpStatus.OK);
    }

    public static <T> BasicResponse<T> ofFailure(String message, HttpStatus status) {
        return new BasicResponse<>(message, status.value(), null, status);
    }

    public static <T> BasicResponse<T> ofSuccess(T data, String message) {
        return new BasicResponse<>(message, HttpStatus.OK.value(), data, HttpStatus.OK);
    }
}
