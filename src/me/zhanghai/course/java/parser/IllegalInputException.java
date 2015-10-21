/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.parser;

/**
 * Exception thrown by a {@link Lexer} or {@link Parser} to signal bad input.
 */
public class IllegalInputException extends Exception {

    /**
     * {@inheritDoc}
     */
    public IllegalInputException() {}

    /**
     * {@inheritDoc}
     */
    public IllegalInputException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public IllegalInputException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public IllegalInputException(Throwable cause) {
        super(cause);
    }

    /**
     * {@inheritDoc}
     */
    public IllegalInputException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
