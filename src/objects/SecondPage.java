package objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SecondPage {
	private final WebDriver driver;
	private final WebDriverWait wait;

	// Constants for locators
	private static final By SELECT_LOCATION_BUTTON = By.xpath("//span[@aria-label='Select location']");
	private static final By DROPDOWN_OPTION = By.xpath("//li[@id='pv_id_2_4']");
	private static final By SECOND_PAGE_BUTTON = By.xpath("//p[@class='blue-button']");

	public SecondPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	/**
	 * Selects a location from the dropdown.
	 */
	public void selectLocationFromDropdown() {
		WebElement selectLocation = wait.until(ExpectedConditions.elementToBeClickable(SELECT_LOCATION_BUTTON));
		selectLocation.click();

		WebElement dropdownOption = wait.until(ExpectedConditions.elementToBeClickable(DROPDOWN_OPTION));
		dropdownOption.click();
	}

	/**
	 * Clicks the button on the second page.
	 */
	public void clickSecondPageButton() {
		// Instead of Thread.sleep, use an explicit wait
		WebDriverWait extendedWait = new WebDriverWait(driver, Duration.ofSeconds(90));
		WebElement secondPageButton = extendedWait.until(ExpectedConditions.elementToBeClickable(SECOND_PAGE_BUTTON));
		secondPageButton.click();
	}
}
