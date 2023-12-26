package ru.bikkul.compliment.telegram.bot.exception;

import org.springframework.http.HttpStatus;

public record ApiError(HttpStatus status, String reason, String exceptionMessage) {
}
