package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import cucumber.CustomReportListener;
import managers.Log;


	public class CoreFunctions {
		public static String timeStamp = new SimpleDateFormat("MM.dd.yyyy.HH.mm.ss").format(new Date());

		public static void waitForBrowserToLoad(WebDriver driver) {
			try {
				boolean isReady = checkBrowserReadyState(driver);
				if (!isReady) {
					waitForBrowserToLoad(driver);
					}
				
			} catch (Exception e) {
				Log.info("ERROR : "+ e.getMessage());
			}		
	}
		
	private static boolean checkBrowserReadyState(WebDriver driver) {
		boolean ready = false;
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			if (js.executeScript("return document.readyState").toString().equals("complete")){
				ready = true;
			}
		} catch (Exception e) {
			Log.info("ERROR : "+ e.getMessage());
		}
		return ready;
	}
	
	public static void writeToPropertiesFile(String key, String data) {
		FileOutputStream fileOut = null;
		FileInputStream fileIn = null;
		try {
			Properties configProperty = new Properties();
			File file = new File(System.getProperty("user.dir") + "/Configs/Config.properties");
			fileIn = new FileInputStream(file);
			configProperty.load(fileIn);
			configProperty.setProperty(key, data);
			fileOut = new FileOutputStream(file);
			configProperty.store(fileOut, "Data Saved to Property file");
		}catch (Exception e) {
				Assert.fail("ERROR : "+e.getMessage());
		}finally {
			try {
				if (fileOut!=null)
					fileOut.close();
				} catch (IOException e) {
					Assert.fail("ERROR : "+e.getMessage());
				}
		}
			
	}
	

	public static String getBrowser() {
		String _browser = (System.getProperty("browser") !=null) ? System.getProperty("browser").toLowerCase()
				: CoreFunctions.getPropertyFromConfig("browser").toLowerCase();
		return _browser;
	}
	
	private static String getPropertyFromConfig(String property) {
		FileInputStream fi;
		String value = "";
		try {
			fi = new FileInputStream(System.getProperty("user.dir")+"\\Configs\\Config.properties" );
			Properties prop = new Properties();
			prop.load(fi);
			value = prop.getProperty(property);
		} catch (Exception e) {
			Assert.fail("ERROR : "+ e.getMessage());
		}
		return value;
	}
	
	public static void hover(WebDriver driver, WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
		
	}
	
	public static void explicitWaitTillElementBecomesClickable(WebDriver driver, WebElement element, String name) {
		try {
			Log.info("Waiting for " +name + " to display...");
			waitTillElementClickable(driver, element, 60);
			Log.info("SUCCESS : " +name + " is displayed");
			
		} catch (Exception e) {
			Assert.fail("Failed to click element : ", e); 
		}
		
	}
	
	private static void waitTillElementClickable(WebDriver driver, WebElement element, long time) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			}catch (StaleElementReferenceException e) {
				refreshAndWaitForElementToBeClickableOnStale(driver, element);
			} catch (Exception e) {
				Assert.fail("ERROR : "+ e.getMessage());
			}
	}
	
	public static void explicitWaitTillElementVisibility(WebDriver driver, WebElement element, String name, long time) {
			try {
				Log.info("Waiting for " +name + " to display...");
				WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(time));
				wait.until(ExpectedConditions.visibilityOf(element));
				Log.info("SUCCESS : " + name +" is displayed");
				CustomReportListener.addStepLog("PASS","   "+name+" element found.");
			}catch (StaleElementReferenceException e) {
				refreshAndWaitForElementVisibilityOnStale(driver, element);
			}
	}
	
	private static void refreshAndWaitForElementVisibilityOnStale(WebDriver driver, WebElement element) {
		Log.info("waiting for Refresh...");
		WebDriverWait wait = new WebDriverWait (driver,Duration.ofSeconds(60));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.invisibilityOf(element)));	
	}	

	private static void refreshAndWaitForElementToBeClickableOnStale(WebDriver driver, WebElement element) {
		Log.info("waiting for Refresh...");
		WebDriverWait wait = new WebDriverWait (driver,Duration.ofSeconds(60));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));			
	}

	public static String explicitWaitTillElementVisibleGetText(WebDriver driver, WebElement element, long time) {
		String foundText = "";
		try {
			Log.info("looking for web element="+element+"  to get text");
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(time));
			wait.until(ExpectedConditions.visibilityOf(element));
			foundText = element.getText();
			CustomReportListener.addStepLog("PASS","   "+ element +" element found.");
			Log.info("INFO : "+ foundText + "     Text has been found" );
		}catch (StaleElementReferenceException e) {
		refreshAndWaitForElementVisibilityOnStale(driver, element);
		}
		return foundText;
	}
	
	public static void explicitWaitForInvisibilityOfElement(WebDriver driver, WebElement element, long time) {
		try {
			Log.info("waiting for element: "+ " to disappear");
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(time));
			wait.until(ExpectedConditions.invisibilityOf(element));
			CustomReportListener.addStepLog("PASS","   "+ element +" element no longer found.");
		}catch(TimeoutException e){
            Assert.fail("Time Out On : "+element);
        }
	}
	
	public static void click(WebDriver driver, WebElement element, String name) {
		explicitWaitTillElementBecomesClickable(driver,element,name);
		Log.info("Clicking on "+ name);
		try {
			element.click();
			CustomReportListener.addStepLog("PASS", " :  Verified "+name+" Element Clicked ");
		} catch(StaleElementReferenceException e) {
			refreshAndWaitForElementVisibilityOnStale(driver, element);
			element.click();
			CustomReportListener.addStepLog("PASS", " :  Verified "+name+" Element Clicked ");
		} catch (Exception e) {
			Assert.fail("failed to click element: "+ name+ "  "+e );
		}
		
	}
	
	public static void actualStringContainsExpectedString(String expected, String actual ) {
		boolean match=false;
		if (actual.contains(expected)) {
			match = true;
			CustomReportListener.addStepLog("PASS", " :  Verified Expected String ="+ expected + "   is contained in the Actual String=" +actual);  
		}
		else {
			CustomReportListener.addStepLog("FAIL", " :  Expected String =" +expected +" is not contained in the actual String="+ actual);
			}
		Assert.assertTrue(match);
	}
	
	public static void actualStringEqualsExpectedString(String expected, String actual ) {
		boolean match=false;
		if (actual.equals(expected)) {
			match = true;
			CustomReportListener.addStepLog("PASS", " :  Verified Expected String ="+ expected + "  matches the Actual String=" +actual);  
		}
		else {
			CustomReportListener.addStepLog("FAIL", " :  Expected String =" +expected +" does NOT match the Actual String="+ actual);
			}
		Assert.assertTrue(match);
	}
	
	
}
	

