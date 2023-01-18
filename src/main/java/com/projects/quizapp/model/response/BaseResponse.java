package com.projects.quizapp.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {

    private Boolean success;
    private Object data;
    private Integer status;
    private String message;

    public static BaseResponse ofFailure(Integer status, String message) {
        return BaseResponse.builder()
                           .success(false)
                           .status(status)
                           .message(message)
                           .build();
    }

    public static BaseResponse ofSuccess(Object data) {
        return BaseResponse.builder()
                           .success(true)
                           .status(HttpStatus.OK.value())
                           .data(data)
                           .build();
    }

    public static BaseResponse ofSuccess() {
        return BaseResponse.builder()
                           .success(true)
                           .status(HttpStatus.OK.value())
                           .build();
    }
}
