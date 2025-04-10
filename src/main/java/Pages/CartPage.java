package Pages;

import DriverSetup.DriverManager;
import Utils.EventHandler;
import Utils.Waits;
import core.ui.Button;
import core.ui.Label;
import core.ui.TextBox;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartPage extends DemoBlaze {
    //Locators
    private final Label totalPRICE = new Label(By.id("totalp"));
    private final Button placeOrderBtn = new Button(By.cssSelector(".btn.btn-success"));
    private final TextBox placeOrderName = new TextBox(By.id("name"));
    private final TextBox placeOrderCountry = new TextBox(By.id("country"));
    private final TextBox placeOrderCity = new TextBox(By.id("city"));
    private final TextBox placeOrderCreditCard = new TextBox(By.id("card"));
    private final TextBox placeOrderMonth = new TextBox(By.id("month"));
    private final TextBox placeOrderYear = new TextBox(By.id("year"));
    private final Button placeOrderPurchaseBtn = new Button(By.cssSelector("button[onclick='purchaseOrder()']"));

    private final Label purchaseMessage = new Label(By.xpath("//h2[normalize-space()='Thank you for your purchase!']"));
    private final Label purchaseDetails = new Label(By.cssSelector(".lead.text-muted"));
    private final Button purchaseOkBtn = new Button(By.cssSelector(".confirm.btn.btn-lg.btn-primary"));
    private final By logoPic = By.cssSelector(".sa-line.sa-long.animateSuccessLong");

    //public static final By prodcutTable = By.cssSelector("table.table");
    public static final By productRows = By.cssSelector("tbody tr.success");
    //public static final By productTitle = By.xpath(".//td[contains(@class,'title') or contains(text(),'')]");
    //public static final By productPrice = By.xpath(".//td[contains(@class,'price')  or string-length(text()) > 0]");
    //private static Button deleteBtn = new Button(By.xpath("//a[contains(text(),'Delete')]"));

    public CartPage() {
        super();
    }

    public double totalPrice() {
        String totalPrice = totalPRICE.getText();
        totalPrice = totalPrice.replace("$", "").replace(",", "").trim();
        return Double.parseDouble(totalPrice);
    }

    @Step("Placing order")
    public CartPage placeOrder() {
        Waits.waitForElementVisible(DriverManager.getDriver(), totalPRICE.getLocator());
        placeOrderBtn.click();
        return this;
    }

    private void setPlaceOrderName(String name) {
        placeOrderName.sendData(name);
    }

    private void setPlaceOrderCountry(String country) {
        placeOrderCountry.sendData(country);
    }

    private void setPlaceOrderCity(String city) {
        placeOrderCity.sendData(city);
    }

    private void setPlaceOrderCreditCard(String creditCard) {
        placeOrderCreditCard.sendData(creditCard);
    }

    private void setPlaceOrderMonth(String month) {
        placeOrderMonth.sendData(month);
    }

    private void setPlaceOrderYear(String year) {
        placeOrderYear.sendData(year);
    }

    private void clickPlaceOrderPurchase() {
        placeOrderPurchaseBtn.click();
    }

    @Step("Setting total order with name: {name}, country: {country}, city: {city}, creditCard: {creditCard}, month: {month}, year: {year}")
    public CartPage setTotalOrder(String name, String country, String city, String creditCard, String month, String year) {
        setPlaceOrderName(name);
        setPlaceOrderCountry(country);
        setPlaceOrderCity(city);
        setPlaceOrderCreditCard(creditCard);
        setPlaceOrderMonth(month);
        setPlaceOrderYear(year);
        clickPlaceOrderPurchase();
        return this;
    }

    public String getPurchaseMessage() {
        return purchaseMessage.getText();
    }

    public Map<String, String> getPurchaseDetails() {
        String fullText = purchaseDetails.getText();
        Map<String, String> details = new HashMap<>();

        String[] lines = fullText.split("\n");
        for (String line : lines) {
            if (line.trim().isEmpty() || line.equals("OK")) continue;

            if (line.equals("Thank you for your purchase!")) {
                details.put("header", line);
                continue;
            }

            String[] parts = line.split(":", 2);
            if (parts.length == 2) {
                details.put(parts[0].trim(), parts[1].trim());
            }
        }
        return details;
    }

    @Step("Clicking purchase OK button")
    public void clickPurchaseOk() {
        EventHandler.waitForSuccessAnimation(DriverManager.getDriver(), logoPic);
        purchaseOkBtn.click();
    }

    public void deleteProduct(String deleteThis) {
        Waits.waitForElementVisible(DriverManager.getDriver(), productRows);
        List<WebElement> rows = DriverManager.getDriver().findElements(productRows);
        for (WebElement row : rows) {
            String itemName = row.findElement(By.cssSelector("td:nth-child(2)")).getText();
            if (itemName.equals(deleteThis)) {
                row.findElement(By.tagName("a")).click();
            }
        }
        DriverManager.getDriver().navigate().refresh();
    }

    public double getTotalCartPrice() {
        Waits.waitForElementVisible(DriverManager.getDriver(), totalPRICE.getLocator());
        By rowLocator = By.cssSelector("#tbodyid tr.success");
        By priceCellLocator = By.xpath("./td[3]");  // Directly targets the 3rd column (price)
        double total = 0.0;
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));

        // Wait for ALL rows to be visible (not just the first)
        List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rowLocator));

        for (WebElement row : rows) {
            // Wait for the PRICE CELL in the current row to be visible
            WebElement priceCell = wait.until(ExpectedConditions.visibilityOf(
                    row.findElement(priceCellLocator)
            ));

            // Parse and sum the price
            String priceText = priceCell.getText().trim();
            try {
                total += Double.parseDouble(priceText.replaceAll("[^\\d.]", ""));
            } catch (NumberFormatException e) {
                System.err.println("Skipping invalid price: " + priceText);
            }
        }
        return total;
    }

}