/**
 * Contains methods related to browser interactions using Selenium library
 */
package core;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class SeleniumMethods {

	private static String rootpath = System.getProperty("user.dir");
	private static WebDriver driver;
	private static int implicitWaitInSeconds = 10;

	// getter setter method

	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver driver) {
		SeleniumMethods.driver = driver;
	}

	public static int getImplicitWaitInSeconds() {
		return implicitWaitInSeconds;
	}

	public static void setImplicitWaitInSeconds(int implicitWaitInSeconds) {
		SeleniumMethods.implicitWaitInSeconds = implicitWaitInSeconds;
	}

	public static void initiateBrowser(String browser) {
		try {
			switch (browser) {

			case "chrome":
				System.setProperty("webdriver.chrome.driver",
						rootpath + "\\src\\main\\resources\\drivers\\chromedriver.exe");
				driver = new ChromeDriver();
				break;

			case "firefox":

				System.setProperty("webdriver.gecko.driver",
						rootpath + "\\src\\main\\resources\\drivers\\geckodriver.exe");
				driver = new FirefoxDriver();
				break;
			}

			// setting driver
			setDriver(driver);

			// maximize window
			getDriver().manage().window().maximize();

			setImplicitWaitInSeconds(implicitWaitInSeconds);
		} catch (Exception e) {
			System.err.println("Exception thrown : " + e.getMessage());
		}

	}

	protected void goToUrl(String url) {
		try {
			getDriver().get(url);
		} catch (Exception e) {
			System.err.println("Exception thrown while trying to load url " + url + "':" + e.getMessage());
		}

	}

	protected WebElement getWebElement(By locator) {
		WebElement element = null;
		try {
			element = getDriver().findElement(locator);
		} catch (Exception e) {

		}
		return element;
	}

	protected List<WebElement> getWebElements(By locator) {
		List<WebElement> elements = null;
		try {
			elements = getDriver().findElements(locator);
		} catch (Exception e) {
			System.err.println("Exception thrown while trying to  " + e.getMessage());
		}
		return elements;
	}

	protected String getText(By locator) {
		String rtnText = "";
		try {
			rtnText = getDriver().findElement(locator).getText();
		} catch (Exception e) {
			System.err.println("Exception thrown while trying to  " + e.getMessage());
		}
		return rtnText;
	}

	protected void clickOnElement(By locator) {
		try {
			getWebElement(locator).click();
			waitForPageLoad(40);
		} catch (Exception e) {
			System.err.println("Exception thrown while trying to  " + e.getMessage());
		}
	}

	protected void typeText(By locator, String text) {
		try {
			WebElement element = getWebElement(locator);
			element.sendKeys(text);
		} catch (Exception e) {
			System.err.println("Exception thrown while trying to enter text  " + e.getMessage());
		}
	}

	public void isElementPresent(By locator, String successMessage, String failMessage) {

		try {
			if (getWebElement(locator).isDisplayed()) {
				System.out.println(successMessage);
			} else
				System.err.println(failMessage);

		} catch (Exception e) {

		}

	}

	public boolean isElementPresent(By locator) {
		boolean blnFlag = false;
		try {
			if (getWebElement(locator).isDisplayed())
				return true;

		} catch (Exception e) {

		}
		return blnFlag;

	}

	public boolean isElementPresent(By locator, int timeOutInSeconds) {

		boolean blnFlag = false;
		try {
			if (waitForElementToBeVisible(locator, timeOutInSeconds).isDisplayed())
				return true;

		} catch (Exception e) {

		}
		return blnFlag;
	}

	public WebElement waitForElementToBeVisible(By locator, int timeOutInSeconds) {
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSeconds);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {

		}
		return element;
	}

	public void isClickable(By locator) {
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), 1);
			element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (Exception e) {
			System.err.println("Not clickable ");
		}

		System.out.println(element);
	}

	protected boolean waitForPageLoad(int timeoutInSeconds) {

		final String javaScriptFunction = "function f(){return document.readyState;} return f();";

		Function<WebDriver, Boolean> condition = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver d) {
				String result = (String) ((JavascriptExecutor) d).executeScript(javaScriptFunction);
				System.err.println("The page is in " + result + " state");
				if (result.equalsIgnoreCase("complete"))
					return true;
				return false;
			}
		};
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).pollingEvery(2, TimeUnit.SECONDS)
				.withTimeout(timeoutInSeconds, TimeUnit.SECONDS).ignoring(RuntimeException.class);
		try {
			return wait.until(condition);
		} catch (TimeoutException err) {
			System.out.println("THE PAGE IS NOT LOADED EVEN AFTER THE TIMEOUT, OF 180 SECONDS"
					+ "THIS COULD MEAN THAT THE AUTOMATION MIGHT FAIL OR BE UNRELIABLE");
			return false;
		}
	}

	protected int getResponseCode(String urlValue) {
		int code = 0;
		try {
			URL url = new URL(urlValue);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			code = connection.getResponseCode();

		} catch (Exception e) {

		}
		return code;
	}

	protected void hardSleep(int timeInSeconds) {
		try {
			Thread.sleep(timeInSeconds * 1000);
		} catch (Exception e) {

		}
	}

}
