package page;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {
    WebDriver driver;
    WebDriverWait wait;
    AccountPage accountPage;
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
}
