package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {

	WebDriver driver;

    public Login(WebDriver driver) {
		// TODO Auto-generated constructor stub
    	this.driver = driver;
        PageFactory.initElements(driver, this);
	}
   

    @FindBy(xpath = "//input[@type='email']")
    WebElement emailID;

    @FindBy(xpath = "//input[@type='password']")
    WebElement password;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement loginButton;
    
    @FindBy(xpath ="//h2[contains(text(), 'Login')]")
    WebElement loginMsg;
    
    @FindBy(xpath = "//h1[contains(text(), 'Personal Finance Manager')]")
    WebElement homePageMsg;
    
    public String getTitle() {
    	String res = loginMsg.getText();
    	if (res.contains("Login")) {
    		System.out.println("Login Page!");
    	}
    	return res;
    }

    public void enterEmail(String email) {
    	emailID.click();
    	emailID.clear();
        emailID.sendKeys(email);
    }

    public void enterPassword(String pass) {
    	password.click();
    	password.clear();
        password.sendKeys(pass);
    }

    public void clickLogin() {
        loginButton.click();
    }
    
    public String homePage() {
    	String response = homePageMsg.getText();
    	return response;
    }
    
    
}