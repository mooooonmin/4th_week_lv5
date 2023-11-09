package com.level5.basket.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // TODO 중복 예외처리 정리하기

    // IllegalArgumentException 잘못된 입력
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorMessage errorMessage = ErrorMessage.INVALID_INPUT;
        int status = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(errorMessage.getMessage(), status));
    }

    // NoSuchElementException 데이터 못찾을 때
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
        ErrorMessage errorMessage = ErrorMessage.DATA_NOT_FOUND;
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(errorMessage.getMessage(), status));
    }

    // USER_NOT_FOUND 사용자 못찾음
    @ExceptionHandler(CustomException.UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException.UserNotFoundException e) {
        ErrorMessage errorMessage = ErrorMessage.USER_NOT_FOUND;
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(errorMessage.getMessage(), status));
    }


    // InvalidPasswordException 비밀번호 불일치
    @ExceptionHandler(CustomException.InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPasswordException(CustomException.InvalidPasswordException e) {
        ErrorMessage errorMessage = ErrorMessage.InvalidPasswordException;
        int status = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(errorMessage.getMessage(), status));
    }

    // AuthenticationException 인증실패 -> 시큐리티에서 제공하는 기본 예외, 인증과정에서 발생할 수 있는 예외의 상위 클래스
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        ErrorMessage errorMessage = ErrorMessage.UNAUTHORIZED;
        int status = HttpStatus.UNAUTHORIZED.value();
        return ResponseEntity.status(status).body(new ErrorResponse(errorMessage.getMessage(), status));
    }

    @ExceptionHandler({CustomException.AlreadyExistsException.class,
            CustomException.InvalidTokenException.class,
            CustomException.TokenExpiredException.class})
    public ResponseEntity<ErrorResponse> handleCustomExceptions(RuntimeException e) {
        ErrorMessage errorMessage = ErrorMessage.valueOf(e.getClass().getSimpleName());
        int status = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(status).body(new ErrorResponse(errorMessage.getMessage(), status));
    }

    // 권한이 없을 때
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        ErrorMessage errorMessage = ErrorMessage.FORBIDDEN;
        int status = HttpStatus.FORBIDDEN.value();
        return ResponseEntity.status(status).body(new ErrorResponse(errorMessage.getMessage(), status));
    }

    // IOException 일 때
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException e) {
        ErrorMessage errorMessage = ErrorMessage.INTERNAL_SERVER_ERROR;
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return ResponseEntity.status(status).body(new ErrorResponse(errorMessage.getMessage(), status));
    }

    // RuntimeException 일 때
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        ErrorMessage errorMessage = ErrorMessage.INTERNAL_SERVER_ERROR;
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return ResponseEntity.status(status).body(new ErrorResponse(errorMessage.getMessage(), status));
    }

}
