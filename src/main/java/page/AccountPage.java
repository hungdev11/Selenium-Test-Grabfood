package page;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public AccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void addNewAddress(String addressText, String originalTabHandle) {
        try {
            // Click "Địa chỉ giao hàng" tab
            WebElement addressTabButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[contains(text(),'Địa chỉ giao hàng')]")));
            addressTabButton.click();

            // Click "Thêm địa chỉ"
            WebElement addAddressButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(., 'Thêm địa chỉ')]")));
            addAddressButton.click();

            // Nhập địa chỉ vào ô input
            WebElement addressInput = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@placeholder='Nhập địa điểm...']")));
            addressInput.clear();
            addressInput.sendKeys(addressText);

            // Chọn suggestion đầu tiên
            WebElement firstSuggestion = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//ul[contains(@class, 'max-h-40')]//li[1]")));
            firstSuggestion.click();

            // Click nút "Lưu"
            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Lưu')]")));

            Thread.sleep(2000);
            saveButton.click();
            // Chờ 2s sau khi lưu để UI ổn định
            Thread.sleep(2000);

            // Nếu có tab gốc, quay lại
            if (StringUtils.isNotBlank(originalTabHandle)) {
                driver.close(); // đóng tab hiện tại
                driver.switchTo().window(originalTabHandle); // quay lại tab ban đầu
            }
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi thêm địa chỉ: " + e.getMessage());
        }
    }
}
