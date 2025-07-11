import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestaurantTest {

    static WebDriver driver;
    static LoginPage loginPage;
    static WebDriverWait wait;

    static HomePage homePage;
    static RestaurantPage restaurantPage;
    @BeforeEach
    public void setup() {
        var options = Util.setup();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        loginPage = new LoginPage(driver);
        loginPage.login("011223344", "Ransomeware");
        homePage = new HomePage(driver);
        restaurantPage = new RestaurantPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    @Test
    public void testRestaurantHadFood() {
        homePage.selectRestaurantByName("0Urban Flavor");
        Util.sleep(1);
        assertTrue(restaurantPage.isRestaurantHadFood());
    }

    @Test
    public void testRestaurantNotHadFood() {
        homePage.selectRestaurantByName("ABC Bakery 5");
        Util.sleep(1);
        assertFalse(restaurantPage.isRestaurantHadFood());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
