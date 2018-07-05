package page.astro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import core.SeleniumMethods;
import utils.FileHandling;

public class HomePage extends SeleniumMethods {

	By footerCopyRightText = By.id("footeryear");

	public void goToAstroHomePage() {
		goToUrl(FileHandling.getGlobalPropertyValue("astroUrl"));

	}

	public void pageLoadTimeValidation() {
		long startTime = System.currentTimeMillis();

		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		String script = "window.location = \'" + FileHandling.getGlobalPropertyValue("astroUrl") + "\'";
		js.executeScript(script);
		long endtime = 0;

		boolean flag = true;
		do {
			endtime = System.currentTimeMillis();

			System.out.println(endtime - startTime);
			if ((endtime - startTime) < 100l && flag == true) {
				System.err.println("Application is not loaded in within .1 seconds");
				flag = false;
			}
		} while (!js.executeScript("return document.readyState").toString().equals("complete"));

		if ((endtime - startTime) < 5000)
			System.out.println("Äpplicaiton has been loaded within 5 seconds");
		else
			System.err.println("Äpplicaiton has not been loaded within 5 seconds");
	}

	public void validateHomePageFooter() {
		isElementPresent(footerCopyRightText, "Page loaded completely",
				"Footer copy right text is not present and page has not loaded completely");
	}

	public void validateBrokenUrls() {

		List<WebElement> linkElements = getWebElements(By.tagName("a"));
		System.out.println("Found '" + linkElements.size() + "' links in homePage.");

		// iterate through all the link elements and find the urls which is not
		// 200
		int nonAcceptUrlCount = 0;
		for (WebElement element : linkElements) {

			// get the url link
			String url = element.getAttribute("href");
			long starttime = System.currentTimeMillis(); // get start time
			int responseCode = getResponseCode(url); // get status code
			long endTime = System.currentTimeMillis(); // get end time

			// check if the response code is 200 else print the response time
			// with response code
			if (responseCode != 200) {

				System.out.println("Url : " + url + "| responseCode :" + responseCode + "| responseTime "
						+ (endTime - starttime) + " milliseconds");
				nonAcceptUrlCount++;

			}
		}

		System.out.println(nonAcceptUrlCount + " links/urls are not getting 200 response.");

	}

}
