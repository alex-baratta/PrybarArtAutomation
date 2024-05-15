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

public class Artwork_LinocutPage extends Base {

	public Artwork_LinocutPage(WebDriver driver){
		super(driver);
	}
	
	@FindBy(how = How.CSS, using =".credits")
	private WebElement _creditName;
	
	@FindBy(how = How.CSS, using = ".post-title")
	private WebElement _postTitle;
	
	@FindBy(how = How.CSS, using=".main-menu")
	private WebElement _HomeHeader;
	
	@FindBy(how = How.CSS, using="div[class='post-content entry-content'] p strong")
	private WebElement _MaterialsListHeader;
	
	@FindBy(how = How.CSS, using="div[class='post-content entry-content'] ul")
	private WebElement _MaterialsListList;
	
	@FindBy(how = How.CSS, using="#foogallery-gallery-247")
	private WebElement _LinocutGallery;
	
	@FindBy(how = How.CSS, using="button[aria-label='Next Media']")
	private WebElement _GalleryNextImage;
	
	@FindBy(how = How.CSS, using="button[aria-label='Previous Media']")
	private WebElement _GalleryPreviousImage;

	public void lookForPageTitle() {
    	Log.info("attempting to look for page title");
		CoreFunctions.waitForBrowserToLoad(driver);
		CoreFunctions.explicitWaitTillElementVisibility(driver, _postTitle, "Linocut", 60);
	}

	public void lookForMaterialsList() {
		CoreFunctions.waitForBrowserToLoad(driver);
		lookForPageTitle();
		CoreFunctions.explicitWaitTillElementVisibility(driver, _MaterialsListHeader, "materials list:", 60);
		String textFound = CoreFunctions.explicitWaitTillElementVisibleGetText(driver, _MaterialsListList, 60);
		CoreFunctions.actualStringContainsExpectedString("Flexcut",textFound);
	}
	
	public void lookForGalleryObject() {
		CoreFunctions.explicitWaitTillElementVisibility(driver, _LinocutGallery, null, 60);
	}

	public void selectAnImageFromTheGalleryOfImages() {
		WebElement randomImage = driver.findElement(By.xpath(("//img[@alt='Goofy Skeleton']")));
		CoreFunctions.click(driver, randomImage, null);
	}

	public void selectGalleryDirectionKey(String direction) {
		CoreFunctions.waitForBrowserToLoad(driver);
		Log.info("Waiting for " +direction + " directopn key to display to display...");
		if (direction.equals("Left")){
			CoreFunctions.click(driver,_GalleryPreviousImage,null);
		}
		else if(direction.equals("Right")) {
			CoreFunctions.click(driver,_GalleryNextImage,null);
		}
		else {
			
		}
		
		
	}

	
	
}
