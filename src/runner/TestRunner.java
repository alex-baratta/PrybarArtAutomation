/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package runner;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = "src/features/", glue = {"stepDefinition"}
, tags = ("${cucumber.filter.tags}"), plugin = {"pretty", "cucumber.CustomReportListener", 
          "testng:target/cucumber-results.xml", "html:target/cucumber-results.html",
          "json:target/cumber-results.json"}, monochrome = true)



public class TestRunner extends AbstractTestNGCucumberTests {
    
}
