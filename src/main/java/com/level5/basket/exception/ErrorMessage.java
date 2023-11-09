package com.level5.basket.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorMessage {

    // 반환될 에러 메세지들 TODO 여유있을 때 추가하고 정리하기
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    INVALID_INPUT("데이터를 잘못 입력하셨습니다."),
    UNAUTHORIZED("승인되지 않은 접근입니다."),
    FORBIDDEN("권한이 없습니다."),
    InvalidPasswordException("비밀번호가 일치하지 않습니다."),
    BAD_REQUEST("잘못된 요청입니다."),
    ALREADY_EXISTS("이미 존재하는 데이터입니다."),
    DATA_NOT_FOUND("요청하신 데이터를 찾을 수 없습니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED("토큰이 만료되었습니다."),

    // TODO 당장은 사용하지 않지만 나중에 사용할 수 있는 메세지,
    INTERNAL_SERVER_ERROR("서버 내부 오류가 발생했습니다."),
    CONFLICT("요청이 서버의 현재 상태와 충돌합니다."),
    TOO_MANY_REQUESTS("너무 많은 요청을 보내셨습니다. 잠시 후 다시 시도해주세요."),
    SERVICE_UNAVAILABLE("서비스를 일시적으로 이용할 수 없습니다."),
    PAYLOAD_TOO_LARGE("요청 데이터가 너무 큽니다."),
    UNSUPPORTED_MEDIA_TYPE("지원하지 않는 미디어 타입입니다."),
    NOT_IMPLEMENTED("요청하신 기능은 아직 구현되지 않았습니다.")
    ;

    private final String message;

    public String getMessage() {
        return message;
    }
}

