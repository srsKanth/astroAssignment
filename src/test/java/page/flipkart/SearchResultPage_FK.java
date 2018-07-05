package page.flipkart;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import core.SeleniumMethods;

public class SearchResultPage_FK extends SeleniumMethods {

	By searchResultPageSortByLink = By.xpath("//span[text()='Sort By']");
	String searchResultPageSearchKeyWordString = "//span[contains(text(),'Showing')]/span[text()='REPLACE_TEXT']";
	By nextLink = By.xpath("//span[text()='Next']");

	By productNameList = By.xpath("//div[@class='_3wU53n']");
	By productLinkList = By.xpath("//div[@class='_3wU53n']/ancestor::a");
	By productPriceList = By.xpath("//div[@class='_1vC4OE _2rQ-NK']");

	public void verifySearchResultPageWithSearchKeyword(String searchKeyword) {
		waitForElementToBeVisible(searchResultPageSortByLink, 25);
		if (isElementPresent(searchResultPageSortByLink) && isElementPresent(
				By.xpath(searchResultPageSearchKeyWordString.replace("REPLACE_TEXT", searchKeyword.toLowerCase()))))
			System.out.println("Serach result page displayed for given search keywor");
		else
			System.err.println("Serach result page is displayed or given search keyword is not present");
	}

	public List<JSONObject> getElementsDetailsForGivenKeyWord(String searchKeyword) {
		List<JSONObject> productDetails = new ArrayList<>();
		boolean flag = false;

		do {
			List<WebElement> productNameElements = getWebElements(productNameList);
			List<WebElement> productPriceElements = getWebElements(productPriceList);
			List<WebElement> productLinkElements = getWebElements(productLinkList);

			for (int i = 0; i < productNameElements.size(); i++) {
				String productName = productNameElements.get(i).getText().toLowerCase();
				if (productName.contains(searchKeyword.toLowerCase())) {
					JSONObject productObject = new JSONObject();
					productObject.put("name", productName);
					int productPrice = Integer.parseInt(productPriceElements.get(i).getText().replace(",", "").substring(1));
					productObject.put("price", productPrice);
					productObject.put("link", productLinkElements.get(i).getAttribute("href"));
					productObject.put("app", "FlipKart");

					productDetails.add(productObject);
				}
			}
			if (isElementPresent(nextLink)) {
				clickOnElement(nextLink);
				hardSleep(2);
				flag = true;
			} else
				flag = false;
		} while (flag);

		return productDetails;
	}

}
