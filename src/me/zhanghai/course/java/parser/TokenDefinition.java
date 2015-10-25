/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.parser;

import java.util.regex.Matcher;

/**
 * Definition of a type of token.
 */
public interface TokenDefinition {

    /**
     * Get the identifier for this type of token.
     *
     * @return The identifier.
     */
    Enum<?> getIdentifier();

    /**
     * Retrieve a matcher against specified input for this token.
     *
     * <p>This method may not be thread-safe, because it may reuse the same instance of
     * {@link Matcher} to avoid frequent allocation.</p>
     *
     * @param input The input to match against.
     * @return A {@link Matcher} that will match against the input.
     */
    Matcher getMatcher(CharSequence input);
}
