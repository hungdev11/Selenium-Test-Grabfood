import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "D:/PHUCLE/PJ/Test_order/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("D:/PHUCLE/PJ/Test_order/chrome-win64/chrome.exe");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox", "--disable-gpu");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}