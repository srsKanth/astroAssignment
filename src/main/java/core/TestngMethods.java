/**
 * Contains Methods related testng actions to build framework  
 */
package core;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utils.FileHandling;

public class TestngMethods {
	static String browserType;

	/**
	 * Static block used to initialize global parameters We load browser value
	 * from
	 */

	static {
		browserType = FileHandling.getGlobalPropertyValue("browser");
	}

	/**
	 * Method will be called before running each @test we use @Beforemethod
	 * annotation to initialize browser
	 */
	@BeforeMethod
	public void beforeMethod() {
		SeleniumMethods.initiateBrowser(browserType);

	}

	/**
	 * Method will be called each time after @Test execution completed We kill
	 * all the initialized browsers using this method
	 */
	@AfterMethod
	public void tearDown() {
		if (SeleniumMethods.getDriver() != null) {
			SeleniumMethods.getDriver().quit();
		}
	}

}
