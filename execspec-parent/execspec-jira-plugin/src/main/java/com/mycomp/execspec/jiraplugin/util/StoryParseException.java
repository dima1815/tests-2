package com.mycomp.execspec.jiraplugin.util;

/**
 * Created by Dmytro on 5/30/2014.
 */
public class StoryParseException extends RuntimeException {

    public StoryParseException(String message) {
        super(message);
    }

    public StoryParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
