package page.amazon;

import org.openqa.selenium.By;

import core.SeleniumMethods;
import utils.FileHandling;

public class HomePage_AM extends SeleniumMethods {

	By searchTextBox = By.id("twotabsearchtextbox");
	By serachTextBoxSubmitButton = By.xpath("//input[@value='Go']");

	public void launchAmazonHomePage() {
		goToUrl(FileHandling.getGlobalPropertyValue("amazonUrl"));
	}

	public void searchWithKeyWord(String searchKeyword) {
		typeText(searchTextBox, searchKeyword);
		clickOnElement(serachTextBoxSubmitButton);
	}

}
