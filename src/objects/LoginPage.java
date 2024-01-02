package objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	private final WebDriver driver;
	private final WebDriverWait wait;

	// Constants for XPath expressions
	private static final String LOCATION_INPUT_XPATH = "//input[@placeholder='Location']";
	private static final String LOCATION_RESULT_XPATH = "//p[@class='drug-search-result']";
	private static final String USERNAME_INPUT_XPATH = "//input[@placeholder='Username/email']";
	private static final String PASSWORD_INPUT_XPATH = "//input[@placeholder='Password']";
	private static final String LOGIN_BUTTON_XPATH = "//p[@class='blue-button']";

	public LoginPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	public void openLoginPage(String url) {
		driver.get(url);
	}

	public void enterLocation(String location) {
		WebElement locationInput = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(LOCATION_INPUT_XPATH)));
		locationInput.sendKeys(location);

		WebElement locationResultElement = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath(LOCATION_RESULT_XPATH + "[text()='" + location + "']")));
		locationResultElement.click();
	}

	public void enterCredentials(String username, String password) {
		WebElement usernameInput = driver.findElement(By.xpath(USERNAME_INPUT_XPATH));
		usernameInput.sendKeys(username);

		WebElement passwordInput = driver.findElement(By.xpath(PASSWORD_INPUT_XPATH));
		passwordInput.sendKeys(password);
	}

	public void clickLoginButton() {
		WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(LOGIN_BUTTON_XPATH)));
		loginButton.click();
	}
}
