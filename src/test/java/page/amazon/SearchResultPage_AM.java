package page.amazon;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import core.SeleniumMethods;

public class SearchResultPage_AM extends SeleniumMethods {

	String searchResultPageSearchKeyWordString = "//span[@id='editableBreadcrumbContent']//span[text()='\"REPLACE_TEXT\"']";
	By nextLink = By.id("pagnNextString");
	By productLinkList = By.xpath("//span[contains(@class,'s-price')]/parent::a/parent::div/preceding-sibling::div/a");
	By productPriceList = By.xpath("//span[contains(@class,'s-price')]");
	By searchPageMaxPaginationCount = By.className("pagnLink");
	By serachPageCurrentPaginationCount = By.className("pagnCur");
	By imageViewLink = By.xpath("//a[@title='Image View']");
	By listViewLink = By.xpath("//a[@title='List View']");

	/**
	 * method to verify if the given keyword present in page
	 * 
	 * @param searchKeyword
	 */
	public void verifySearchResultPageWithSearchKeyword(String searchKeyword) {
		if (isElementPresent(By.xpath(searchResultPageSearchKeyWordString.replace("REPLACE_TEXT", searchKeyword))))
			System.out.println("Serach result page displayed for given search keywor");
		else
			System.err.println("Serach result page is displayed or given search keyword is not present");
	}

	/**
	 * method to get the search results for the given key word in list in JSON
	 * object
	 * 
	 * @param searchKeyword
	 * @return
	 */
	public List<JSONObject> getElementsDetailsForGivenKeyWord(String searchKeyword) {

		List<JSONObject> productDetails = new ArrayList<>();

		// page loads view is keep changing so ensuring to load in one view
		if (isElementPresent(imageViewLink)) {
			clickOnElement(imageViewLink);
			// waiting till the image view loads
			waitForElementToBeVisible(listViewLink, 10);
		}

		// get the pagination max count to loop through to get the results
		int maxPaginationCount = Integer.parseInt(getText(searchPageMaxPaginationCount)) + 1;

		// loop through the pages and get the produce details if the product
		// name matches with given keyword
		for (int pagenationCount = 0; pagenationCount < maxPaginationCount; pagenationCount++) {

			// get the project price and link elements in list
			List<WebElement> productPriceElements = getWebElements(productPriceList);
			List<WebElement> productLinkElements = getWebElements(productLinkList);

			// loop through the product list and check if the product title
			// contains the keyword and add the product details to list
			for (int i = 0; i < productPriceElements.size(); i++) {
				String productName = productLinkElements.get(i).getAttribute("title").toLowerCase();
				if (productName.contains(searchKeyword.toLowerCase())) {
					JSONObject productObject = new JSONObject();
					productObject.put("name", productName);
					productObject.put("price",
							Integer.parseInt(productPriceElements.get(i).getText().trim().replace(",", "")));
					productObject.put("link", productLinkElements.get(i).getAttribute("href"));
					productObject.put("app", "Amazon");

					productDetails.add(productObject);
				}
			}

			if (isElementPresent(nextLink)) {
				clickOnElement(nextLink);
				hardSleep(2);
			}

		}
		return productDetails;
	}

}
