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

public class HomePage extends Base{

	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(how = How.CSS, using =".credits")
	private WebElement _creditName;
	
	@FindBy(how = How.CSS, using = ".post-title")
	private WebElement _postTitle;
	
	@FindBy(how = How.CSS, using=".main-menu")
	private WebElement _HomeHeader;
	
	@FindBy(how= How.CLASS_NAME, using="blog-description")
	private WebElement _HomeDescription;
	
	@FindBy (how=How.XPATH, using ="//ul[@class='main-menu']//a[normalize-space()='ARTWORK']")
	private WebElement _artworkDropDownArtwork;
	
	@FindBy (how=How.XPATH, using ="//ul[@class='main-menu']//a[contains(text(),'Artist Statement')]")
	private WebElement _artworkDropDownStatement;
	
	@FindBy (how=How.XPATH, using ="//ul[@class='main-menu']//a[contains(text(),'Linocut')]")
	private WebElement _artworkDropDownLinocut;
	
	@FindBy (how=How.XPATH, using ="//ul[@class='main-menu']//a[contains(text(),'Photography Work')]")
	private WebElement _artworkDropDownPhotography;
	
	@FindBy (how=How.XPATH, using ="//ul[@class='main-menu']//a[normalize-space()='Uke and Tuba Discography']")
	private WebElement _artworkDropDownUkeAndTuba;
	
	
	public void openHomePage() {
    	Log.info("attempting to Open Homepage");
		Log.info(FileReaderManager.getInstance().getConfigReader().getHomePageUrl());
		CoreFunctions.waitForBrowserToLoad(driver);
		CoreFunctions.explicitWaitTillElementVisibility(driver, _postTitle, "HELLO", 60);
	}
	
	public void expandArtworkDropDownOptionAndSelect() {
				
				CoreFunctions.hover(driver,_artworkDropDownArtwork);
	}
	
	public void selectArtworkDropDownOption(String dropdownOption) {
		
		switch(dropdownOption){
		case "ARTWORK": 	
			CoreFunctions.click(driver, _artworkDropDownArtwork, "ARTWORK");
			break;
		case "Artist Statement": 	
			CoreFunctions.click(driver, _artworkDropDownStatement, dropdownOption);
			break;
		case "Linocut": 	
			CoreFunctions.click(driver, _artworkDropDownLinocut, dropdownOption);
			break;
		case "Photography Work": 	
			CoreFunctions.click(driver, _artworkDropDownPhotography, dropdownOption);
			break;
		case "Uke and Tuba Discography": 	
			CoreFunctions.click(driver, _artworkDropDownUkeAndTuba, dropdownOption);
			break;
		default:
			Log.info("The dropdown option "+ dropdownOption + "is not present");	
		}
	}
}
