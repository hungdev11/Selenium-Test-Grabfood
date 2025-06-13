package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

public class RegisterPage {
    WebDriver driver;
    WebDriverWait wait;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public void registerRandomUser() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Login')]"))).click();
        wait.until(ExpectedConditions.urlToBe("http://localhost:3000/login"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Register')]"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys("Ransomeware");
        String randomEmail = "ransomeware" + UUID.randomUUID() + "@gmail.com";
        driver.findElement(By.id("registerEmail")).sendKeys(randomEmail);
        driver.findElement(By.id("phone")).sendKeys("011223344");
        driver.findElement(By.id("registerPassword")).sendKeys("Ransomeware");
        driver.findElement(By.id("confirmPassword")).sendKeys("Ransomeware");
        driver.findElement(By.xpath("//button[contains(text(),'Create account')]"))
                .click();

        Util.ignoreAlert(driver);
    }
}
