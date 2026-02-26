package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    // Use CSS Selectors - more reliable for React apps like EverShop
    private By emailField = By.cssSelector("input[name='email']");
    private By passwordField = By.cssSelector("input[name='password']");
    private By loginButton = By.xpath("//div[contains(@class, 'form-submit-button')]/button[normalize-space(.)='Sign In']");
    private By errorAlert = By.cssSelector(".text-critical");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void login(String email, String password) {
        // 1. Enter Email (Assuming this part is working)
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        emailInput.clear();
        emailInput.sendKeys(email);

        // 2. Enter Password
        driver.findElement(By.name("password")).sendKeys(password);

        // 3. FIX: Use a robust locator for the button
        By loginBtnLocator = By.xpath("//div[contains(@class, 'form-submit-button')]/button[normalize-space(.)='Sign In']");

        // 4. WAIT for the button to be present and then Scroll/Click
        try {
            WebElement loginBtn = wait.until(ExpectedConditions.presenceOfElementLocated(loginBtnLocator));

            // Scroll to the button to make sure it's not hidden
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginBtn);

            // Use JavaScript to click - it's the most reliable way for this demo site
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtn);
            System.out.println("Login button clicked via JavaScript.");

        } catch (Exception e) {
            System.err.println("Could not hit the login button: " + e.getMessage());
            throw e; // Rethrow to fail the test clearly
        }
    }

  //  public String getErrorMessage() {
    //    return wait.<WebElement>until(ExpectedConditions.visibilityOfElementLocated(errorAlert)).getText();
   // }
}