package objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignPage {
	private static final By USERNAME_INPUT_LOCATOR = By.xpath("//input[@placeholder='Username']");
	private static final By PASSWORD_INPUT_LOCATOR = By.xpath("//input[@placeholder='PIN/Password']");
	private static final By GREEN_BUTTON_LOCATOR = By.xpath("//div[@class='green-button']");

	private WebDriverWait wait;

	public SignPage(WebDriver driver, WebDriverWait wait) {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.wait = wait;
	}

	public void performSignature(String username, String password) {
		enterUsername(username);
		enterPassword(password);
		clickGreenButton();
	}

	private void enterUsername(String username) {
		WebElement usernameInput = wait.until(ExpectedConditions.presenceOfElementLocated(USERNAME_INPUT_LOCATOR));
		usernameInput.click();
		usernameInput.clear();
		usernameInput.sendKeys("valeshan.naidoo@strongroom.ai");
	}

	private void enterPassword(String password) {
		WebElement passwordInput = wait.until(ExpectedConditions.elementToBeClickable(PASSWORD_INPUT_LOCATOR));
		passwordInput.click();
		passwordInput.sendKeys("1111");
	}

	private void clickGreenButton() {
		WebElement greenButton = wait.until(ExpectedConditions.elementToBeClickable(GREEN_BUTTON_LOCATOR));
		greenButton.click();
	}

}
