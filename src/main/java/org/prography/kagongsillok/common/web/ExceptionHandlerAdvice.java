package org.prography.kagongsillok.common.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prography.kagongsillok.auth.application.exception.AuthenticationException;
import org.prography.kagongsillok.common.exception.CanNotProceedException;
import org.prography.kagongsillok.common.exception.CommonSecurityException;
import org.prography.kagongsillok.common.exception.InvalidParamException;
import org.prography.kagongsillok.common.exception.NotFoundException;
import org.prography.kagongsillok.common.web.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(CommonSecurityException.class)
    public ResponseEntity<CommonResponse<Void>> handleSecurity(final CommonSecurityException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(CommonResponse.error(e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CommonResponse<Void>> handleAuthentication(final AuthenticationException e) {
        log.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.error(e.getMessage()));
    }

    @ExceptionHandler(InvalidParamException.class)
    public ResponseEntity<CommonResponse<Void>> handleInvalidArgument(final InvalidParamException e) {
        log.warn(e.getMessage());
        return ResponseEntity.badRequest().body(CommonResponse.error(e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResponse<Void>> handleNotExistException(final NotFoundException e) {
        log.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CommonResponse.error(e.getMessage()));
    }

    @ExceptionHandler(CanNotProceedException.class)
    public ResponseEntity<CommonResponse<Void>> handleCanNotProceed(final CanNotProceedException e) {
        log.warn(e.getMessage());
        return ResponseEntity.badRequest().body(CommonResponse.error(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> unhandledException(final Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.error("알 수 없는 예외 발생"));
    }
}
