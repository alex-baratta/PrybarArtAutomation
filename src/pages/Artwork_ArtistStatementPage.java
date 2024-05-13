package pages;

import java.lang.ModuleLayer.Controller;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

import controller.Base;
import controller.CoreFunctions;
import cucumber.CustomReportListener;
import managers.FileReaderManager;
import managers.Log;

public class Artwork_ArtistStatementPage extends Base {

	public Artwork_ArtistStatementPage(WebDriver driver){
		super(driver);
	}
	
	@FindBy(how = How.CSS, using =".credits")
	private WebElement _creditName;
	
	@FindBy(how = How.CSS, using = ".post-title")
	private WebElement _postTitle;
	
	@FindBy(how = How.CSS, using=".main-menu")
	private WebElement _HomeHeader;
	
	@FindBy(how = How.XPATH, using="//blockquote[@class='wp-block-quote is-layout-flow wp-block-quote-is-layout-flow']")
	private WebElement _BlockQuote;
	
	
	public void lookForPageTitle() {
    	Log.info("attempting to look for page title");
		CoreFunctions.waitForBrowserToLoad(driver);
		CoreFunctions.explicitWaitTillElementVisibility(driver, _postTitle, "Artist Statement", 60);
	}
	
	public void LookForGeorgeAWalkerQuote() {
    	Log.info("attempting to look for George A Walker attribution");
    	CoreFunctions.waitForBrowserToLoad(driver);
		String textFound = CoreFunctions.explicitWaitTillElementVisibleGetText(driver, _BlockQuote, 60);
		CoreFunctions.actualStringContainsExpectedString("GEORGE A WALKER",textFound);
		
	}
}
