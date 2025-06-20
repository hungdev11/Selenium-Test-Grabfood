package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CheckoutPage {
    WebDriver driver;
    WebDriverWait wait;
    AccountPage accountPage;
    private static final Logger logger = LoggerHelper.getLogger();

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        accountPage = new AccountPage(driver);
    }

    public boolean isCheckoutPage() {
        try {
            return wait.until(ExpectedConditions.urlToBe("http://localhost:3000/checkout"));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void fillAddress(String s) throws InterruptedException {
        logger.info("Filling address value: " + s);
        Util.ignoreAlert(driver);
        var selectAddressButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(text(),'+ Chọn địa chỉ giao hàng')]")));
        selectAddressButton.click();
        var addAddressButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Thêm địa chỉ mới')]")));

        // Lưu lại tab hiện tại
        String originalTab = driver.getWindowHandle();
        addAddressButton.click();

        // Đợi đến khi có tab thứ 2
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(driver -> driver.getWindowHandles().size() > 1);

        // Duyệt qua tất cả tab và chuyển sang tab mới
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalTab)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // Bây giờ bạn đang ở tab mới → có thể tiếp tục thao tác
        System.out.println("Tab mới URL: " + driver.getCurrentUrl());

        wait.until(ExpectedConditions.urlToBe("http://localhost:3000/account"));

        accountPage.addNewAddress(s, originalTab);
        driver.navigate().refresh();
    }

    public void placeOrder() {
        var placeOrderButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Đặt đơn')]")));
        placeOrderButton.click();
        logger.info("Placing order completed");
    }

    public boolean isShippingAddressNotSelected() {
        try {
            WebElement chooseAddressDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[normalize-space(text())='+ Chọn địa chỉ giao hàng']")
            ));
            return chooseAddressDiv.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
    public boolean isShippingAddressSelected() {
        try {
            WebElement addressDetail = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'rounded-md') and .//p]")
            ));
            return addressDetail.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void handleAddress(String address) throws InterruptedException {
        Util.ignoreAlert(driver);
        Util.ignoreAlert(driver);
        if (isShippingAddressNotSelected()) {
            fillAddress(address);
        }
    }

    // Hàm parse dùng chung
    private int parseCurrency(String raw) {
        if (raw == null || raw.isEmpty()) return 0;
        return Integer.parseInt(raw.replace(".", "").replace(",", "").replace("đ", "").replace("-", "").trim());
    }

    // Lấy danh sách giá từng sản phẩm
    public List<Integer> getItemPrices() {
        List<WebElement> priceElements = driver.findElements(By.cssSelector(".text-base.font-semibold"));
        List<Integer> prices = new ArrayList<>();
        for (WebElement el : priceElements) {
            try {
                prices.add(parseCurrency(el.getText()));
            } catch (NumberFormatException e) {
                System.out.println("Lỗi chuyển đổi giá sản phẩm: " + el.getText());
            }
        }
        return prices;
    }

    // Lấy phí vận chuyển
    public int getDeliveryFee() {
        try {
            WebElement feeEl = driver.findElement(By.xpath("//span[contains(text(),'Phí vận chuyển')]/following-sibling::span"));
            return parseCurrency(feeEl.getText());
        } catch (Exception e) {
            System.out.println("Không tìm thấy phí vận chuyển.");
            return 0;
        }
    }

    // Lấy tổng cộng hiển thị dưới cùng
    public int getDisplayedTotal() {
        try {
            WebElement totalEl = driver.findElement(By.cssSelector("div.fixed div p.text-sm.text-gray-500"));
            String totalText = totalEl.getText().split("đ")[0];
            return parseCurrency(totalText);
        } catch (Exception e) {
            System.out.println("Không tìm thấy tổng tiền hiển thị.");
            return 0;
        }
    }

    // Nhập mã voucher
    public void enterVoucherCode(String code) {
        WebElement voucherInput = driver.findElement(By.cssSelector("input[placeholder*='Nhập mã khuyến mãi']"));
        voucherInput.clear();
        voucherInput.sendKeys(code);
    }

    // Nhấn nút Áp dụng
    public void clickApplyVoucher() {
        WebElement applyButton = driver.findElement(By.xpath("//button[contains(text(),'Áp dụng')]"));

        // Scroll vào vùng nhìn thấy
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", applyButton);

        // Chờ 1 chút (nếu cần)
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}

        applyButton.click();
    }

    // Lấy text thông báo lỗi voucher (nếu có)
    public String getVoucherErrorMessage() {
        try {
            WebElement errorText = driver.findElement(By.xpath("//p[contains(text(),'Mã voucher') or contains(text(),'Vui lòng')]"));
            return errorText.getText().trim();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public void clickOrderButton() {
        WebElement orderBtn = driver.findElement(By.id("order-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", orderBtn);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(orderBtn))
                .click();
    }

    public boolean isSuccessDialogVisible() {
        try {
            WebElement dialog = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//h2[contains(text(),'Đặt hàng thành công!')]")));
            return dialog.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
