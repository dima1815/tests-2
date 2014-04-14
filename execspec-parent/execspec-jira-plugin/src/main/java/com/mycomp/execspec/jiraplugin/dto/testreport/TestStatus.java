package com.mycomp.execspec.jiraplugin.dto.testreport;

/**
 * Created by Dmytro on 4/7/2014.
 */
public enum TestStatus {

    PASSED("Passed"),
    FAILED("Failed"),
    PENDING("Pending"),
    NOT_PERFORMED("Not Performed"),
    /*not sure if need this one - but some steps can be ignored in story reporter*/
    IGNORED("Ignored");

    public final String guiName;

    TestStatus(String guiName) {
        this.guiName = guiName;
    }
}
