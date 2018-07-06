/**
 * Contains Methods related testng actions to build framework  
 */
package core;

import java.lang.reflect.Method;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
	public void beforeMethod(Method method) {
		Test test = method.getAnnotation(Test.class);
		SeleniumMethods.initiateBrowser(browserType);
		System.out.println("***********Execution started for " + test.description()+"*******************");

	}

	/**
	 * Method will be called each time after @Test execution completed We kill
	 * all the initialized browsers using this method
	 */
	@AfterMethod
	public void tearDown(Method method) {
		if (SeleniumMethods.getDriver() != null) {
			SeleniumMethods.getDriver().quit();
		}
		
		Test test = method.getAnnotation(Test.class);
		System.out.println("***********Execution compelted for " + test.description()+"*******************");
	}

}
