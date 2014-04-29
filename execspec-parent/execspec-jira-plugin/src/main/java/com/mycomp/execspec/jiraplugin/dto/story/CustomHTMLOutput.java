package com.mycomp.execspec.jiraplugin.dto.story;

import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.reporters.HtmlOutput;

import java.io.PrintStream;
import java.util.Properties;

/**
 * Created by Dmytro on 4/29/2014.
 */
public class CustomHTMLOutput extends HtmlOutput {

    public CustomHTMLOutput(PrintStream output) {
        super(output);
    }

    public CustomHTMLOutput(PrintStream output, Properties outputPatterns) {
        super(output, outputPatterns);
    }

    public CustomHTMLOutput(PrintStream output, Keywords keywords) {
        super(output, keywords);
    }

    public CustomHTMLOutput(PrintStream output, Properties outputPatterns, Keywords keywords) {
        super(output, outputPatterns, keywords);
    }

    public CustomHTMLOutput(PrintStream output, Properties outputPatterns, Keywords keywords, boolean reportFailureTrace) {
        super(output, outputPatterns, keywords, reportFailureTrace);
    }

    public CustomHTMLOutput(PrintStream output, Properties outputPatterns, Keywords keywords, boolean reportFailureTrace, boolean compressFailureTrace) {
        super(output, outputPatterns, keywords, reportFailureTrace, compressFailureTrace);
    }

    public String escapeHTML(String args) {
        Object[] escaped = super.escape(Format.HTML, args);
        return (String) escaped[0];
    }
}
