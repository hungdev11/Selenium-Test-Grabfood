package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    WebDriver driver;
    WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public void verifyQuantity(String foodName, int expectedQty) {
        driver.findElement(By.id("cart-button")).click();
        WebElement qty = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[h4[text()='"+ foodName +"']]//span[text()='" + expectedQty + "']")));
        if (!qty.getText().equals(String.valueOf(expectedQty))) {
            throw new AssertionError("Expected quantity: " + expectedQty + " but found: " + qty.getText());
        }
    }

    public void checkout() {
        driver.findElement(By.id("cart-button")).click();
        driver.findElement(By.id("checkout-button")).click();
    }
}
