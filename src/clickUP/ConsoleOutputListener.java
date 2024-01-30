package clickUP;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.IOException;

public class ConsoleOutputListener extends createTask implements ITestListener {
	public void onTestFailure(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String consoleError = extractConsoleError(result);
		String status = "fail";
		String listId = "";
		try {
			createClickUpTask(methodName, consoleError, listId, status);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String extractConsoleError(ITestResult result) {
		String consoleOutput = "";
		Throwable throwable = result.getThrowable();
		if (throwable != null) {
			consoleOutput = throwable.getMessage();
		}
		return consoleOutput;
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String consoleError = extractConsoleError(result);
		String status = "pass";
		String listId = "";
		try {
			createClickUpTask(methodName, consoleError, listId, status);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Other methods of ITestListener (not implemented for brevity)
	// You can choose to implement them if needed
	// ...
	@Override
	public void onStart(ITestContext context) {
		// Initialization or additional logic before all tests start
	}

	@Override
	public void onFinish(ITestContext context) {
		// Cleanup or additional logic after all tests have finished
	}
}