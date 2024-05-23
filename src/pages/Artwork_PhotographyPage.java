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
	
	@FindBy(how = How.CSS, using="main[id='site-content'] p:nth-child(1)")
	private WebElement _gearList;
	
	@FindBy(how = How.ID, using="foogallery-gallery-308")
	private WebElement _galleryObject;
	
	
	@FindBy (how = How.CLASS_NAME, using="fg-caption")
	private WebElement _imageCaptions;

	public void lookForPageTitle() {
    	Log.info("attempting to look for page title");
		CoreFunctions.waitForBrowserToLoad(driver);
		CoreFunctions.explicitWaitTillElementVisibility(driver, _postTitle, "Photography Work", 60);
	}
	
	public void lookForPhotographyGear() {
		CoreFunctions.actualStringContainsExpectedString("Fujifilm X-T3", CoreFunctions.explicitWaitTillElementVisibleGetText(driver, _gearList, 10));
		
	}
	
	public void lookForGalleryObject() {
		CoreFunctions.explicitWaitTillElementVisibility(driver, _galleryObject, "Photography gallery", 10);
	}
	
	public void lookForCaptionObject() {
		CoreFunctions.explicitWaitTillElementVisibility(driver, _imageCaptions, "Captions on images", 10);

	}
	
}
