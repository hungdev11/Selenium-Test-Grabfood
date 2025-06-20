import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.*;

import java.time.Duration;

public class CartTest {
    static WebDriver driver;
    static LoginPage loginPage;
    static WebDriverWait wait;

    static HomePage homePage;
    static RestaurantPage restaurantPage;
    static CartPage cartPage;
    @BeforeEach
    public void setup() {
        var options = Util.setup();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        loginPage = new LoginPage(driver);
        loginPage.login("011223344", "Ransomeware");
        homePage = new HomePage(driver);
        restaurantPage = new RestaurantPage(driver);
        homePage.selectRestaurantByName("0Urban Flavor");
         restaurantPage.addRandomFood();
        cartPage = new CartPage(driver);
        cartPage.clickCart();
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    @Test
    public void testCartEmpty() {
        if (!cartPage.isCartEmpty()) {
            cartPage.removeAllItem();
            Assertions.assertTrue(cartPage.isCartEmpty(), "Cart empty");
        }
    }
    @Test
    void testIncreaseQuantity() {
        int before = cartPage.getFirstItemQuantity();
        cartPage.increaseFirstItemQuantity();
        int after = cartPage.getFirstItemQuantity();
        Assertions.assertEquals(before + 1, after, "Số lượng phải tăng lên 1");
    }

    @Test
    void testDecreaseQuantity() {
        int before = cartPage.getFirstItemQuantity();
        if (before > 1) {  // chỉ giảm khi số lượng > 1
            cartPage.decreaseFirstItemQuantity();
            int after = cartPage.getFirstItemQuantity();
            Assertions.assertEquals(before - 1, after, "Số lượng phải giảm đi 1");
        } else {
            System.out.println("Số lượng <= 1, bỏ qua kiểm tra giảm");
        }
    }

    @Test
    public void testRemove() {
        if (!cartPage.isCartEmpty()) {
            int before = cartPage.getCartItemCount();
            if (before > 1) {
                cartPage.removeFirstItem();
                int after = cartPage.getCartItemCount();
                Assertions.assertEquals(before - 1, after, ".......");
            } else {
                cartPage.removeFirstItem();
            }

        }
    }
    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
