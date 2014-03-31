package com.mycomp.execspec;

import org.jbehave.core.annotations.Given;

/**
 * Created by Dmytro on 2/15/14.
 */
public class MySteps {

    @Given("something")
    public void someStep() {
        System.out.println("in something");
    }

}
