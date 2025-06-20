import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckOutTest {
    static WebDriver driver;
    static LoginPage loginPage;
    static WebDriverWait wait;

    static HomePage homePage;
    static RestaurantPage restaurantPage;
    static CartPage cartPage;

    static CheckoutPage checkoutPage;
    @BeforeEach
    public void setup() {
        var options = Util.setup();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        loginPage = new LoginPage(driver);
        loginPage.login("0869738540", "123456");
        homePage = new HomePage(driver);
        restaurantPage = new RestaurantPage(driver);
        homePage.selectRestaurantByName("Urban Flavor");
        restaurantPage.addRandomFood();
        cartPage = new CartPage(driver);
        cartPage.clickCart();
        cartPage.checkout();
        checkoutPage = new CheckoutPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    @Test
    public void testTotalPriceDisplay() {
        List<Integer> itemPrices = checkoutPage.getItemPrices();
        int deliveryFee = checkoutPage.getDeliveryFee();
        //int discount = checkoutPage.getDiscount();
        int displayedTotal = checkoutPage.getDisplayedTotal();

        int calculatedTotal = itemPrices.stream().mapToInt(Integer::intValue).sum() + deliveryFee /*- discount*/;

        assertEquals(calculatedTotal, displayedTotal,
                "Tổng cộng hiển thị không khớp với tổng tính toán từ sản phẩm, phí giao hàng");
    }

    @Test
    public void testInvalidVoucherCode_ShowsError() {
        checkoutPage.enterVoucherCode("WRONG-CODE");
        checkoutPage.clickApplyVoucher();
        String error = checkoutPage.getVoucherErrorMessage();
        Assertions.assertEquals("Mã voucher không tồn tại!", error);
    }

    @Test
    public void testEmptyVoucherCode_ShowsRequiredError() {
        //checkoutPage.enterVoucherCode("");
        checkoutPage.clickApplyVoucher();
        String error = checkoutPage.getVoucherErrorMessage();
        Assertions.assertEquals("Vui lòng nhập mã khuyến mãi!", error);
    }

    @Test
    public void testPlaceOrder() {
        checkoutPage.clickOrderButton();

        Assertions.assertTrue(checkoutPage.isSuccessDialogVisible(),
                "Dialog đặt hàng thành công không hiển thị sau khi nhấn nút 'Đặt đơn'");
    }

    @Test
    public void testMomoPayment() {
        checkoutPage.selectMomoPaymentMethod();
        checkoutPage.clickPlaceOrderButton();
        Util.sleep(2);
        checkoutPage.clickMomoBackButton();
        Util.sleep(1);
        checkoutPage.clickCancelTransactionButton();
        Util.sleep(2);
        Assertions.assertTrue(
                checkoutPage.isPaymentFailedMessageVisible(),
                "Thông báo thanh toán thất bại phải được hiển thị"
        );
    }
    @AfterEach
    public void tearDown() {
        Util.sleep(1);
        driver.quit();
    }
}
