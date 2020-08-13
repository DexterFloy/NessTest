package test.cucumber.base;

import static test.cucumber.utility.ValuesPropertiesHandling.getConfigProperties;
import static test.cucumber.smoketest.StepDefinitionsHomePage.policyProcessing;
import static test.cucumber.smoketest.StepDefinitionsHomePage.XPATH_BUTTON_NEXT;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import cucumber.api.java.en.Given;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import test.cucumber.utility.WebPageHandling;

public class BaseStepDefinition {

	private static WebDriver driver = null;
	private static Properties properties = getConfigProperties();
	
	@Given("^I open the browser$")
	public static void initialize() {
		WebDriverManager.chromedriver().version("77").setup();
		ChromeDriverManager.getInstance(DriverManagerType.CHROME).setup();
//		runHeadless();
		runWithGUI();
	}

	@Given("^I close the browser$")
	public static void tearDown() {
		driver.close();
		driver.quit();
	}

	@Given("^I Navigate To \"(.*)\" Page$")
	public void iNavigateToPage(String value) {
		switch (value) {
			case "bottomOfThe":
				policyProcessing();
				WebElement areaToGoTo = new WebPageHandling().getElementByXPath(XPATH_BUTTON_NEXT);
				Actions action = new Actions(driver);
				action.moveToElement(areaToGoTo);
				action.perform();
				break;
			default:
				driver.navigate().to(properties.getProperty(value, value));
				break;
		}
	}

	public static WebDriver getDriver() {
		return driver;
	}

	private static void runHeadless() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--disable-gpu");
		driver = new ChromeDriver(options);
	}

	private static void runWithGUI() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

}