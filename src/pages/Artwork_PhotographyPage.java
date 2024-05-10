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
import managers.FileReaderManager;
import managers.Log;
import controller.Base;

public class Artwork_PhotographyPage extends Base {

	public Artwork_PhotographyPage(WebDriver driver){
		super(driver);
	}

	@FindBy(how = How.CSS, using =".credits")
	private WebElement _creditName;
	
	@FindBy(how = How.CSS, using = ".post-title")
	private WebElement _postTitle;
	
	@FindBy(how = How.CSS, using=".main-menu")
	private WebElement _HomeHeader;

	public void lookForPageTitle() {
    	Log.info("attempting to look for page title");
		CoreFunctions.waitForBrowserToLoad(driver);
		CoreFunctions.explicitWaitTillElementVisibility(driver, _postTitle, "Photography Work", 60);
	}
}
