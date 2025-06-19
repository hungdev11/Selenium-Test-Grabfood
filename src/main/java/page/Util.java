package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Util {
    public static void ignoreAlert(WebDriver driver) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            while(true) {
                shortWait.until(ExpectedConditions.alertIsPresent());
                driver.switchTo().alert().accept();
                System.out.println("Alert accepted.");
            }
        } catch (Exception e) {
            System.out.println("No Alert found.");
        }
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
