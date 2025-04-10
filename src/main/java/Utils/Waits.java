package Utils;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static Utils.PropertiesUtil.getPropertyValue;

public class Waits {

    private static final int EXPLICIT_WAIT = Integer.parseInt(getPropertyValue("explicitWait"));



    private Waits() {
    }

    @Step("Waiting for element to be present: {locator}")
    public static WebElement waitForElementPresent(WebDriver driver, By locator) {

        Logs.info("Waiting for element to be present: " + locator);
        return new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT)).
                until(driver1 -> driver1.findElement(locator));
    }

    @Step("Waiting for element to be Visible: {locator}")
    public static WebElement waitForElementVisible(WebDriver driver, By locator) {
        Logs.info("Waiting for element to be visible: " + locator);
        return new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT)).
                until(driver1 ->
                        {
                            WebElement element = waitForElementPresent(driver, locator);
                            return element.isDisplayed() ? element : null;
                        }
                );
    }

    @Step("Waiting for element to be Clickable: {locator}")
    public static WebElement waitForElementClickable(WebDriver driver, By locator) {
        Logs.info("Waiting for element to be clickable: " + locator);
        return new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT))
                .until(driver1 ->
                        {
                            WebElement element = waitForElementVisible(driver, locator);
                            return element.isEnabled() ? element : null;
                        }
                );
    }

    @Step("Waiting for element to be not present: {element}")
    public static boolean waitForElementNotPresent(WebDriver driver, WebElement element) {
        Logs.info("Waiting for element to be not present: " + element);
        return new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT))
                .until(ExpectedConditions.stalenessOf(element));
    }

    @Step("Waiting for Alert to be present")
    public static Alert waitForAlertPresent(WebDriver driver) {
        Logs.info("Waiting for alert to be present");
        return new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT))
                .until(ExpectedConditions.alertIsPresent());
    }

}