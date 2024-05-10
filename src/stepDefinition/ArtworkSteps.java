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
		homePage.expandArtworkDropDownOptionAndSelect();
		
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
		@When("^I access the Artist statement page")
		public void i_access_the_artist_statement_page() {
			homePage.selectArtworkDropDownOption("Artist statement");
			
		}

}
