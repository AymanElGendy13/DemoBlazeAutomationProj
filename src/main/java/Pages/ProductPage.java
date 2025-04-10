package Pages;

import Utils.AlertHandler;
import core.ui.Button;
import core.ui.Label;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductPage extends DemoBlaze {

    //Locators
    private final Label productName = new Label(By.cssSelector(".name"));
    private final Label productPrice = new Label(By.cssSelector(".price"));
    private final Label productDescription = new Label(By.cssSelector("div[id='more-information'] p"));
    private final Button addToCartButton = new Button(By.cssSelector(".btn.btn-success.btn-lg"));

    public ProductPage() {
        super();
    }

    public void addToCart() {
        addToCartButton.click();
    }

    public String getProductName() {
        return productName.getText();
    }

    public String getProductPrice() {
       return productPrice.getText();
    }

    public String getProductDescription() {
        return productDescription.getText();
    }

    @Step("Adding product to cart and verifying alert")
    public ProductPage addToCartAndVerify() {
        addToCart();
        AlertHandler.verifyAndAccept("Product added");
        return this;
    }

}