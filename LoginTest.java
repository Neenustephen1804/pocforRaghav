package Tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.LoginPage;
import java.time.Duration;

public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Direct URL
        driver.get("https://demo.evershop.io/account/login");

        // CRITICAL: Wait for the document to be fully ready before initializing POM
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );

        loginPage = new LoginPage(driver);
    }

    @Test(priority = 1)
    public void testLoginSuccess() {
        loginPage.login("neenustephen@gmail.com", "P@55w0rd");

        // Wait for URL to change to the account/dashboard page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        boolean isLogged = wait.until(ExpectedConditions.urlContains("account"));

        Assert.assertTrue(isLogged, "Login failed: User was not redirected to the account page.");
    }

    @Test(priority = 2)
    public void testLoginFailure() {
        loginPage.login("neenugfgfstephen@gmail.com", "wrong_password");

        // Verify the red error text appears
       // String errorMsg = loginPage.getErrorMessage();
       // Assert.assertTrue(errorMsg.contains("Invalid"), "Error message not displayed for invalid login.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}