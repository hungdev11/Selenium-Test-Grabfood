package page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[contains(text(),'Urban Flavor')]"))).click();
        wait.until(ExpectedConditions.urlToBe("http://localhost:3000/restaurant/7?lat=-1&lon=-1"));
    }

    public void selectRestaurantByName(String restaurantName) {
        WebElement restaurant = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h3[text()='" + restaurantName + "']/ancestor::div[contains(@class,'cursor-pointer')]")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", restaurant);
        wait.until(ExpectedConditions.elementToBeClickable(restaurant)).click();
    }
}