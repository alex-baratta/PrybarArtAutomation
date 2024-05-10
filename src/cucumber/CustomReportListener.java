package cucumber;



import controller.CoreFunctions;
import managers.FileReaderManager;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.log4j.PropertyConfigurator;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.gherkin.model.Then;
import com.aventstack.extentreports.gherkin.model.When;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;

import io.cucumber.core.gherkin.DataTableArgument;
import io.cucumber.datatable.DataTable;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.Step;
import io.cucumber.plugin.event.StepArgument;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;


public class CustomReportListener implements ConcurrentEventListener {
	Map<String, ExtentTest> features = new HashMap<>();
	public ExtentSparkReporter spark;
	public ExtentReports extent;
	public static List<ExtentTest> scenarios = new ArrayList<ExtentTest>();
	public static ExtentTest step;
	public static ExtentTest scenario;
	public static String reportInfo = (System.getProperty("reportName") != null) ? System.getProperty("reportName"): "AiresAutomation";
	public static String REPORT_NAME = reportInfo.replaceAll(" ", "_") + "_Report_" + CoreFunctions.timeStamp;
	public static WebDriver webDriver;
	public static int scenariosCount_currentFeature = 0;
	public static int stepsCount_currentScenario = 0;
	public static String current_feature = "";
	public static String scenarioBeingExecuted = "";
	public static String stepBeingExecuted = "";
	public static Timestamp startTimeStamp;

	public static int passedCount = 0, failedCount = 0, skippedCount = 0;

	@Override
	public void setEventPublisher(EventPublisher publisher) {
		publisher.registerHandlerFor(TestRunStarted.class, this::runStarted);
		publisher.registerHandlerFor(TestRunFinished.class, this::runFinished);
		publisher.registerHandlerFor(TestCaseStarted.class, this::scenarioStarted);
		publisher.registerHandlerFor(TestCaseFinished.class, this::scenarioFinsihed);
		publisher.registerHandlerFor(TestStepStarted.class, this::stepStarted);
		publisher.registerHandlerFor(TestStepFinished.class, this::stepFinished);
	}

	private void runStarted(TestRunStarted event) {
		CoreFunctions.writeToPropertiesFile("ReportName", REPORT_NAME + ".html");
		PropertyConfigurator.configure(".//src//log4j.properties");
		spark = new ExtentSparkReporter("TestReports/" + REPORT_NAME + ".html");
		spark.config().setTheme(Theme.STANDARD);
		List<ViewName> DEFAULT_ORDER = Arrays.asList(ViewName.DASHBOARD, ViewName.CATEGORY, ViewName.TEST);
		spark.viewConfigurer().viewOrder().as(DEFAULT_ORDER);
		try {
			spark.loadXMLConfig(new File(FileReaderManager.getInstance().getConfigReader().getReportConfigPath()));
			} catch (IOException e) {
					e.getMessage();
			}
		extent = new ExtentReports();
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
		extent.setSystemInfo("Machine", System.getProperty("os.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		extent.setSystemInfo("Selenium Version", getDependencyVersions("selenium-java"));
		extent.setSystemInfo("Report version", getDependencyVersions("extentreports-cucumber7-adapter"));
		extent.setSystemInfo("Browser Name", CoreFunctions.getBrowser().toUpperCase());
		extent.attachReporter(spark);


	}

	private void runFinished(TestRunFinished event) {
		extent.flush();
	}

	private void scenarioStarted(TestCaseStarted event) {
		String featureName = event.getTestCase().getUri().toString();
		current_feature = featureName.split(".*/")[1];
		scenarioBeingExecuted = event.getTestCase().getName();
		if (features.get(featureName) == null) {
				features.putIfAbsent(featureName, extent.createTest(Feature.class, featureName.split(".*/")[1]));
				scenariosCount_currentFeature = 1;
		} else {
				scenariosCount_currentFeature++;
		}
		scenario = features.get(featureName).createNode(Scenario.class, event.getTestCase().getName());
	}

	private void stepStarted(TestStepStarted event) {
		if (event.getTestStep() instanceof PickleStepTestStep) {
			Step testStep = ((PickleStepTestStep) event.getTestStep()).getStep();
			stepBeingExecuted = testStep.getText();
			stepsCount_currentScenario++;
			step = getStepAsPerTheKeyword(scenario, testStep);
		}
	}

	private void scenarioFinsihed(TestCaseFinished event) {
		if (event.getResult().getStatus() == Status.FAILED)
			failedCount++;
		else if (event.getResult().getStatus() == Status.PASSED)
			passedCount++;
		else if (event.getResult().getStatus() == Status.SKIPPED)
			skippedCount++;
	}

	private void stepFinished(TestStepFinished event) {
		if (!(event.getTestStep() instanceof PickleStepTestStep))
			return;
		if (event.getResult().getStatus() == Status.FAILED) {
			step.fail("<div style=\"padding:15px 0px 5px 0px;font-family:Consolas;font-size:14px\"><font color=red><b><i>" 
					+ event.getResult().toString().split(",")[2].replace("}", "").replace("error","Failure Reason")+ "</i></b></font></div>");
			addScreenshot("Scenario terminated at below section");
		} else if (event.getResult().getStatus() == Status.SKIPPED) {
			step.skip("skipped");
		}
	}

	public String formatToDataTable(DataTable table) {
		String data = "";
		if (Objects.nonNull(table)) {
			int height = table.height();
			int width = table.width();
			Integer maxColumnWidth[] = new Integer[width];
			Arrays.fill(maxColumnWidth, 0);
			String[][] renderedCells = new String[height][width];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++)
					renderedCells[i][j] = table.cell(i, j).toString();
			}
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++)
					maxColumnWidth[i] = maxColumnWidth[i] < renderedCells[j][i].length() ? renderedCells[j][i].length(): maxColumnWidth[i];
			}
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					int appendWidth = maxColumnWidth[i] - renderedCells[j][i].length();
					for (int k = 0; k < appendWidth; k++)
					renderedCells[j][i] = renderedCells[j][i] + "&nbsp";
					}
				}
			data = "<br/>|";
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++)
					data = data + renderedCells[i][j] + "|";
					if (i < height - 1)
						data = data + "\n <br/>|";
					else
						data = data + "\n";
				}
		}
		return data;
	}

	private ExtentTest getStepAsPerTheKeyword(ExtentTest scenario, Step step) {
		StepArgument stepArgument = step.getArgument();
		DataTable table = null;
		if (DataTableArgument.class.isInstance(stepArgument)) {
			try {
				table = DataTable.create(((DataTableArgument) stepArgument).cells());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		switch (step.getKeyword().trim()) {
			case "Given":
				return scenario.createNode(Given.class,"<div style=\"padding:15px 0px 5px 0px;font-family:Consolas;font-size:14px\"><font color=black><b>Given    </b>"
						+ step.getText() + formatToDataTable(table) + "</font></div>");
			case "When":
				return scenario.createNode(When.class,"<div style=\"padding:15px 0px 5px 0px;font-family:Consolas;font-size:14px\"><font color=black><b>When    </b>"
						+ step.getText() + formatToDataTable(table) + "</font></div>");
			case "Then":
				return scenario.createNode(Then.class,"<div style=\"padding:15px 0px 5px 0px;font-family:Consolas;font-size:14px\"><font color=black><b>Then    </b>"
						+ step.getText() + formatToDataTable(table) + "</font></div>");
			case "And":
				return scenario.createNode(Given.class,"<div style=\"padding:15px 0px 5px 0px;font-family:Consolas;font-size:14px\"><font color=black><b>And    </b>"
						+ step.getText() + formatToDataTable(table) + "</font></div>");
		}
		return scenario.createNode(Given.class,"<div style=\"padding:15px 0px 5px 0px;font-family:Consolas;font-size:14px\"><font color=black><b>Given    </b>"
				+ step.getText() + "</font></div>");
	}

	public static void addStepLog(String status, String msg) {
		switch (status) {
			case "PASS":
				CustomReportListener.step.pass("<div style=\"font-family:Consolas;padding:5px 0 5px 30px;font-size:13px\"><font color=black>"
				+ status + msg + "</font></div>");
				break;
			case "FAIL":
					CustomReportListener.step.fail("<div style=\"font-family:Consolas;padding:5px 0 5px 30px;font-size:13px\"><font color=red><b>"
					+ status + msg + "</b></font></div>");
					addScreenshot("Please refer to screen shot attached below:");
				break;
			case "INFO":
					CustomReportListener.step.info("<div style=\"font-family:Consolas;padding:5px 0 5px 30px;font-size:13px\"><font color=red><b>"
					+ status + msg + "</b></font></div>");
					addScreenshot("Please refer to screen shot attached below:");
				break;
			default:
				Assert.fail("Please provide the correct status for logging the message");
		}

	}

	public static void addScreenshot(String msg) {
		step.log(com.aventstack.extentreports.Status.FAIL,
				"<div style=\"font-family:Consolas;padding:5px 0 5px 0px;font-size:13px\"><font color=blue><b>"	+ msg+ "</b></font></div>")
				.fail(MediaEntityBuilder.createScreenCaptureFromBase64String(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64)).build());
	}

	public String getDependencyVersions(String dependencyName) {
		String versionNo = null;
		try {
			Model model = new MavenXpp3Reader().read(new FileReader(new File(System.getProperty("user.dir") + "/pom.xml")));
			List<Dependency> dependencies = model.getDependencies();
			for (Dependency dependency : dependencies) {
				if (dependency.getArtifactId().equalsIgnoreCase(dependencyName))
				versionNo = dependency.getVersion();
			}
		} catch (Exception e) {
			Assert.fail("Please provide the correct pom file path");
		}
		return versionNo;
	}

}
