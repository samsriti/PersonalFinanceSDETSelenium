package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ReportGeneration {
	
	WebDriver driver;

    public  ReportGeneration(WebDriver driver) {
    	// TODO Auto-generated constructor stub
    	this.driver = driver;
        PageFactory.initElements(driver, this);
        
	} 
    
    @FindBy(xpath = "//a[@href='/report']")
    WebElement reportNavBar;
    
    @FindBy(xpath="//select[@id='year-select']")
    WebElement selectYear;
  
    @FindBy(xpath="//select[@id='month-select']")
    WebElement monthDropdown;
    
    @FindBy(xpath = "//button[@class='generate-report']")
    WebElement generateReport;
    
    
    public void clickReportNav() {
        reportNavBar.click();
    }

    public void selectYear(String yearValue) {
    	Select select = new Select(selectYear);
        select.selectByValue(yearValue);
    }

    public void selectMonthByValue(String monthValue) {
        Select select = new Select(monthDropdown);
        select.selectByValue(monthValue); // e.g., "4" for April
    }

    public void clickGenerateReport() {
        generateReport.click();
    }


}
