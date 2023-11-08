package com.level5.basket.global;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }

    // 사용자 없음
    public static class UserNotFoundException extends CustomException {
        public UserNotFoundException() {
            super(ErrorMessage.USER_NOT_FOUND.getMessage());
        }
    }

    // 비밀번호 불일치
    public static class InvalidPasswordException extends CustomException {
        public InvalidPasswordException() {
            super(ErrorMessage.InvalidPasswordException.getMessage());
        }
    }

    // 이미 존재하는 데이터
    public static class AlreadyExistsException extends CustomException {
        public AlreadyExistsException() {
            super(ErrorMessage.ALREADY_EXISTS.getMessage());
        }
    }

    // 유효하지 않은 토큰
    public static class InvalidTokenException extends CustomException {
        public InvalidTokenException() {
            super(ErrorMessage.INVALID_TOKEN.getMessage());
        }
    }

    // 토큰이 만료된 경우
    public static class TokenExpiredException extends CustomException {
        public TokenExpiredException() {
            super(ErrorMessage.TOKEN_EXPIRED.getMessage());
        }
    }

    // 강의 없음
    public static class CourseNotFoundException extends CustomException {
        public CourseNotFoundException() {
            super(ErrorMessage.DATA_NOT_FOUND.getMessage());
        }
    }

    // 댓글 못찾음
    public static class CommentNotFoundException extends CustomException {
        public CommentNotFoundException() {
            super(ErrorMessage.DATA_NOT_FOUND.getMessage());
        }
    }

    public static class UnauthorizedActionException extends CustomException {
        public UnauthorizedActionException(String message) {
            super(ErrorMessage.FORBIDDEN.getMessage());
        }
    }
}