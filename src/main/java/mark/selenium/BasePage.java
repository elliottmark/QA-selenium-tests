package mark.selenium;

import static org.testng.Assert.fail;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

/**
 * BasePage contains reusable functions
 */
public class BasePage {

	public WebDriver driver;

	protected BasePage(WebDriver driver) {
		this.driver = driver;
	}

	protected void visit(String appURL) {
		driver.get(appURL);
	}
	
	protected void waitToLoad(int num) {
		try {
			Thread.sleep(num);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected WebElement getElementWhenVisible(By elementToken) {
		WebElement foundElement=null;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		try {
			foundElement = wait.until(ExpectedConditions.elementToBeClickable(elementToken));
		} catch (NoSuchElementException excp) {
			fail(logMessage("[ASSERT FAILED]: Element " + elementToken + " not found on the webPage !!!"));
		} catch (NullPointerException npe) {
			fail("[UNHANDLED EXCEPTION]: " + npe.getLocalizedMessage());
		}
		return foundElement;
	}
	
	protected boolean clickOnElement(By elementToken) {
		try {
			WebElement el = getElementWhenVisible(elementToken);
			el.click();
			return true;
		} catch (NullPointerException npe) {
			fail("[UNHANDLED EXCEPTION]: " + npe.getLocalizedMessage());
		}
		return false;
	}
	
	protected boolean clickOnElementViaJS(String elementToken) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.querySelector(\""+elementToken+"\").click()");
		return true;
	}

	protected String logMessage(String message) {
		Reporter.log(message, true);
		return message;
	}

}
