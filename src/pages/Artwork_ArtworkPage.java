package pages;
import java.lang.ModuleLayer.Controller;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

import controller.Base;
import controller.CoreFunctions;
import dataProviders.UrlData;
import managers.FileReaderManager;
import managers.Log;

public class Artwork_ArtworkPage extends Base{

		public Artwork_ArtworkPage(WebDriver driver) {
			super(driver);
		}
		
		@FindBy(how = How.CSS, using =".credits")
		private WebElement _creditName;
		
		@FindBy(how = How.CSS, using = ".post-title")
		private WebElement _postTitle;
		
		@FindBy(how = How.CSS, using=".main-menu")
		private WebElement _homeHeader;
		
		@FindBy(how = How.LINK_TEXT, using="Linocut")
		private WebElement _linocutLink;
		
		@FindBy(how = How.LINK_TEXT, using="Photography work")
		private WebElement _photographyLink;
		
		@FindBy(how = How.PARTIAL_LINK_TEXT, using="Uke and Tuba")
		private WebElement _ukeAndTubaLink;
		
		
		public void lookForPageTitle() {
	    	Log.info("attempting to look for page title");
			CoreFunctions.waitForBrowserToLoad(driver);
			CoreFunctions.explicitWaitTillElementVisibility(driver, _postTitle, "ARTWORK", 60);
		}
		
		public void selectLinocutLink() {
			CoreFunctions.click(driver, _linocutLink, "Linocut Link");
		}
		
		public void selectPhotographyLink() {
			CoreFunctions.click(driver, _photographyLink, "Photography Link");
		}
		
		public void selectUkeAndTubaLink() {
			CoreFunctions.click(driver, _ukeAndTubaLink, "Uke and Tuba Discography Link");	
		}
		
		public HashMap recordArtworkLinksfunctionality() {
			HashMap<String, String> results = new HashMap<String, String>();
			String initialUrl = CoreFunctions.currentUrlAsString(driver);
			results.put("Initial Url", 	initialUrl);
			CoreFunctions.click(driver, _linocutLink, "Linocut Link");
			results.put("Linocut selection", CoreFunctions.currentUrlAsString(driver));
			CoreFunctions.goBackToPreviousPage(driver, initialUrl);
			CoreFunctions.click(driver, _photographyLink, "Photography Link");
			results.put("Photography selection", CoreFunctions.currentUrlAsString(driver));
			CoreFunctions.goBackToPreviousPage(driver, initialUrl);
			CoreFunctions.click(driver, _ukeAndTubaLink, "Uke and Tuba Link");
			results.put("Uke and Tuba selection", CoreFunctions.currentUrlAsString(driver));
			Log.info("Results hashmap: " + results.toString());
			return results;
		}

		public void verifyArtworkLinkResults(HashMap<String, String> results) {			
			boolean validation = true;
			UrlData urls = FileReaderManager.getInstance().getJsonReader().getUrls();
			for (Map.Entry<String, String> entry : results.entrySet()){
				String key = entry.getKey();
				String value = entry.getValue();
				switch(key) {
					case "Initial Url":
							if (!value.equalsIgnoreCase(urls.artworkArtworkUrl)){
								validation = false;
								Log.info(" moving validation to false");
								}
							else {
								Log.info(" Actual: " + value + "  Expected: " + urls.artworkArtworkUrl + "  Match  for Key:" +key );
							}
							break;
					case "Linocut selection":
						if (!value.equalsIgnoreCase(urls.linocutArtworkUrl)){
							validation = false;
							Log.info(" moving validation to false");
							}
						else {
							Log.info(" Actual: " + value + "  Expected: " + urls.linocutArtworkUrl + "  Match  for Key:" +key );
						}
							break;
					case "Photography selection":
						if (!value.equalsIgnoreCase(urls.photographyArtworkUrl)){
							validation = false;
							Log.info(" moving validation to false");
							}
						else {
							Log.info(" Actual: " + value + "  Expected: " + urls.photographyArtworkUrl + "  Match  for Key:" +key );
						}
							break;
					case "Uke and Tuba selection":
						if (!value.equalsIgnoreCase(urls.ukeAndTubaArtworkUrl)){
							validation = false;
							Log.info(" moving validation to false");
							}
					else {
							Log.info(" Actual: " + value + "  Expected: " + urls.ukeAndTubaArtworkUrl + "  Match  for Key:" +key );
						}
							break;
					default:
						Log.info("Key text: " + key  + " not found");
						validation = false;
						Log.info(" moving validation to false");
							break;			
					}
			
				}
				
				Assert.assertTrue(validation);

			}
}
