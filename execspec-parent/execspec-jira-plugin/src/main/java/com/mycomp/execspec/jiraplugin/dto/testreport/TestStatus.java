package com.mycomp.execspec.jiraplugin.dto.testreport;

/**
 * Created by Dmytro on 4/7/2014.
 */
public enum TestStatus {

    PASSED,
    FAILED,
    PENDING,
    NOT_PERFORMED,
    /*not sure if need this one - but some steps can be ignored in story reporter*/
    IGNORED
}
