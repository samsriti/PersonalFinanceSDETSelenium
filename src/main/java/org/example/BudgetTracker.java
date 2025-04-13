package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BudgetTracker {

	WebDriver driver;

	public BudgetTracker(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[@class='add-button']")
	WebElement addButton;

	@FindBy(xpath = "//input[@type='date']")
	WebElement date;

	@FindBy(xpath = "//input[@type='number']")
	WebElement incomeNumber;

	@FindBy(xpath = "//button[contains(text(), 'Remove Expense')]")
	WebElement removeExpense;

	@FindBy(xpath = "//input[@type='month']")
	WebElement selectMonth;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement saveEntries;

	@FindBy(xpath = "//p[contains(text(),'Date')]")
	WebElement extractDate;

	@FindBy(xpath = "(//p[contains(text(),'Income')])[2]")
	WebElement extractIncome;

	@FindBy(xpath = "//input[@id='category-0']")
	WebElement category;

	@FindBy(xpath = "//input[@id='amount-0']")
	WebElement expenseAmt;
	
	@FindBy(xpath = "//li[1]")
	WebElement extractExpenseAmt;

	public void addButton() {
		addButton.click();
	}

	public void addDate(String dates) {
		date.click();
		date.clear();
		date.sendKeys(dates);
	}

	public void incomeAdd(int num) {
		incomeNumber.click();
		incomeNumber.clear();
		incomeNumber.sendKeys(String.valueOf(num));
	}

	public void removeExpense() {
		removeExpense.click();
	}

	public void saveEntries() {
		saveEntries.click();
	}

	public void displayMonthData(String month) {
		selectMonth.sendKeys(month);

	}

	public String extractDate() {
		String fullDate = extractDate.getText();
		System.out.println("fullDate" + fullDate);
		String date = fullDate.replace("Date:", "").trim();
		System.out.println(date);
		return date;
	}

	public String extractIncome() {

		String fullIncome = extractIncome.getText();
		System.out.println("fullIncome: " + fullIncome);
		String extractedIncome = fullIncome.replace("Income: $", "").trim();
		System.out.println("extracted income: " + extractedIncome);
		return extractedIncome;
	}

	public void addCategory(String categoryExpense) {
		category.click();
		category.clear();
		category.sendKeys(categoryExpense);

	}

	public void addExpenseAmt(int amt) {
		expenseAmt.click();
		expenseAmt.clear();
		expenseAmt.sendKeys(String.valueOf(amt));
	}

	public String extractExpense() {

		String fullExtractedAmt = extractExpenseAmt.getText();
		System.out.println("Full string: " + fullExtractedAmt);

	    // Remove everything up to the dollar sign
	    String extracted = fullExtractedAmt.replaceAll(".*\\$", "").trim();

	    System.out.println("Extracted amount: " + extracted);
	    return extracted;
	
	}

}
