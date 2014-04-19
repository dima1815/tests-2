package com.mycomp.execspec.jiraplugin.customfields;

import com.mycomp.execspec.jiraplugin.dto.testreport.TestStatus;

import java.util.*;

/**
 * Created by Dmytro on 4/18/2014.
 */
public class EnvironmentTestStatuses {

    private Map<String, TestStatus> statusesByEnvironment;

    public EnvironmentTestStatuses(Map<String, TestStatus> statusesByEnvironment) {
        this.statusesByEnvironment = statusesByEnvironment;
    }

    public List<String> getEnvironments() {
        Set<String> keys = statusesByEnvironment.keySet();
        List<String> environments = new ArrayList<String>(keys);
        Collections.sort(environments);
        return environments;
    }

    public TestStatus getStatus(String environment) {

        TestStatus testStatus = statusesByEnvironment.get(environment);
        return testStatus;
    }

}
