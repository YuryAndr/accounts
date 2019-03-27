package ru.andryakov.accounts.common.exception;

public class AccountServiceException extends RuntimeException {

    public AccountServiceException(String message, Exception e) {
        super(message, e);
    }
}
