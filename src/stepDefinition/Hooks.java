package stepDefinition;

import java.sql.Timestamp;
import java.util.Date;

import cucumber.CustomReportListener;
import cucumber.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import managers.FileReaderManager;

import org.openqa.selenium.WebDriver;


public class Hooks {
	TestContext testContext;
	public static int testResult;
	public static Scenario scenarioName;
	public static int passedCount = 0, failCount = 0, skippedCount = 0;
	public static Timestamp startTimestamp;
	public WebDriver driver;
	
	public Hooks(TestContext context) {
		testContext = context;
	}
	
	
	@Before
	public void BeforeSteps(Scenario scenario) {
		startTimestamp = new Timestamp(new Date().getTime());
		scenarioName=scenario;
		testContext.initializeWebManager();
		driver = testContext.getWebDriverManager().getDriver();
		CustomReportListener.webDriver = driver;
		driver.navigate().to(FileReaderManager.getInstance().getConfigReader().getHomePageUrl());
	}
	
	@After
	public void CloseScenario(Scenario scenario) {
		testContext.getWebDriverManager().closeDriver();
		CustomReportListener.webDriver = null;
		Runtime.getRuntime().gc();		
	}	
}
