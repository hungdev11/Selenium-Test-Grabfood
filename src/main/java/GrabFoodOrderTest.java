import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.logging.Logger;

import page.*;

public class GrabFoodOrderTest {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static RegisterPage registerPage;
    private static LoginPage loginPage;
    private static HomePage homePage;
    private static RestaurantPage restaurantPage;
    private static CartPage cartPage;
    private static CheckoutPage checkoutPage;

    private static final Logger logger = LoggerHelper.getLogger();

    static {
        setup();
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        restaurantPage = new RestaurantPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    public static void main(String[] args) throws InterruptedException {
        happycase();
    }

    private static void happycase() throws InterruptedException {
        logger.info("Start Happy Case Test : new user, success order");
        accessWebsite("http://localhost:3000");

        logger.info("Registering user...");
//        registerPage.registerRandomUser();

        logger.info("Logging in...");
        loginPage.login("011223344", "Ransomeware");

        logger.info("Selecting first restaurant...");
        homePage.selectFirstRestaurant();

        logger.info("Adding item to cart...");
        restaurantPage.addFoodToCart("Phở bò", 3);

        logger.info("Verifying cart quantity...");
        cartPage.verifyQuantity("Phở bò", 3);

        logger.info("Proceeding to checkout...");
        cartPage.checkoutAfterVerify();
        Util.ignoreAlert(driver);

        logger.info("Filling address...");
        checkoutPage.isCheckoutPage();
        checkoutPage.fillAddress("97 man thien");

        logger.info("Placing order...");
        checkoutPage.placeOrder();

        Thread.sleep(5000);
        logger.info("Test completed. Closing browser...");
        closeBrowser();
    }

    private static void setup() {
        var options = Util.setup();
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private static void accessWebsite(String url) {
        driver.get(url);
    }

    private static void closeBrowser() {
        driver.quit();
    }
}