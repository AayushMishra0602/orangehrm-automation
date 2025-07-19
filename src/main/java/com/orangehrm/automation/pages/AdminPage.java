package com.orangehrm.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class AdminPage {
    WebDriver driver;
    WebDriverWait wait;

    // Constructor
    public AdminPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
    By adminMenuOptions = By.xpath("//nav[@aria-label='Main menu']//li//span"); // Left menu options
    By usernameField = By.xpath("//label[text()='Username']/../following-sibling::div/input");
    By searchButton = By.xpath("//button[@type='submit']");
    By resetButton = By.xpath("//button[normalize-space()='Reset']");
    By userRoleDropdown = By.xpath("//label[text()='User Role']/../following-sibling::div//i");
    By userRoleOptions = By.xpath("//div[@role='listbox']//span");
    By userStatusDropdown = By.xpath("//label[text()='Status']/../following-sibling::div//i");
    By userStatusOptions = By.xpath("//div[@role='listbox']//span");
    By resultsTableRows = By.xpath("//div[@class='oxd-table-body']/div"); // Rows in results

    // Actions
    public int getAdminMenuOptionsCount() {
        List<WebElement> menuOptions = driver.findElements(adminMenuOptions);
        System.out.println("🔵 Admin Left Menu Options:");
        for (WebElement option : menuOptions) {
            System.out.println("• " + option.getText());
        }
        return menuOptions.size();
    }

    public void searchByUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(searchButton).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(resultsTableRows));
        int totalRecords = driver.findElements(resultsTableRows).size();
        System.out.println("✅ Total records found for username '" + username + "': " + totalRecords);
        driver.findElement(resetButton).click(); // Reset for next search
    }

    public void searchByUserRole(String role) {
        driver.findElement(userRoleDropdown).click();
        List<WebElement> roles = driver.findElements(userRoleOptions);
        for (WebElement r : roles) {
            if (r.getText().equalsIgnoreCase(role)) {
                r.click();
                break;
            }
        }
        driver.findElement(searchButton).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(resultsTableRows));
        int totalRecords = driver.findElements(resultsTableRows).size();
        System.out.println("✅ Total records found for role '" + role + "': " + totalRecords);
        driver.findElement(resetButton).click();
    }

    public void searchByUserStatus(String status) {
        driver.findElement(userStatusDropdown).click();
        List<WebElement> statuses = driver.findElements(userStatusOptions);
        for (WebElement s : statuses) {
            if (s.getText().equalsIgnoreCase(status)) {
                s.click();
                break;
            }
        }
        driver.findElement(searchButton).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(resultsTableRows));
        int totalRecords = driver.findElements(resultsTableRows).size();
        System.out.println("✅ Total records found for status '" + status + "': " + totalRecords);
        driver.findElement(resetButton).click();
    }
}
