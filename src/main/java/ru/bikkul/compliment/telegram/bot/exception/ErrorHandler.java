package ru.bikkul.compliment.telegram.bot.exception;

import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler({
            ServletException.class,
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            NumberFormatException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError badRequestExceptionHandle(final Exception e) {
        log.error("400 bad request exception reason:{}", e.getMessage(), e);
        String reason = "Incorrectly made request";
        String msg = e.getMessage();
        return new ApiError(HttpStatus.BAD_REQUEST, reason, msg);
    }

    @ExceptionHandler(UniqueConstraintException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError uniqueConstraintException(final UniqueConstraintException e) {
        log.error("409 conflict exception reason:{}", e.getMessage(), e);
        String reason = "For the requested operation the conditions are not met";
        String msg = e.getMessage();
        return new ApiError(HttpStatus.CONFLICT, reason, msg);
    }
}
