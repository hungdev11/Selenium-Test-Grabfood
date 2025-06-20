package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;
    private static final Logger logger = LoggerHelper.getLogger();

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public void login(String username, String password) {
        logger.info("Login with username " + username + " and password " + password);
        driver.get("http://localhost:3000/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Log in')]"))).click();
        Util.ignoreAlert(driver);
        //wait.until(ExpectedConditions.urlToBe("http://localhost:3000/"));
    }

    public boolean isLoginSuccessful() {
        return wait.until(ExpectedConditions.urlToBe("http://localhost:3000/"));
    }

    public boolean isLoginFailed() {
        return driver.getCurrentUrl().equals("http://localhost:3000/login");
    }
}