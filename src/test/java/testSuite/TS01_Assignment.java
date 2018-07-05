package testSuite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import core.TestngMethods;
import page.amazon.HomePage_AM;
import page.amazon.SearchResultPage_AM;
import page.astro.HomePage;
import page.flipkart.HomePage_FK;
import page.flipkart.SearchResultPage_FK;

public class TS01_Assignment extends TestngMethods {

	// @Test
	public void TC01_VefiyPageLoadAndBrokenLinks() {
		HomePage astroHomePage = new HomePage();

		astroHomePage.pageLoadTimeValidation();
		astroHomePage.validateHomePageFooter();

		astroHomePage.goToAstroHomePage();
		astroHomePage.validateBrokenUrls();
	}

	@Test
	public void TC02_FindPhonePriceDifferenceAndPrintInAscendingOrder() {

		List<JSONObject> combinedSearchResults = new ArrayList<>();
		String searchKeyword = "iPhone 7";

		HomePage_FK flipkartHomePage = new HomePage_FK();
		SearchResultPage_FK fkSearchPage = new SearchResultPage_FK();
		HomePage_AM amazonHomePage = new HomePage_AM();
		SearchResultPage_AM amazonSearchPage = new SearchResultPage_AM();

		flipkartHomePage.launchFlipkartHomePage();
		flipkartHomePage.searchWithKeyWord(searchKeyword);

		// verify search result page and result display for Iphone 7
		fkSearchPage.verifySearchResultPageWithSearchKeyword(searchKeyword);
		combinedSearchResults.addAll(fkSearchPage.getElementsDetailsForGivenKeyWord(searchKeyword));

		amazonHomePage.launchAmazonHomePage();
		amazonHomePage.searchWithKeyWord(searchKeyword);
		amazonSearchPage.verifySearchResultPageWithSearchKeyword(searchKeyword);
		combinedSearchResults.addAll(amazonSearchPage.getElementsDetailsForGivenKeyWord(searchKeyword));

		System.out.println("Found " + combinedSearchResults.size());

		Collections.sort(combinedSearchResults, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject jsonObjectA, JSONObject jsonObjectB) {
				int compare = 0;
				try {
					int keyA = jsonObjectA.getInt("price");
					int keyB = jsonObjectB.getInt("price");
					compare = Integer.compare(keyA, keyB);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return compare;
			}
		});
		
		for (JSONObject obj : combinedSearchResults) {
			System.err.println(obj);
		}

	}

}
