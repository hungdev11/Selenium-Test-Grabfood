import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
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
        accessWebsite("http://localhost:3000");
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