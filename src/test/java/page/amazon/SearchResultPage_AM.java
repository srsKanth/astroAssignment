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

	public void verifySearchResultPageWithSearchKeyword(String searchKeyword) {
		if (isElementPresent(By.xpath(searchResultPageSearchKeyWordString.replace("REPLACE_TEXT", searchKeyword))))
			System.out.println("Serach result page displayed for given search keywor");
		else
			System.err.println("Serach result page is displayed or given search keyword is not present");
	}

	public List<JSONObject> getElementsDetailsForGivenKeyWord(String searchKeyword) {

		if (isElementPresent(imageViewLink)) {
			clickOnElement(imageViewLink);
			// waiting till the image view loads
			waitForElementToBeVisible(listViewLink, 10);
		}
		List<JSONObject> productDetails = new ArrayList<>();

		int maxPaginationCount = Integer.parseInt(getText(searchPageMaxPaginationCount)) + 1;

		for (int pagenationCount = 0; pagenationCount < maxPaginationCount; pagenationCount++) {

			List<WebElement> productPriceElements = getWebElements(productPriceList);
			List<WebElement> productLinkElements = getWebElements(productLinkList);

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
				try {
					Thread.sleep(2000);
				} catch (Exception e) {

				}
			}

		}
		return productDetails;
	}

}
