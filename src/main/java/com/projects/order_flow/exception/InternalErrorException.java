package com.projects.order_flow.exception;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(String mensagem) {
        super(mensagem);
    }
}
