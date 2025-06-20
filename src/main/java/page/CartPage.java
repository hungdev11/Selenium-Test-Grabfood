package page;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

public class CartPage {
    WebDriver driver;
    WebDriverWait wait;
    private static final Logger logger = LoggerHelper.getLogger();

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
        //driver.findElement(By.id("cart-button")).click();
        driver.findElement(By.id("checkout-button")).click();
    }

    public void checkoutAfterVerify()  {
        logger.info("Clicking cart button...");
        WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("cart-button")));
        cartButton.click();
        logger.info("Clicking checkout button...");
        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout-button")));
        checkoutBtn.click();
    }

    public boolean isCartEmpty() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Start Grabbing Food!')]")
            )).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public int getCartItemCount() {
        List<WebElement> items = driver.findElements(By.xpath("//div[contains(@class, 'items-center') and contains(@class, 'border-b')]"));
        return items.size();
    }

    public int getFirstItemQuantity() {
        WebElement quantitySpan = driver.findElement(By.xpath("(//span[@class='text-sm'])[1]"));
        return Integer.parseInt(quantitySpan.getText().trim());
    }

    public void increaseFirstItemQuantity() {
        List<WebElement> buttons = driver.findElements(By.xpath("//button[text()='+']"));
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
        }
        Util.sleep(2);
    }

    public void decreaseFirstItemQuantity() {
        List<WebElement> buttons = driver.findElements(By.xpath("//button[text()='-']"));
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
        }
        Util.sleep(2);
    }

    public void removeFirstItem() {
        List<WebElement> removeButtons = driver.findElements(By.xpath("//button[contains(text(),'Xóa')]"));
        if (!removeButtons.isEmpty()) {
            removeButtons.get(0).click();
        }
        Util.sleep(1);
    }

    public void removeAllItem() {
        List<WebElement> removeButtons = driver.findElements(By.xpath("//button[contains(text(),'Xóa')]"));
        for (WebElement remove: removeButtons) {
            remove.click();
        }
    }

    public void clickCart() {
        driver.findElement(By.id("cart-button")).click();
    }
}
