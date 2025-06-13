package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public void selectFirstRestaurant() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[contains(text(),'0Urban Flavor')]"))).click();
        wait.until(ExpectedConditions.urlToBe("http://localhost:3000/restaurant/1?lat=-1&lon=-1"));
    }
}