package strongroom;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import clickUP.createTask;
import objects.LoginPage;
import objects.SecondPage;

public class Base extends createTask {
	protected WebDriver driver;
	protected WebDriverWait wait;
	private LoginPage loginPage;
	private SecondPage secondPage;
	private static String formattedDateTime;
	private static String loginTaskDescription;
	protected static String inputdata;

	@BeforeClass
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		loginPage = new LoginPage(driver, wait);
		secondPage = new SecondPage(driver, wait);
		formattedDateTime = getCurrentDateTime();

		// Test Scenario
		loginPage.openLoginPage();
		LoginPage.login();
		loginPage.clickLoginButton();

		secondPage.selectLocationFromDropdown();
		secondPage.clickSecondPageButton();
		// notificationPage.clickNotificationIcon();

		String loginPageURL = driver.getCurrentUrl();
		String selectedLocation = secondPage.getSelectedLocation(); // Assuming you have a method to get selected
																	// location
		String enteredLocation = LoginPage.getEnteredLocation();
		String enteredUsername = LoginPage.getEnteredUsername(); // Assuming you have a method to get entered username
		String enteredPassword = LoginPage.getEnteredPassword(); // Assuming you have a method to get entered password

		// Creating ClickUp task description for login
		loginTaskDescription = "Login Page URL: " + loginPageURL + "\n" + "Entered Location: " + enteredLocation + "\n"
				+ "Entered Username: " + enteredUsername + "\n" + "Entered Password: " + enteredPassword + "\n"
				+ "Selected Location: " + selectedLocation;

	}

	

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		// Use the stored formattedDateTime in the method

		if (result.getStatus() == ITestResult.FAILURE) {
			String taskDescription = loginTaskDescription;
			String listId = "901600130678";
			String status = "FAIL";
			createClickUpTask(formattedDateTime, taskDescription, listId, status);

			String methodName = result.getMethod().getMethodName();
			String consoleError = extractConsoleError(result);
			String faildes = loginTaskDescription + "\n" + inputdata + "Result: " + status + "\n" + "Error message: "
					+ consoleError;

			createClickUpTask(methodName, faildes, listId, status);

			String mainTaskId = getTaskId(formattedDateTime, listId);
			String subTaskId = getTaskId(methodName, listId);
			updateTask(subTaskId, mainTaskId);

		} else {
			String taskDescription = loginTaskDescription;
			String listId = "901600535467";
			String status = "PASS";

			createClickUpTask(formattedDateTime, taskDescription, listId, status);

			String methodName = result.getMethod().getMethodName();
			String consoleError = extractConsoleError(result);
			String faildes = loginTaskDescription + "\n" + inputdata + "Result: " + status + consoleError;
			createClickUpTask(methodName, faildes, listId, status);

			String mainTaskId = getTaskId(formattedDateTime, listId);
			String subTaskId = getTaskId(methodName, listId);
			updateTask(subTaskId, mainTaskId);
		}

	}

	@AfterClass
	public void tearDown() {
		driver.quit();

	}
	
	private String getCurrentDateTime() {
		LocalDateTime now = LocalDateTime.now(ZoneId.of("Australia/Sydney"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy H:m");
		return now.format(formatter);
	}

	private String extractConsoleError(ITestResult result) {
		String consoleOutput = "";
		Throwable throwable = result.getThrowable();
		if (throwable != null) {
			consoleOutput = throwable.getMessage();
		}
		return consoleOutput;
	}

}