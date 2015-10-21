/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.parser;

import java.util.regex.Matcher;

/**
 * Definition of a type of terminal.
 */
public interface TerminalDefinition {

    /**
     * Get the identifying {@link Enum} for this type of terminal.
     *
     * @return The identifying {@link Enum}.
     */
    Enum<?> getType();

    /**
     * Retrieve a matcher against specified input for this terminal.
     *
     * <p>This method is not thread-safe, because it reuses the same instance of {@link Matcher} to
     * avoid frequent allocation.</p>
     *
     * @param input The input to match against.
     * @return A {@link Matcher} that will match against the input.
     */
    Matcher getMatcher(CharSequence input);
}
