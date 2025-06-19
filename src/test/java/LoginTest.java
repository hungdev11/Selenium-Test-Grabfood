import org.junit.jupiter.api.Test;
import page.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest{
    @Test
    public void testLoginSuccess() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("0869738540", "123456");
        assertTrue(loginPage.isLoginSuccessful(), "Login should succeed");
    }

    @Test
    public void testLoginFailure() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("wrongUser", "wrongPass");
        assertTrue(loginPage.isLoginFailed(), "Login should fail");
    }

    @Test
    public void testMissingUsername() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "somePassword");
        assertTrue(loginPage.isLoginFailed(), "Login should fail");
    }

    @Test
    public void testMissingPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("user@example.com", "");
        assertTrue(loginPage.isLoginFailed(), "Login should fail");
    }
}
