package managers;

import org.openqa.selenium.WebDriver;

import pages.Artwork_ArtistStatementPage;
import pages.Artwork_ArtworkPage;
import pages.Artwork_LinocutPage;
import pages.Artwork_PhotographyPage;
import pages.Artwork_UkeAndTubaPage;
import pages.HomePage;

public class PageObjectManager {
	private WebDriver _driver;
	private Artwork_ArtworkPage artworkPage;
	private Artwork_LinocutPage linocutPage;
	private Artwork_ArtistStatementPage statementPage;
	private Artwork_PhotographyPage photographyPage;
	private Artwork_UkeAndTubaPage ukeAndTubaPage;
	private HomePage homePage;
	
	
	
	public PageObjectManager(WebDriver driver) {
		this._driver = driver;
	}
	
	public HomePage getHomePage() {
		return (homePage == null) ? homePage = new HomePage(_driver) : homePage;
	}
	
	public Artwork_ArtworkPage getArtworkPage() {
		return (artworkPage == null) ? artworkPage = new Artwork_ArtworkPage(_driver) : artworkPage;
	}
	
	public Artwork_LinocutPage getLinocutPage() {
		return (linocutPage == null) ? linocutPage = new Artwork_LinocutPage(_driver) : linocutPage;
	}
	
	public Artwork_ArtistStatementPage getArtistStatementPage() {
		return (statementPage == null) ? statementPage = new Artwork_ArtistStatementPage(_driver) : statementPage;
	}
	
	public Artwork_PhotographyPage getPhotographyPage() {
		return (photographyPage == null) ? photographyPage = new Artwork_PhotographyPage(_driver) : photographyPage;
	}
	
	public Artwork_UkeAndTubaPage getUkeAndTubaPage() {
		return (ukeAndTubaPage == null) ? ukeAndTubaPage = new Artwork_UkeAndTubaPage(_driver) : ukeAndTubaPage;
	}
}
