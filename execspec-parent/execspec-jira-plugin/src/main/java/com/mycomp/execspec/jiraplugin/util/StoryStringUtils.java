package com.mycomp.execspec.jiraplugin.util;

/**
 * Created by Dmytro on 5/19/2014.
 */
public class StoryStringUtils {

    private StoryStringUtils() {
    }

    public static boolean isFirstStoryLine(String input) {
        String[] lines = input.split("\\n");
        if (lines.length == 1) {
            return true;
        } else {
            boolean isFirst = true;
            for (int i = 0; i < lines.length - 1; i++) {
                String line = lines[i];
                if (!line.startsWith("!--") && !line.isEmpty()) {
                    isFirst = false;
                    break;
                }
            }
            return isFirst;
        }
    }

    public static boolean previousLineStartsWith(String asdf) {
        return false;
    }
}
