package com.mycomp.execspec;

import org.jbehave.core.annotations.Then;
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

    @Then("the scenario should fail")
    public void failScenario() {
        throw new RuntimeException("this is a step that always fails");
    }

}
