package strongroom;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Administration extends Base {

	public static void administration(String action, String location, String drugname, String transaction_id,
			String resident, String drugqty, String note, String username, String pin, String AdminRound)
			throws InterruptedException {

		Thread.sleep(5000);
		Task_Name = action;
		String clickedAdminRoundText = null;
		String clickedLocationText = null;
		String clickedResidentText = null;
		String FoundMedicationText = null;

		WebElement Administration = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Administration']")));
		Administration.click();

		List<WebElement> spinnerElements = driver.findElements(By.xpath("//i[@class='pi pi-spin pi-spinner']"));

		for (WebElement spinner : spinnerElements) {
			wait.until(ExpectedConditions.invisibilityOf(spinner));
		}

		Thread.sleep(5000);

		spinnerElements = driver.findElements(By.xpath("//i[@class='pi pi-spin pi-spinner']"));

		for (WebElement spinner : spinnerElements) {
			wait.until(ExpectedConditions.invisibilityOf(spinner));
		}

		WebElement AdministrationRound = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//span[@aria-label='Select Administration Round']")));
		AdministrationRound.click();

		WebElement dropdownContainer = driver.findElement(By.xpath("//div[@class='p-dropdown-items-wrapper']"));

		List<WebElement> dropdownOptions = dropdownContainer.findElements(By.xpath(".//li[@class='p-dropdown-item']"));

		System.out.println("site AdministrationRound Size: " + dropdownOptions.size());

		for (WebElement option : dropdownOptions) {
			Thread.sleep(2000);
			System.out.println("site AdministrationRound: " + option.getText());

		}

		boolean AdministrationRound1 = false;

		AdminRound = AdminRound.replace("–", "-");

		for (WebElement option : dropdownOptions) {
			Thread.sleep(1000);

			if (option.getText().toLowerCase().contains(AdminRound.toLowerCase())
					|| option.getText().trim().contains(AdminRound.trim()) || option.getText().contains(AdminRound)
					|| option.getText().startsWith(AdminRound)) {
				AdministrationRound1 = true;
				clickedAdminRoundText = option.getText();
				option.click();
				break;
			}

			Thread.sleep(2000);
		}

		if (!AdministrationRound1) {
			inputdata = "\n" + "No Administration Round Found" + "\n" + "Entered Administration Round [" + AdminRound
					+ "] For Administration Not Found" + "\n";
			Task_Name = action;
			return;
		}

		// Continue with the logic for "Location"
		WebElement locationElement = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//div[@class='p-treeselect-label p-placeholder']")));
		locationElement.click();

		dropdownContainer = driver.findElement(By.xpath("//div[@class='p-tree-wrapper']"));

		dropdownOptions = dropdownContainer
				.findElements(By.xpath("//div[@class='p-tree-wrapper']//span[@class='p-treenode-label']"));

		for (WebElement option : dropdownOptions) {
			Thread.sleep(2000);
			System.out.println("Site Location: " + option.getText());
		}

		System.out.println("Site Location size: " + dropdownOptions.size());

		boolean locationFound = false;

		for (WebElement option : dropdownOptions) {
			Thread.sleep(1000);

			if (option.getText().toLowerCase().contains(location.toLowerCase())
					|| option.getText().trim().contains(location.trim()) || option.getText().contains(location)
					|| option.getText().startsWith(location)) {
				locationFound = true;
				clickedLocationText = option.getText();
				option.click();
				break;
			}

			Thread.sleep(2000);
		}

		if (!locationFound) {
			inputdata = "\n" + "No Location Found" + "\n" + "Entered Location [" + location + "] Not Found" + "\n";
			Task_Name = action;
			return;
		}

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//i[@class='pi pi-spin pi-spinner']")));

		// Continue with the logic for "Resident"
		Thread.sleep(1000);

		WebElement sidebar = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='administration-round-results']")));

		List<WebElement> residentOptions = sidebar
				.findElements(By.xpath("//div[@class='administration-round-results']//h4"));

		for (WebElement option : residentOptions) {
			Thread.sleep(2000);
			System.out.println("Site Resident: " + option.getText());
		}

		System.out.println("Site Resident size: " + residentOptions.size());

		boolean residentFound = false;

		for (WebElement option : residentOptions) {
			Thread.sleep(1000);

			if (option.getText().toLowerCase().contains(resident.toLowerCase())
					|| option.getText().trim().contains(resident.trim()) || option.getText().contains(resident)
					|| option.getText().startsWith(resident)) {
				residentFound = true;
				clickedResidentText = option.getText();
				option.click();
				break;
			}

			Thread.sleep(2000);
		}

		if (!residentFound) {
			inputdata = "\n" + "No Resident Found" + "\n" + "Entered Resident [" + resident + "] Not Found" + "\n";
			Task_Name = action;
			return;
		}

		/// ----
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//i[@class='pi pi-spin pi-spinner']")));

		// Continue with the logic for "Medication"
		// Find the dropdown container
		dropdownContainer = driver.findElement(By.xpath(
				"//body/div[@id='app']/div/div[@class='drug-register-view view']/div[@class='panel']/div/div/div[@class='active-chart-table-container']/div[@class='chart-approval-only']/div/div[@class='admin-chart-form-container']/div[@class='admin-chart-form']/div[@class='general-patient']/div[2]"));

		// Find the medication options within the dropdown container
		List<WebElement> medicationOptions = dropdownContainer
				.findElements(By.xpath("//p[@data-v-29335e91 and @style='font-size: 1em; font-weight: bold;']"));

		boolean medicationFound = false;

		for (WebElement option : medicationOptions) {
			try {
				Thread.sleep(2000);
				System.out.println("Site Medication: " + option.getText());

				if (option.getText().toLowerCase().contains(drugname.toLowerCase())
						|| option.getText().trim().contains(drugname.trim()) || option.getText().contains(drugname)
						|| option.getText().startsWith(drugname)) {

					// Find the parent row of the current option
					WebElement row = (WebElement) ((JavascriptExecutor) driver)
							.executeScript("return arguments[0].parentNode;", option);

					// Print the relative XPath of the row
					String relativeXPath = getRelativeXPath(driver, row);
					System.out.println("Relative XPath of the row: " + relativeXPath);
					// Modify the relative XPath to point to the last td (index + 3)
					String modifiedXPath = relativeXPath.replaceFirst("/td\\[\\d+\\]$", "/td[6]");
					// Find the button within the modifiedXPath
					WebElement approveButton = driver.findElement(By.xpath(modifiedXPath));
					System.out.println("Approve Button xpath: " + approveButton);
					// Click the 'Approve' button within the current row
					approveButton.click();
					medicationFound = true;
					FoundMedicationText = option.getText();

					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (!medicationFound) {
			inputdata = "\n" + "Administration Medication not Found" + "\n" + "Administration Round: " + AdminRound
					+ "\n" + "Location Name: " + location + "\n" + "Patient Name: " + resident + "\n"
					+ "Entered Medication [" + drugname + "] Not Found" + "\n";
			Task_Name = action;
			return;
		}

		WebElement usernameInput = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Username']")));
		usernameInput.click();
		usernameInput.clear();
		usernameInput.sendKeys(username);

		WebElement passwordInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='PIN/Password']")));
		passwordInput.click();
		passwordInput.sendKeys(pin);

		WebElement greenButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='green-button']")));
		greenButton.click();

		try {
			WebElement wrongCred = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//p[contains(text(), 'Invalid login or password.')]")));
			Thread.sleep(1000);

			String ErrorMessage = wrongCred.getText();
			System.out.println("ErrorMessage text: " + ErrorMessage);

			if ("Invalid login or password.".equals(ErrorMessage)) {
				inputdata = "\n" + ErrorMessage + "\n" + "  QA: Entered Credentials are Incorrect" + "\n";
				Task_Name = action;
				return;
			}

		} catch (Exception e) {

		}

		inputdata = "\n" + "Medication supplied to Patient Succesfully" + "\n" + "Administration Round: "
				+ clickedAdminRoundText + "\n" + "Location Name: " + clickedLocationText + "\n" + "Patient Name: "
				+ clickedResidentText + "\n" + "Medication Name: " + FoundMedicationText + "\n";
		Task_Name = action;

	}

	// Function to get the relative XPath of an element
	private static String getRelativeXPath(WebDriver driver, WebElement element) {
		return (String) ((JavascriptExecutor) driver).executeScript("function absoluteXPath(element) {"
				+ "var comp, comps = [];" + "var parent = null;" + "var xpath = '';"
				+ "var getPos = function(element) {" + "var position = 1, curNode;"
				+ "if (element.nodeType == Node.ATTRIBUTE_NODE) {" + "return null;" + "}"
				+ "for (curNode = element.previousSibling; curNode; curNode = curNode.previousSibling) {"
				+ "if (curNode.nodeName == element.nodeName) {" + "++position;" + "}" + "}" + "return position;" + "};"
				+ "if (element instanceof Document) {" + "return '/';" + "}"
				+ "for (; element && !(element instanceof Document); element = element.nodeType == Node.ATTRIBUTE_NODE ? element.ownerElement : element.parentNode) {"
				+ "comp = comps[comps.length] = {};" + "switch (element.nodeType) {" + "case Node.TEXT_NODE:"
				+ "comp.name = 'text()';" + "break;" + "case Node.ATTRIBUTE_NODE:"
				+ "comp.name = '@' + element.nodeName;" + "break;" + "case Node.PROCESSING_INSTRUCTION_NODE:"
				+ "comp.name = 'processing-instruction()';" + "break;" + "case Node.COMMENT_NODE:"
				+ "comp.name = 'comment()';" + "break;" + "case Node.ELEMENT_NODE:" + "comp.name = element.nodeName;"
				+ "break;" + "}" + "comp.position = getPos(element);" + "}"
				+ "for (var i = comps.length - 1; i >= 0; i--) {" + "comp = comps[i];"
				+ "xpath += '/' + comp.name.toLowerCase();" + "if (comp.position !== null) {"
				+ "xpath += '[' + comp.position + ']';" + "}" + "}" + "return xpath;" + "}"
				+ "return absoluteXPath(arguments[0]);", element);
	}

}