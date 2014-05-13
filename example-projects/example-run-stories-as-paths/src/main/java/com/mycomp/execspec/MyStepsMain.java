package com.mycomp.execspec;

import org.hamcrest.Matchers;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.model.OutcomesTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dmytro on 2/15/14.
 */
public class MyStepsMain {

    @Given("something")
    public void someStep() {
        System.out.println("throwing exception from 'Given something'");
    }

    @Alias("some step with a parameter $myParam that is named but uses an alias")
    @Given("some step with a parameter $myParam that is named")
    public void someStepWithNamedParam(@Named("myParam") String myParam) {
        System.out.println("throwing exception from 'Given something'");
    }

    @Given("test parameter value - $paramValue units")
    public void testing(String paramValue) {
        System.out.println("-> something");
//        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Then("something should happen")
    public void verify() {
        System.out.println("-> verify");
//        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Given("something else:$trades")
    public void someOtherStep(ExamplesTable trades) {

//        List<Parameters> parameters = trades.getRowsAsParameters();

//        for (Parameters parameter : parameters) {
//
//            String value = parameter.valueAs("currency", String.class);
//
//        }

        List<Map<String, String>> actuals = new ArrayList<Map<String, String>>();
        Map<String, String> actual = new HashMap<String, String>();
        actual.put("action", "Buy");
        actual.put("value", "100");
        actual.put("currency", "USD");
        actuals.add(actual);
        Map<String, String> actual2 = new HashMap<String, String>();
        actual2.put("action", "Sell");
        actual2.put("value", "40");
        actual2.put("currency", "UAH");
        actuals.add(actual2);

        OutcomesTable outcomes = new OutcomesTable();
        List<Map<String, String>> rows = trades.getRows();
        for (int i = 0; i < rows.size(); i++) {
            Map<String, String> row = rows.get(i);
            Map<String, String> result = actuals.get(i);
            for (String key : row.keySet()) {
                outcomes.addOutcome(key, result.get(key), Matchers.equalTo(row.get(key)));
            }
        }
        outcomes.verify();

        System.out.println("in something");
    }

}
