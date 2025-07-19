package com.orangehrm.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    WebDriver driver;

    // Locators
    By usernameField = By.name("username");
    By passwordField = By.name("password");
    By loginButton = By.xpath("//button[@type='submit']");
    By welcomeMenu = By.xpath("//p[@class='oxd-userdropdown-name']");         // Dashboard element after successful login
    By errorMessage = By.linkText("Logout");    // Error message on failed login

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Login method
    public void login(String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for username field to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));

        // Enter username and password
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);

        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);

        // Click Login button
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginBtn.click();

        // Wait for either Dashboard or error message
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMenu));
            System.out.println(" Login successful for user: " + username);
        } catch (Exception e) {
            if (driver.findElements(errorMessage).size() > 0) {
                System.out.println(" Login failed for user: " + username + " - Invalid credentials");
            } else {
                System.out.println(" Login failed for user: " + username + " - Unknown reason");
            }
        }
    }

    // Logout method (added here for simplicity)
    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click Welcome menu
        wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMenu));
        driver.findElement(welcomeMenu).click();

        // Click Logout link
        By logoutLink = By.linkText("Logout");
        wait.until(ExpectedConditions.visibilityOfElementLocated(logoutLink));
        driver.findElement(logoutLink).click();

        // Wait for Login page to reappear
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton));

        System.out.println(" Logout successful");
    }
}
