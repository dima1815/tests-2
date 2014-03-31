package com.mycomp.execspec;

import org.jbehave.core.reporters.StepdocReporter;
import org.jbehave.core.steps.Stepdoc;

import java.util.List;

/**
 * Created by Dmytro on 2/26/14.
 */
public class JiraStepDocReporter implements StepdocReporter {


    @Override
    public void stepdocs(List<Stepdoc> stepdocs, List<Object> stepsInstances) {
        System.out.println("stepdocs, stepdocs - " + stepdocs + ", stepsInstances - " + stepsInstances);
    }

    @Override
    public void stepdocsMatching(String stepAsString, List<Stepdoc> matching, List<Object> stepsIntances) {
        System.out.println("stepdocsMatching, stepAsString - " + stepAsString + ", matching - " + matching
                + ", stepsIntances - " + stepsIntances);
    }
}
