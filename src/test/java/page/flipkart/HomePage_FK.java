package page.flipkart;

import org.openqa.selenium.By;

import core.SeleniumMethods;
import utils.FileHandling;

public class HomePage_FK extends SeleniumMethods {

	By searchTextBox = By.name("q");
	By serachTextBoxSubmitButton = By.xpath("//button[@type='submit']");
	By loginOverlayNewToSignUpLink = By.xpath("//span[text()='New to Flipkart? Sign up']");
	By loginOverlayCloseButton = By.xpath("//button[text()='\u2715']");

	public void launchFlipkartHomePage() {
		goToUrl(FileHandling.getGlobalPropertyValue("flipkarturl"));

		// close if login overlay present
		if (isElementPresent(loginOverlayNewToSignUpLink, 5)) {
			clickOnElement(loginOverlayCloseButton);
		}
	}

	public void searchWithKeyWord(String searchKeyword) {
		typeText(searchTextBox, searchKeyword);
		clickOnElement(serachTextBoxSubmitButton);
	}

}
