package test.cucumber.smoketest;

import static test.cucumber.base.BaseStepDefinition.getDriver;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import test.cucumber.utility.WebPageHandling;

public class StepDefinitionsHomePage {

	private static final String XPATH_INPUT_TYPE = ".//input[@type='%s']";
	public static final String XPATH_BUTTON_NEXT = ".//a[contains(text(),'Next')]";
	private static final String XPATH_P_STRONG = ".//p/strong[2]";
	private static final String XPATH_ARTICLE_ANCHOR = ".//article[1]/a";
	
	@When("^I Insert \"(.*)\" In The \"(.*)\" Area$")
	public void iInsertInTheArea(String insertElement, String area) {
		insertIntoArea(10, String.format(XPATH_INPUT_TYPE, area), insertElement);
	}

	@When("I Hit The \"(.*)\" Keyboard Key On \"(.*)\" Area")
	public void iHitTheKeyboardKeyOnArea(String keyValue, String area) {
		switch (keyValue) {
		case "enter":
			new WebPageHandling().getElementByXPath(String.format(XPATH_INPUT_TYPE, area)).sendKeys(Keys.ENTER);
			break;
		default:
			fail(keyValue + " not yet handled!");
		}
	}

	@Then("I Verify That \"(.*)\" Shows Up To Result Number \"(.*)\"")
	public void iVerifyThatResultsShowUpToNumber(String list, String count) {
		switch (list) {
		case "resultsList":
			waitForWebElement(10, XPATH_P_STRONG);
			assertTrue(count.equals(new WebPageHandling().getElementByXPath(XPATH_P_STRONG).getText()));
			break;
		default:
			fail(list + " not yet handled!");
			break;
		}
	}

	@When("I Click The \"(.*)\" Button")
	public void iClickTheButton(String buttonElement) {
		switch (buttonElement) {
		case "nextPage":
			waitAndClick(10,XPATH_BUTTON_NEXT);
			break;
		case "firstResult":
			waitAndClick(10, XPATH_ARTICLE_ANCHOR);
			break;
		default:
			fail(buttonElement + " not yet handled!");
			break;
		}
	}

	@Then("I Verify That \"(.*)\" Page Is Loaded")
	public void iVerifyThatPageIsLoaded(String elementName) {

		switch (elementName) {
		case "searchResult":
			waitForDebugTime(1000);
			assertTrue(getDriver().getCurrentUrl().endsWith(".pdf"));	
			break;
		default:
			fail(elementName + " not yet handled!");
			break;
		}
	}

	private void waitAndClick(int timeout, String xpath) {
		waitForWebElement(timeout, xpath);
		new WebPageHandling().getElementByXPath(xpath).click();
	}

	private void insertIntoArea(int timeout, String xpath, String value) {
		waitForWebElement(timeout, xpath);
		new WebPageHandling().getElementByXPath(xpath).sendKeys(value);
	}

	private void waitForWebElement(int timeout, String xpath) {
		WebDriverWait waiter = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
		try {
			waiter.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		} catch (TimeoutException e) {
			waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		}
	}
	
	public static void policyProcessing() {
		try {
			WebElement closeAndContinue = new WebPageHandling().getElementByXPath("//a[contains(text(),'Close this message and continue')]");
			if (closeAndContinue.isDisplayed()) {
				closeAndContinue.click();
			}
		} catch (NoSuchElementException e) {
		}
	}
	
	public static void waitForDebugTime(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
