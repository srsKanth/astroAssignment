package core;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utils.FileHandling;

public class TestngMethods {
	static String browserType;
	static {
		browserType = FileHandling.getGlobalPropertyValue("browser");
	}

	@BeforeMethod
	public void beforeMethod() {
		SeleniumMethods.initiateBrowser(browserType);
	}

	@AfterMethod
	public void tearDown() {
		if (SeleniumMethods.getDriver() != null) {
			SeleniumMethods.getDriver().quit();
		}
	}

}
