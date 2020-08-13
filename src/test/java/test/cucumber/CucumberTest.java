package test.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(junit = "--step-notifications", tags = "not @ignore")
public class CucumberTest {

}
