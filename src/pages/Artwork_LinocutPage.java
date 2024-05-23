package pages;
import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import controller.Base;
import controller.CoreFunctions;
import dataProviders.GalleryData;
import managers.FileReaderManager;
import managers.Log;

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
	
	@FindBy(how = How.CSS, using=".fg-media-content")
	private WebElement _GalleryLargeView;
	
	@FindBy(how = How.CLASS_NAME, using="fg-loader")
	private WebElement _galleryLoadSpinner;
	
	
	int galleryImageID =0;
	int gallerySize =20;
	int movedGalleryImageID =0;
	
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
		CoreFunctions.waitForBrowserToLoad(driver);		
		Random r = new Random();
		galleryImageID = r.nextInt(gallerySize-1) + 1;		
		GalleryData.Details galleryImage = FileReaderManager.getInstance()
				.getJsonReader().getLinocutGalleryImageByImageNumber(Integer.toString(galleryImageID)).details;		
		Log.info("galleryImage.cssselector= "+ galleryImage.cssselector);
		WebElement randomImage = driver.findElement(By.cssSelector(galleryImage.cssselector));
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		js.executeScript("arguments[0].scrollIntoView();", randomImage );
		CoreFunctions.explicitWaitTillElementBecomesClickable(driver, randomImage, galleryImage.title);
		CoreFunctions.click(driver, randomImage,"Random Gallery Image "+ galleryImage.title );		
	}

	public void selectGalleryDirectionKey(String direction) {
		CoreFunctions.explicitWaitTillElementVisibility(driver,_GalleryLargeView,"Gallery Image in Large view",60);
		if (direction.equals("Left")){		
			CoreFunctions.click(driver,_GalleryPreviousImage,"Left");
			movedGalleryImageID = galleryImageID -1;
		}
		else if(direction.equals("Right")) {
			CoreFunctions.click(driver,_GalleryNextImage,"Right");
			movedGalleryImageID = galleryImageID +1;
		}
		else {
		}
		if (movedGalleryImageID == gallerySize+1) {
			movedGalleryImageID = 1;
			}
		else if (movedGalleryImageID == 0) {
			movedGalleryImageID = gallerySize;
			}
	}

	public void checkLinocutGalleryImageIsCorrectPostDirection(String direction) {
		
		GalleryData.Details movedGalleryImage = FileReaderManager.getInstance()
				.getJsonReader().getLinocutGalleryImageByImageNumber(Integer.toString(movedGalleryImageID)).details;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		CoreFunctions.explicitWaitTillElementVisibility(driver,_GalleryLargeView,direction +" Gallery Image in Large view",60);
		String largeViewImageSrc =_GalleryLargeView.getAttribute("src"); 
		CoreFunctions.actualStringEqualsExpectedString(movedGalleryImage.url, largeViewImageSrc);		
	}

}
