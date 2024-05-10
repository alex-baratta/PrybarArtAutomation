package managers;

import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.testng.Assert;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;


public class ExtentReportUtil {
		String fileName = System.getProperty("user.dir") + "extentreport.html";
		public ExtentReports extent;
		public static ExtentTest currentScenario;
		public static ExtentTest features;
		public static String reportLocation = System.getProperty("user.dir")+ "/TestReports";
		
		public ExtentReportUtil() {}
		
		public void ExtentReport() {
			extent = new ExtentReports();
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
			htmlReporter.config().setTheme(Theme.DARK);
			htmlReporter.config().setDocumentTitle("Prybar Test Report");
			htmlReporter.config().setEncoding("uft-8");
			htmlReporter.config().setReportName("Prybar Test Report");
			extent.attachReporter(htmlReporter);
		}
		
		public void ExtentReportScreenShot() {
			try {
				String screenshotPath = System.getProperty("user.dir")+"/screenshots+" + "/FAIL"+".png";
				File fileObj = new File(screenshotPath);
				Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
				BufferedImage capture = new Robot().createScreenCapture(screen);
				try {ImageIO.write(capture, "png", fileObj);
					}catch (IOException e){
						e.printStackTrace();
					}
				currentScenario.fail("details").addScreenCaptureFromPath(reportLocation + "screenshot.png");
				} catch (Exception e) {
					Assert.fail("ERROR : "+ e.getMessage());
				}
			
		}
		public void flushReport() {
			extent.flush();
		}
		
}
