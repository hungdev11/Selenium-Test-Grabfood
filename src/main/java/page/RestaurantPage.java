package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RestaurantPage {
    WebDriver driver;
    WebDriverWait wait;

    public RestaurantPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public void addFoodToCart(String foodName, int quantity) {
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h3[normalize-space(text())='" + foodName + "']")));
        WebElement plusBtn = driver.findElement(By.xpath("//button[contains(@class,'rounded-full') and text()='+']"));
        for (int i = 0; i < quantity; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(plusBtn)).click();
        }
        driver.findElement(By.xpath("//button[contains(text(),'Add to basket')]")).click();
    }
}