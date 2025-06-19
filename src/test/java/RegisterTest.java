import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import page.RegisterPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {
    static WebDriver driver;
    static RegisterPage registerPage;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "D:/PHUCLE/PJ/Test_order/chromedriver-win64/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setBinary("D:/PHUCLE/PJ/Test_order/chrome-win64/chrome.exe");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        registerPage = new RegisterPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testValidRegistration() {
        registerPage.openRegisterForm();
        registerPage.registerWithData("Test User", registerPage.generateRandomEmail(), "0123456789", "abc123", "abc123");
        assertTrue(registerPage.isAlertWithTextDisplayed("Registration completed")); // không có lỗi nào
    }

    @Test
    public void testPasswordMismatch() {
        registerPage.openRegisterForm();
        registerPage.registerWithData("Mismatch", registerPage.generateRandomEmail(), "0123456789", "abc123", "abc321");
        assertTrue(registerPage.isAlertWithTextDisplayed("Passwords don't match"));
    }

    @Test
    public void testInvalidEmailFormat() {
        registerPage.openRegisterForm();
        registerPage.registerWithData("Invalid Email", "invalid-email", "0123456789", "abc123", "abc123");
        assertFalse(registerPage.isAlertWithTextDisplayed("Registration completed")); // không có lỗi nào
    }


    @Test
    public void testEmptyFields() {
        registerPage.openRegisterForm();
        registerPage.registerWithData("", "", "", "", "");
        assertFalse(registerPage.isAlertWithTextDisplayed("Registration completed")); // không có lỗi nào
    }
}
