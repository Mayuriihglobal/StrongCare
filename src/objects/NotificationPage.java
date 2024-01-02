package objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotificationPage {
    private final WebDriverWait wait;

    // Constants for locators
    private static final By NOTIFICATION_ICON_SELECTOR = By.cssSelector("i.pi.pi-exclamation-circle");

    public NotificationPage(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
    }

    /**
     * Clicks on the notification icon.
     */
    public void clickNotificationIcon() {
        WebElement notificationIcon = waitForElement(NOTIFICATION_ICON_SELECTOR);
        notificationIcon.click();
    }

    /**
     * Waits for the presence of an element identified by the given locator.
     *
     * @param locator the By locator strategy
     * @return the WebElement once it's present
     */
    private WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}
