package org.example;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BackendChecker {

    public static boolean isBackendRunning(){
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        //driver.manage().window().maximize();
        boolean isRunning = false;
        try{
            driver.get("https://personalfinsys.onrender.com/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/pre")));

            String response = element.getText();
            if (response.contains("\"message\":\"API is running\"")) {
                System.out.println("backend is running");
                isRunning = true;

            } else {
                System.out.println("backend is not running" + response);
            }

        }catch(Exception e)
        {
            System.out.println("Error while checking backed");
            e.printStackTrace();
        }finally {
            driver.quit();
        }
        return isRunning;
    }
}
