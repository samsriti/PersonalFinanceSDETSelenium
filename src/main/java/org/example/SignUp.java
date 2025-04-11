package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUp {
	
	WebDriver driver;
	
	public SignUp(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[@href='/signup']")
	WebElement signUpLink;
	
	@FindBy(xpath ="//h2[contains(text(), 'Sign Up')]")
	WebElement signUpTitle;
	
	@FindBy(xpath = "(//input[@type='text'])[1]")
	WebElement firstName;
	
	@FindBy(xpath = "(//input[@type='text'])[2]")
	WebElement lastName;
	
	@FindBy(xpath = "//input[@type='email']")
	WebElement emailID;
	
	@FindBy(xpath = "//input[@type='password']")
	WebElement password;
	
	@FindBy(xpath = "//button[@type='submit']")
	WebElement signUpButton;
	
	public void enterFirstName(String firstname) {
    	firstName.click();
    	firstName.clear();
        firstName.sendKeys(firstname);
    }

    public void enterLastName(String lastname) {
    	lastName.click();
    	lastName.clear();
        lastName.sendKeys(lastname);
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
    
    public void signUp() {
    	signUpButton.click();
    }
    
    public void navigateSignUP() {
    	signUpLink.click();
    }
    
    public String generateUniqueEmail() {
        long timestamp = System.currentTimeMillis(); // gets the current time in milliseconds
        return "user" + timestamp + "@testmail.com";
    }
    
    
	
	
	

}
