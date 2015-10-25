/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java;

import java.util.regex.Matcher;

/**
 * A simple stateless and whitespace-ignoring tokenizer implementation.
 *
 * @param <T> The type of token definition.
 */
public class Tokenizer<T extends Tokenizer.Definition> {

    private T[] definitions;
    private Listener<T> listener;

    private boolean canceled;

    /**
     * Create a new {@link Tokenizer}.
     *
     * @param definitions definitions Definitions of tokens.
     * @param listener Listener for new token, tokenization completion or failure.
     */
    public Tokenizer(T[] definitions, Listener<T> listener) {
        this.definitions = definitions;
        this.listener = listener;
    }

    /**
     * Tokenize an input string.
     *
     * @param input The input string.
     */
    public void tokenize(String input) {

        int position = 0;

        while (true) {

            while (position < input.length() && Character.isWhitespace(input.charAt(position))) {
                ++position;
            }
            if (position == input.length()) {
                break;
            }

            boolean matched = false;
            for (T definition : definitions) {
                Matcher matcher = definition.getMatcher(input)
                        .region(position, input.length());
                if (matcher.lookingAt()) {
                    String text = matcher.group();
                    position += text.length();
                    matched = true;
                    listener.onToken(definition, text);
                    if (canceled) {
                        return;
                    }
                    break;
                }
            }

            if (!matched) {
                listener.onFailed(position);
                return;
            }
        }

        if (!canceled) {
            listener.onCompleted();
        }
    }

    /**
     * Cancel the tokenization; listener won't be called any more until next tokenization.
     */
    public void cancel() {
        canceled = true;
    }

    /**
     * Reset this tokenizer for next tokenization.
     */
    public void reset() {
        canceled = false;
    }

    /**
     * Definition of one type of token.
     */
    public interface Definition {

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
    public interface Listener<T> {

        /**
         * Called when a new token is available.
         *
         * @param definition The definition of the new token.
         * @param text The text of the new token.
         */
        void onToken(T definition, String text);

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
