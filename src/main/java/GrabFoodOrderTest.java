import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import page.*;

public class GrabFoodOrderTest {
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static void main(String[] args) throws InterruptedException {
        setup();
        accessWebsite("http://localhost:3000");
        var registerPage = new RegisterPage(driver);
        var loginPage = new LoginPage(driver);
        var homePage = new HomePage(driver);
        var restaurantPage = new RestaurantPage(driver);
        var cartPage = new CartPage(driver);
        var checkoutPage = new CheckoutPage(driver);

        registerPage.registerRandomUser();
        loginPage.login("011223344", "Ransomeware");
        homePage.selectFirstRestaurant();
        restaurantPage.addPhoBoToCart("Phở bò", 1);
        cartPage.verifyQuantity("Phở bò", 1);
        cartPage.checkout();
        Util.ignoreAlert(driver);
        checkoutPage.isCheckoutPage();
        checkoutPage.fillAddress("97 man thien");
        checkoutPage.placeOrder();
        Thread.sleep(5000);
        // place order
        closeBrowser();
    }

    private static void setup() {
        System.setProperty("webdriver.chrome.driver", "E:/chromedriver-win64/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("E:/chrome-win64/chrome-win64/chrome.exe");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox", "--disable-gpu");
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