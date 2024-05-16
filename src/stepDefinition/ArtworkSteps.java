package stepDefinition;
import cucumber.TestContext;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import managers.Log;
import pages.Artwork_ArtistStatementPage;
import pages.Artwork_ArtworkPage;
import pages.Artwork_LinocutPage;
import pages.Artwork_PhotographyPage;
import pages.Artwork_UkeAndTubaPage;
import pages.HomePage;

public class ArtworkSteps {
	TestContext testContext;
	HomePage homePage;
	Artwork_ArtworkPage artworkPage;
	Artwork_ArtistStatementPage statementPage;
	Artwork_PhotographyPage photographyPage;
	Artwork_LinocutPage linocutPage;
	Artwork_UkeAndTubaPage ukeAndTubaPage;
	

	
	public ArtworkSteps(TestContext context) {
		testContext = context;
		artworkPage = testContext.getPageObjectManager().getArtworkPage();
		statementPage = testContext.getPageObjectManager().getArtistStatementPage();
		photographyPage = testContext.getPageObjectManager().getPhotographyPage();
		linocutPage = testContext.getPageObjectManager().getLinocutPage();
		ukeAndTubaPage = testContext.getPageObjectManager().getUkeAndTubaPage();
		homePage = testContext.getPageObjectManager().getHomePage();
	}
	
	@Given("^I am a user who is not logged in on the home page")
	public void i_am_a_user_who_is_not_logged_on_the_home_page() {
		homePage.openHomePage();	
	} 
	@When("^I cursor over artwork from the header")
	public void i_cursor_over_artwork_from_the_header() {
		homePage.expandArtworkDropDownOption();
		
	}
	
	@And("^I select the (.+) provided")
	public void i_select_the_artworksubpage_provided(String artworkSubPage) {
		homePage.selectArtworkDropDownOption(artworkSubPage);
	}
	
	@Then("^I am shown the (.+) Artwork Sub page.")
	public void i_am_show_the_ArtworkSubPage(String artworkSubPage){
		switch (artworkSubPage) {
		case "ARTWORK": 	
			artworkPage.lookForPageTitle();
			break;
		case "Artist Statement": 	
			statementPage.lookForPageTitle();
			break;
		case "Linocut": 	
			linocutPage.lookForPageTitle();
			break;
		case "Photography Work": 	
			photographyPage.lookForPageTitle();
			break;
		case "Uke and Tuba Discography": 	
			ukeAndTubaPage.lookForPageTitle();
			break;
		default:
			Log.info("The artwork Sub Page "+ artworkSubPage + "is not present");	
		}
	}
	
	@When("^I access the Artist statement page from the header")
	public void i_access_the_artist_statement_page_from_the_header() {
			homePage.expandArtworkDropDownOption();
			homePage.selectArtworkDropDownOption("Artist Statement");
		}
		
	@When("^I access the Linocut artwork page from the header")
	public void i_access_the_linocut_artwork_page_from_the_header() {
			homePage.expandArtworkDropDownOption();
			homePage.selectArtworkDropDownOption("Linocut");
		}
	
	@Then("^I am shown the artists statment including a quote from George A Walker")
	public void i_am_shown_the_artist_statement_including_a_quote_from_George_A_Walker() {
			statementPage.LookForGeorgeAWalkerQuote();		
		}
	
	@Then("^I am shown a Linocut artwork page Materials list")
	public void i_am_shown_a_materials_list(){
			linocutPage.lookForMaterialsList();
		}
	
	@And("^I am shown a gallery of images")
	public void i_am_shown_a_gallery_of_images() {
			linocutPage.lookForGalleryObject();
		}
	
	@And("^I select a random image from the Linocut Pages gallery of images")
	public void I_select_a_random_image_from_the_Linocut_Pages_gallery_of_images() {
			linocutPage.selectAnImageFromTheGalleryOfImages();
		}
	
	@And("^I select the (.+) gallery key")
	public void I_select_the_Direction_key(String direction) {
			linocutPage.selectGalleryDirectionKey(direction);
	}
	
	@Then("^I am taken to the image in the corrisponding (.+)")
	public void I_am_taken_to_the_image_in_the_corrisponding_direction(String direction) {
		   linocutPage.checkLinocutGalleryImageIsCorrectPostDirection(direction);
	}
}
