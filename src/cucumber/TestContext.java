package cucumber;

import managers.Log;
import managers.PageObjectManager;
import managers.WebDriverManager;

public class TestContext {

	private WebDriverManager _webDriverManager;
	private PageObjectManager _pageObjectManager;
	
	public TestContext() {
	}
	
	public void initializeWebManager() {
		_webDriverManager = new WebDriverManager();
		_pageObjectManager = new PageObjectManager(_webDriverManager.getDriver());
	}
	
	public WebDriverManager getWebDriverManager() {
		return _webDriverManager;
	}
	
	public PageObjectManager getPageObjectManager() {
		return _pageObjectManager;
	}
	
}
