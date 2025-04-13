import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.time.Duration;

import org.example.BackendChecker;
import org.example.FronendChecker;
import org.example.HandleAlert;
import org.example.Login;
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

public class LoginTest {

	static WebDriver driver;
	static ExtentReports extent;
	static ExtentTest test;

	@BeforeAll
	public static void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		ExtentSparkReporter spark = new ExtentSparkReporter("test-output/LoginTestReport.html");
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
	public static void loginTestPositive() throws IOException {
		System.out.println("Running test case because backend is up");
		test = extent.createTest("Login Test");
		try {
			driver.get("https://fmi-eccp.onrender.com/login");

			Login loginPage = new Login(driver);
			WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(300));
			WebElement element = driverWait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Login')]")));
			String title = loginPage.getTitle();
			test.pass(title);
			loginPage.enterEmail("samsriti111@gmail.com");
			test.info("Entered email!");
			loginPage.enterPassword("test@1234");
			test.info("Entered password!");
			loginPage.clickLogin();
			test.info("Logged in");
			String handleALertMsg = HandleAlert.handleAlert(driver);
			test.info("Handled alert:" + handleALertMsg);
			if (loginPage.homePage().contains("Personal Finance Manager")) {
				test.pass("Logged in successfully!");
			}
		} catch (Exception e) {
			String screenshotPath = ScreenshotUtil.captureScreenshot(driver, "signUpPositiveTest_Failure");
			test.fail("Tst failed due to exception: " + e.getMessage());
			test.addScreenCaptureFromPath(screenshotPath);
			e.printStackTrace();
		}

	}

	@Test
	public void loginTestNegative() throws IOException {
		System.out.println("Running test case because backend is up");
		test = extent.createTest("Incorrect Login Test");

		try {
			driver.get("https://fmi-eccp.onrender.com/login");

			Login loginPage = new Login(driver);
			WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(300));
			WebElement element = driverWait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Login')]")));
			String title = loginPage.getTitle();
			test.pass(title);
			loginPage.enterEmail("samsriti111@gmail.com");
			test.info("Entered email!");
			loginPage.enterPassword("blablabla");
			test.info("Entered password!");
			loginPage.clickLogin();
			String handleAlertText = HandleAlert.handleAlert(driver);
			test.info("Handled incorrect login credentials: " + handleAlertText);

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
