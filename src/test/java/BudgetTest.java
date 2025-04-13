import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.mongodb.internal.connection.Time;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.time.Duration;

import org.example.BackendChecker;
import org.example.BudgetTracker;
import org.example.FronendChecker;
import org.example.HandleAlert;
import org.example.Login;
import org.example.LoginHelper;
import org.example.ScreenshotUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BudgetTest {

	static WebDriver driver;
	static ExtentReports extent;
	static ExtentTest test;

	@BeforeAll
	public static void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		ExtentSparkReporter spark = new ExtentSparkReporter("test-output/BudgetTestReport.html");
		extent = new ExtentReports();
		extent.attachReporter(spark);
	}

	@Test
	public void techStackTest() {
		test = extent.createTest("Stacks Test");
		Assumptions.assumeTrue(BackendChecker.isBackendRunning(), "Backend is not running. Skipping tests.");
		Assumptions.assumeTrue(FronendChecker.isFrontendRunning(), "Frontend is not running. Skipping tests.");
		test.pass("Tech Stacks running!");
	}

	@Test
	public void incomeTracker() throws IOException {
		System.out.println("Running test case because backend is up");
		test = extent.createTest("Income Tracker Test");
		try {
			 // Credentials
		    String email = "user1744513421032@testmail.com";
		    String password = "test@1234";

		    test.info("Using unique generated email/password");
		    LoginHelper.login(driver, email, password);
		    test.info("Logged in with user ID: " + email);

		   
		    BudgetTracker budgetTracker = new BudgetTracker(driver);
		    budgetTracker.addButton();
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		    wait.until(ExpectedConditions.visibilityOfElementLocated(
		            By.xpath("//h2[contains(text(), 'Add New Entry')]")));
		    test.info("Opened 'Add New Entry' form");

		    // Add entry
		    String date = "03-11-2025";
		    int incomeAmount = 1250;

		    budgetTracker.addDate(date);
		    test.info("Date added for income tracking: " + date);
		    Thread.sleep(1000);

		    budgetTracker.incomeAdd(incomeAmount);
		    test.info("Income amount entered: " + incomeAmount);

		    budgetTracker.removeExpense();
		    test.info("Removed default expense entry");

		    budgetTracker.saveEntries();
		    test.info("Clicked on 'Save Entries'");

		    Thread.sleep(2000);
		    // View data
		    String displayMonth = "03-2025";
		    budgetTracker.displayMonthData(displayMonth);
		    test.info("Displayed data for month: " + displayMonth);
		    Thread.sleep(3000);

		    // Validate date
		    String extractedDate = budgetTracker.extractDate();
		    test.info("Extracted date from UI: " + extractedDate);

		    
		    if (extractedDate.contains("2025-03-11")) {
		        test.pass("Added date successfully reflected in UI: " + extractedDate);
		    } else {
		        test.fail("Date has not been reflected correctly!");
		    }

		    // Validate income
		    String extractedIncome = budgetTracker.extractIncome();
		    test.info("Extracted income from UI: " + extractedIncome);

		    if (extractedIncome.contains("1250")) {
		        test.pass("Income successfully reflected in UI: " + extractedIncome);
		    } else {
		        test.fail("Income has not been reflected in the UI!");
		    }

		    test.pass("Income tracked and verified successfully!");

		} catch (Exception e) {
			String screenshotPath = ScreenshotUtil.captureScreenshot(driver, "signUpPositiveTest_Failure");
			test.fail("Test failed due to exception: " + e.getMessage());
			test.addScreenCaptureFromPath(screenshotPath);
			e.printStackTrace();
		}

	}

	@Test
	public void expenseTracker() throws IOException {
		System.out.println("Running test case because backend is up");
		test = extent.createTest("Expense Tracker Test");

		try {
			String email = "user1744513353974@testmail.com";
			String password = "test@1234";
			test.info("Using the unique generated email/password!");
			LoginHelper.login(driver, email, password);
			test.info("Logged in with user ID: " + email);

			BudgetTracker budgetTracker = new BudgetTracker(driver);
			budgetTracker.addButton();
			WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(30));
			WebElement element = driverWait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Add New Entry')]")));
			test.info("Opened 'Add New Entry' form");

			String date = "02-11-2025";
			budgetTracker.addDate(date);
			test.info("Date entered for income: " + date);
			Thread.sleep(1000);
			budgetTracker.incomeAdd(0);
			Thread.sleep(2000);

			String category = "Amazon";
			int expenseAmt = 200;
			budgetTracker.addCategory(category);
			test.info("Entered category: " + category);

			budgetTracker.addExpenseAmt(expenseAmt);
			test.info("Entered expense amount: " + expenseAmt);
			budgetTracker.saveEntries();
			test.info("Clicked on 'Save Entries'");
			Thread.sleep(2000);
			
			String displayMonth = "02-2025";
		    budgetTracker.displayMonthData(displayMonth);
		    test.info("Displayed data for month: " + displayMonth);
		    Thread.sleep(2000);
		    String extractedDate = budgetTracker.extractDate();
		    test.info("Extracted date from UI: " + extractedDate);
			if (extractedDate.contains("2025-02-11")) {
				test.pass("Addded date successfully reflected in UI: " + extractedDate);

			} else {
				test.fail("Date has not been reflected!");
			}

			String extractedExpense = budgetTracker.extractExpense();
		    test.info("Extracted expense from UI: " + extractedExpense);
		    
			if (extractedExpense.contains("200")) {
				test.pass("Expense successfully reflected in UI: " + extractedExpense);
				test.pass("Expense tracked successfully!");

			} else {
				test.fail("Expense has not been reflected!");
				test.fail("Expense tracking failed!");
			}

		} catch (Exception e) {
			String screenshotPath = ScreenshotUtil.captureScreenshot(driver, "signUpPositiveTest_Failure");
			test.fail("Tst failed due to exception: " + e.getMessage());
			test.addScreenCaptureFromPath(screenshotPath);
			e.printStackTrace();
		}
	}

	@AfterAll
	public static void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		if (extent != null) {
			extent.flush();
		}
	}

}
