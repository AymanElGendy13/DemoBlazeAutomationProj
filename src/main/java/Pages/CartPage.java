package Pages;

import DriverSetup.DriverManager;
import Utils.EventHandler;
import Utils.Waits;
import core.ui.Button;
import core.ui.Label;
import core.ui.TextBox;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.HashMap;
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
    //public static final By productRows = By.cssSelector("tbody tr.success");
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

    /*
    public static Map<String, Double> getAllProducts() {
        Waits.waitForElementVisible(DriverManager.getDriver(), prodcutTable);
        List<WebElement> rows = DriverManager.getDriver().findElements(productRows);
        Map<String, Double> products = new LinkedHashMap<>();

        for (WebElement row : rows) {
            try {
                String name = row.findElement(productTitle).getText().trim();
                String priceText = row.findElement(productPrice)
                        .getText()
                        .replaceAll("[^\\d.]", "");
                double price = Double.parseDouble(priceText);
                products.put(name, price);
            } catch (Exception e) {
                System.err.println("Error parsing product row: " + e.getMessage());
            }
        }
        return products;
    }


    public double calculateTotalPrice() {
        return getAllProducts().values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }


    public static double getProductPrice(String productName) {
        Map<String, Double> products = getAllProducts();
        if (!products.containsKey(productName)) {
            throw new NoSuchElementException("Product '" + productName + "' not found");
        }
        return products.get(productName);
    }


    public void deleteProduct(String prodName) {
        Waits.waitForElementVisible(DriverManager.getDriver(), prodcutTable);
        List<WebElement> rows = DriverManager.getDriver().findElements(productRows);

        for (WebElement row : rows) {
            try {
                String currentName = row.findElement(productTitle)
                        .getText()
                        .trim();
                if (currentName.equalsIgnoreCase(prodName)) {
                    row.findElement(deleteBtn.getLocator()).click();
                    Waits.waitForElementNotPresent(DriverManager.getDriver(), row);
                    return;
                }
            } catch (StaleElementReferenceException e) {
                // Row disappeared during iteration (probably deleted by another process)
                continue;
            }
        }
        throw new NoSuchElementException("Product '" + prodName + "' not found");
    }
    */
}