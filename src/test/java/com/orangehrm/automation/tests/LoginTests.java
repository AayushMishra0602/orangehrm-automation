package com.orangehrm.automation.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.orangehrm.automation.base.BaseTest;
import com.orangehrm.automation.pages.LoginPage;
import com.orangehrm.automation.utils.ExcelUtils;
import com.orangehrm.automation.utils.ScreenshotUtils;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, String expectedResult) {
        ExtentTest logger = extent.createTest("Login Test - User: " + username);
        try {
            System.out.println(" Starting login test for: " + username);

            LoginPage loginPage = new LoginPage(driver);

            // Perform login
            loginPage.login(username, password);
            logger.log(Status.INFO, "Attempted login with username: " + username);

            // Take a screenshot after login attempt
            String screenshotPath = ScreenshotUtils.takeScreenshot(driver, username + "_login");
            logger.addScreenCaptureFromPath(screenshotPath);

            if (expectedResult.equalsIgnoreCase("Pass")) {
                // Check for successful login
                Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"),
                        "Login failed for valid credentials: " + username);
                logger.log(Status.PASS, "Login successful for user: " + username);

                // Perform logout
                loginPage.logout();
                logger.log(Status.INFO, "Logout performed for user: " + username);

            } else {
                // Check for invalid login error
                Assert.assertTrue(driver.getPageSource().contains("Invalid credentials"),
                        "Unexpected success for invalid credentials: " + username);
                logger.log(Status.PASS, "Invalid login attempt correctly handled for user: " + username);
            }

        } catch (AssertionError ae) {
            logger.log(Status.FAIL, "Assertion Failed: " + ae.getMessage());
            String failScreenshot = ScreenshotUtils.takeScreenshot(driver, username + "_failure");
            logger.addScreenCaptureFromPath(failScreenshot);
            throw ae;
        } catch (Exception e) {
            logger.log(Status.FAIL, "Test Failed: " + e.getMessage());
            String errorScreenshot = ScreenshotUtils.takeScreenshot(driver, username + "_error");
            logger.addScreenCaptureFromPath(errorScreenshot);
            throw e;
        }
    }

   
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        String excelPath = "src/test/resources/loginData.xlsx";
        return ExcelUtils.readExcelData(excelPath, "Sheet1");
    }

    }
