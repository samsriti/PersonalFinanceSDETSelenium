package org.example;

import java.time.Duration;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HandleAlert {

    WebDriver driver;



    public static String handleAlert(WebDriver driver) {
        try {
        	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
            wait.until(ExpectedConditions.alertIsPresent());

            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            System.out.println("Alert appeared: " + alertText);
            alert.accept();
            System.out.println("Alert accepted.");
            return alertText;
            
        } catch (Exception e) {
            System.out.println("No alert present or error handling alert.");
            e.printStackTrace();
            return "Error check exception";
        }
        
    }


}
