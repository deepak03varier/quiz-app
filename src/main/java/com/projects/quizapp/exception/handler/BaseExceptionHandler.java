package com.projects.quizapp.exception.handler;

import com.projects.quizapp.exception.exceptions.BadRequestException;
import com.projects.quizapp.exception.exceptions.InternalServerException;
import com.projects.quizapp.exception.exceptions.UnauthorizedException;
import com.projects.quizapp.model.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse handleBadRequestException(final BadRequestException exception) {
        return BaseResponse.ofFailure(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResponse handleBadRequestException(final InternalServerException exception) {
        return BaseResponse.ofFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public BaseResponse handleUnauthorizedException(final UnauthorizedException exception) {
        return BaseResponse.ofFailure(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
    }
}
