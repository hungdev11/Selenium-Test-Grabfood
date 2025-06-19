package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.UUID;

public class RegisterPage {
    WebDriver driver;
    WebDriverWait wait;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void openRegisterForm() {
        driver.get("http://localhost:3000"); // hoặc URL trang chính

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Login')]"))).click();
        wait.until(ExpectedConditions.urlContains("/login"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Register')]"))).click();
    }

    public void registerWithData(String name, String email, String phone, String password, String confirmPassword) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).clear();
        driver.findElement(By.id("name")).sendKeys(name);

        driver.findElement(By.id("registerEmail")).clear();
        driver.findElement(By.id("registerEmail")).sendKeys(email);

        driver.findElement(By.id("phone")).clear();
        driver.findElement(By.id("phone")).sendKeys(phone);

        driver.findElement(By.id("registerPassword")).clear();
        driver.findElement(By.id("registerPassword")).sendKeys(password);

        driver.findElement(By.id("confirmPassword")).clear();
        driver.findElement(By.id("confirmPassword")).sendKeys(confirmPassword);


        driver.findElement(By.xpath("//button[contains(text(),'Create account')]")).click();
        sleep(1);
    }

    public boolean isAlertWithTextDisplayed(String expectedText) {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            System.out.println("Alert text: " + alertText);
            boolean match = alertText.contains(expectedText);
            alert.accept(); // dismiss alert to continue testing
            return match;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String generateRandomEmail() {
        return "user" + UUID.randomUUID() + "@gmail.com";
    }

    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
