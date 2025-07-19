package com.orangehrm.automation.tests;

import com.orangehrm.automation.base.BaseTest;
import com.orangehrm.automation.pages.LoginPage;
import com.orangehrm.automation.pages.AdminPage;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminTests extends BaseTest {

    @Test(priority = 1)
    public void verifyAdminMenuOptionsCount() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        AdminPage adminPage = new AdminPage(driver);

        // Navigate to Admin module
        driver.findElement(By.linkText("Admin")).click();

        int count = adminPage.getAdminMenuOptionsCount();
        Assert.assertEquals(count, 12, "❌ Expected 12 menu options but found " + count);
        System.out.println("✅ Total menu options in Admin left menu: " + count);

        loginPage.logout();
    }

    @Test(priority = 2)
    public void searchEmployeeByUsername() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        AdminPage adminPage = new AdminPage(driver);
        driver.findElement(By.linkText("Admin")).click();

        adminPage.searchByUsername("Admin"); // Change username as needed
        loginPage.logout();
    }

    @Test(priority = 3)
    public void searchEmployeeByUserRole() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        AdminPage adminPage = new AdminPage(driver);
        driver.findElement(By.linkText("Admin")).click();

        adminPage.searchByUserRole("Admin"); // Change role as needed
        loginPage.logout();
    }

    @Test(priority = 4)
    public void searchEmployeeByUserStatus() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        AdminPage adminPage = new AdminPage(driver);
        driver.findElement(By.linkText("Admin")).click();

        adminPage.searchByUserStatus("Enabled"); // Or use "Disabled"
        loginPage.logout();
    }
}
