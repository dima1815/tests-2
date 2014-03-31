package com.mycomp.execspec;

import org.jbehave.core.annotations.When;

import java.util.List;

/**
 * Created by Dmytro on 2/15/14.
 */
public class MyStepsMain2 {


    @When("I send the following request:$requests")
    public void sendRequests(List<MyCustomParameter> trades) {
        System.out.println("-> sendRequests");
    }

}
