package com.vi.cucumber.TestRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)

@CucumberOptions(
		
		features = "Features",
		glue = {"TestCode"}
		
		)

public class TESTRunner {

}
