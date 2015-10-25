/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.parser;

import java.util.List;
import java.util.regex.Matcher;

/**
 * A simple stateless and whitespace-ignoring tokenizer implementation.
 */
public class Tokenizer {

    private Tokenizer() {}

    /**
     * Tokenize an input string.
     *
     * @param input The input string.
     * @param definitionList Definitions of tokens.
     * @param listener Listener for new token, tokenization completion or failure.
     */
    public static void tokenize(String input, List<Definition> definitionList,
                                Listener listener) {

        int position = 0;

        while (true) {

            while (position < input.length() && Character.isWhitespace(input.charAt(position))) {
                ++position;
            }
            if (position == input.length()) {
                break;
            }

            boolean matched = false;
            for (Definition definition : definitionList) {
                Matcher matcher = definition.getMatcher(input)
                        .region(position, input.length());
                if (matcher.lookingAt()) {
                    String text = matcher.group();
                    listener.onToken(definition.getIdentifier(), text);
                    position += text.length();
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                listener.onFailed(position);
                return;
            }
        }

        listener.onCompleted();
    }

    /**
     * Definition of one type of token.
     */
    public interface Definition {

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

    /**
     * Listener for new token, tokenization completion or failure.
     */
    public interface Listener {

        /**
         * Called when a new token is available.
         *
         * @param identifier The identifier of the new token, as in
         *                   {@link Definition#getIdentifier()}.
         * @param text The text of the new token.
         */
        void onToken(Enum<?> identifier, String text);

        /**
         * Called when tokenization completed.
         */
        void onCompleted();

        /**
         * Called when tokenization failed.
         *
         * @param position The position where the failure occurred.
         */
        void onFailed(int position);
    }
}
