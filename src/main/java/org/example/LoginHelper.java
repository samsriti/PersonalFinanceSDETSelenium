package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginHelper {
    public static void login(WebDriver driver, String email, String password) {
        driver.get("https://fmi-eccp.onrender.com/login");
        driver.manage().window().maximize();
        Login loginPage = new Login(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Login')]")));

        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
        HandleAlert.handleAlert(driver);
    }
}
