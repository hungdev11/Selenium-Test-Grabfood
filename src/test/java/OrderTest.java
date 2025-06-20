import jdk.jfr.Name;
import org.junit.jupiter.api.Test;
import page.*;

public class OrderTest extends BaseTest {

    @Test
    public void OrderSuccessTest() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        RestaurantPage restaurantPage = new RestaurantPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        loginPage.login("011223344", "Ransomeware");
        homePage.selectRestaurantByName("0Urban Flavor");
        restaurantPage.addRandomFood();
        cartPage.checkout();
        // checkoutPage.isCheckoutPage();
        checkoutPage.handleAddress("97 Man Thien");
        checkoutPage.placeOrder();
        Thread.sleep(1000);
    }
}
