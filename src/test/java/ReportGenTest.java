import java.io.IOException;
import java.time.Duration;

import org.example.BackendChecker;
import org.example.BudgetTracker;
import org.example.FronendChecker;
import org.example.LoginHelper;
import org.example.ReportGeneration;
import org.example.ScreenshotUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ReportGenTest {
	static WebDriver driver;
	static ExtentReports extent;
	static ExtentTest test;

	@BeforeAll
	public static void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ReportGenTestReport.html");
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
	public void reportGenerationPositive() throws IOException {
		System.out.println("Running test case because backend is up");
		test = extent.createTest("Report Generation Test");
		try {
			 // Login
	        String userEmail = "user1744513353974@testmail.com";
	        String password = "test@1234";
	        LoginHelper.login(driver, userEmail, password);
	        test.info("Logged in with user ID: " + userEmail);

	        // Navigate to Report Page
	        ReportGeneration generation = new ReportGeneration(driver);
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	        generation.clickReportNav();
	        test.info("Clicked on 'Report' in navigation bar");

	        WebElement reportTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//h2[contains(text(), 'Generate Report')]")));
	        test.pass("Navigated to Report Generation page");

	      
	        String year = "2025";
	        String month = "2"; 
	        generation.selectYear(year);
	        test.info("Selected year: " + year);
	        generation.selectMonthByValue(month);
	        test.info("Selected month: " + month);

	       
	        Thread.sleep(3000);
	        generation.clickGenerateReport();
	        test.pass("Clicked on 'Generate Report' button");

	        // Success log
	        test.pass("Report generation completed successfully for " + year + "-" + month);
			
			

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
