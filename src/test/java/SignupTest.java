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
import org.example.SignUp;
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

public class SignupTest {

	static WebDriver driver;
	static ExtentReports extent;
	static ExtentTest test;

	@BeforeAll
	public static void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		ExtentSparkReporter spark = new ExtentSparkReporter("test-output/SignupTestReport.html");
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
	public void signUpPositiveTest() throws IOException {

		test = extent.createTest("Sign Up Positive Test Case");
		try {
			driver.get("https://fmi-eccp.onrender.com/login");
			WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(300));
			WebElement element = driverWait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Login')]")));
			test.info("UI rendered");

			// Navigate
			SignUp signupPage = new SignUp(driver);
			signupPage.navigateSignUP();
			WebDriverWait driverWait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
			WebElement element1 = driverWait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Sign Up')]")));
			test.pass("Navigated to Sign Up Page!");

			// Enter Credentials
			signupPage.enterFirstName("Test");
			test.info("entered first name");
			signupPage.enterLastName("User");
			test.info("entered last name");
			signupPage.enterEmail(signupPage.generateUniqueEmail());
			test.info("generated and entered unique name");
			signupPage.enterPassword("test@1234");
			test.info("entered password");
			signupPage.signUp();
			test.info("clicked on sign up");

			// Handle Alert
			String handleAlertText = HandleAlert.handleAlert(driver);
			test.info("Handled sign up: " + handleAlertText);
			test.pass("Signed up Successfully!");
		} catch (Exception e) {
			String screenshotPath = ScreenshotUtil.captureScreenshot(driver, "signUpPositiveTest_Failure");
			test.fail("Tst failed due to exception: " + e.getMessage());
			test.addScreenCaptureFromPath(screenshotPath);
			e.printStackTrace();
		}
	}

	@Test
	public void signUpDuplicateEmailTest() throws IOException {

		test = extent.createTest("Sign Up Exiting Email Test Case");
		try {
			driver.get("https://fmi-eccp.onrender.com/login");
			WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(300));
			WebElement element = driverWait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Login')]")));
			test.info("UI rendered");

			// Navigate
			SignUp signupPage = new SignUp(driver);
			signupPage.navigateSignUP();
			WebDriverWait driverWait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
			WebElement element1 = driverWait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Sign Up')]")));
			test.pass("Navigated to Sign Up Page!");

			// Enter Credentials
			signupPage.enterFirstName("Test");
			test.info("entered first name");
			signupPage.enterLastName("User");
			test.info("entered last name");
			signupPage.enterEmail("samsriti111@gmail.com");
			test.info("used exisiting email ID to verify that sign up is not successful");
			signupPage.enterPassword("test@1234");
			test.info("entered password");
			signupPage.signUp();
			test.info("clicked on sign up");

			// Handle Alert
			String handleAlertText = HandleAlert.handleAlert(driver);
			test.info("Handled duplicate email: " + handleAlertText);
			test.pass("Signed up Failed!!");
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
