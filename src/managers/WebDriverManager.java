/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import cucumber.CustomReportListener;


public class WebDriverManager {
    private WebDriver driver;
    private static String browser;
    String downloadFilepath = System.getProperty("user.home") + "/Downloads/";
    
    public WebDriverManager() {
        browser = System.getProperty("browser").toLowerCase();
  
    }
    
    public WebDriver getDriver(){
    	Log.info("Getting Web Driver");
        if (driver==null)
         try {   driver = createDriver();
         } catch (Exception e) {
        	 Log.info("DRIVER ERROR: " + e.getMessage());
         }
        CustomReportListener.webDriver = driver;
        return driver;
    }
       
    private WebDriver createDriver(){
    	Log.info("Creating Local Driver with browser: "+ browser);
        switch (browser){
            case "chrome":
                startChromeBrowser();
                break;
            case "edge":
                startEdgeBrowser();
                break;
            case "firefox":
                startFirefoxBrowser();
                break;
            case "ie":
                startInternetExplorerBrowser();
                break;
        }
        
        if (FileReaderManager.getInstance().getConfigReader().getBrowserWindowSize())
            driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(
                   Duration.ofSeconds(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait()));
       
        return driver;
    }
    
    public void startChromeBrowser(){
    	Log.info("Invoking Chrome Browser");
        HashMap<String, Object> chromePrefs = new HashMap<String,Object>();
        chromePrefs.put("Profile.default_content_settings.popups", 0);
        downloadFilepath= downloadFilepath.replace("/","\\");
        chromePrefs.put("download.default_directory", downloadFilepath);
        ChromeOptions chromeOption= new ChromeOptions();
        chromeOption.setExperimentalOption("useAutomationExtension",false);
        chromeOption.setPageLoadStrategy(PageLoadStrategy.NONE);
        chromeOption.addArguments("--remote-allow-origins=*");
        chromeOption.addArguments("start-maximized");
        chromeOption.addArguments("enable-automation");
        chromeOption.addArguments("--no-sandbox");
        chromeOption.addArguments("--disable-infobars");
        chromeOption.addArguments("--disable-dev-shm-usage");
        chromeOption.addArguments("--disable-gpu");
        chromeOption.addArguments("--ignore-certificate-errors");
        chromeOption.setBrowserVersion("124");
        chromeOption.addArguments("--incognito");
        chromeOption.setExperimentalOption("prefs", chromePrefs);
        driver = new ChromeDriver(chromeOption);
    }
    
    public void startEdgeBrowser() {
    	Log.info("Invoking Edge Browser");
        EdgeOptions options = new EdgeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        Map<String, Object> edgePerfs = new HashMap<String, Object>();
        edgePerfs.put("download.default_directory", downloadFilepath);
        options.setExperimentalOption("prefs", edgePerfs);
        options.addArguments("--remote-allow-origins=*");
        driver=new EdgeDriver(options);
    }
    
    public void startFirefoxBrowser(){
    	Log.info("Invoking Firefox Browser");
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("browser.download.folderList", 2);
        downloadFilepath= downloadFilepath.replace("/","\\");
        firefoxProfile.setPreference("browser.download.dir", downloadFilepath);
        firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/jpg");
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(firefoxProfile);
        options.setBinary( "C:/Program Files/Mozilla Firefox/firefox.exe");
        driver = new FirefoxDriver(options);
    }
    
    public void startInternetExplorerBrowser(){
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,true);
        InternetExplorerOptions options = new InternetExplorerOptions(cap);
        driver = new InternetExplorerDriver(options);
    }
    
    public void closeDriver(){
        if (driver!=null){
            driver.quit();
            driver=null;
        }
    
    }
    
    
    
}
