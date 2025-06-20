package page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class RestaurantPage {
    WebDriver driver;
    WebDriverWait wait;
    private static final Logger logger = LoggerHelper.getLogger();

    public RestaurantPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public void addFoodToCart(String foodName, int quantity) {
        logger.info("Adding "+ foodName +" with quantity "+ quantity +" to cart...");
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h3[normalize-space(text())='" + foodName + "']")));
        WebElement plusBtn = driver.findElement(By.xpath("//button[contains(@class,'rounded-full') and text()='+']"));
        for (int i = 0; i < quantity; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(plusBtn)).click();
        }
        driver.findElement(By.xpath("//button[contains(text(),'Add to basket')]")).click();
    }

    public boolean isRestaurantHadFood() {
        try {
            List<WebElement> foodItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector("div.flex.items-center.p-4.border")));
            return !foodItems.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public void addRandomFood() {
        List<WebElement> foodItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("div.flex.items-center.p-4.border")));

        if (foodItems.isEmpty()) {
            throw new IllegalStateException("No food items found.");
        }

        WebElement randomFood = foodItems.get(new Random().nextInt(foodItems.size()));

        WebElement addButton = randomFood.findElement(By.xpath(".//button[contains(text(),'+')]"));

        // Scroll đúng vào nút +
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", addButton);

        // Optional: chờ thêm chút để header không che
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Click bằng JavaScript để tránh bị intercept
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addButton);
        WebElement plusBtn = driver.findElement(By.xpath("//button[contains(@class,'rounded-full') and text()='+']"));
        for (int i = 0; i<= 3 ; i++) {
            increase();
        }
        decrease();
        driver.findElement(By.xpath("//button[contains(text(),'Add to basket')]")).click();
    }

    public void increase() {
        WebElement plusButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'flex') and contains(@class,'gap-2')]//button[text()='+']")));
        plusButton.click();
        Util.sleep(1);
    }

    public void decrease() {
        WebElement minusButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'flex') and contains(@class,'gap-2')]//button[text()='−']")));
        minusButton.click();
        Util.sleep(1);
    }
}