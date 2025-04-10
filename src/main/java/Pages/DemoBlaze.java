package Pages;

import DriverSetup.DriverManager;
import Utils.AlertHandler;
import Utils.Waits;
import core.ui.Button;
import core.ui.Label;
import core.ui.TextBox;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import java.util.List;

import static Utils.PropertiesUtil.getPropertyValue;

//vars
//locators
//actions

public class DemoBlaze {

    ProductPage productPage;
    CartPage cartPage;

    public DemoBlaze() {

    }
    public ProductPage getProductPage() {
        if (productPage == null) {
            productPage = new ProductPage();
        }
        return productPage;
    }

    public CartPage getCartPage() {
        if (cartPage == null) {
            cartPage = new CartPage();
        }
        return cartPage;
    }

    //Locators
    //Navbar
    private final Button navBarHome = new Button(By.cssSelector(".nav-item.active"));
    private final Button navbarContact = new Button(By.cssSelector("a[data-target='#exampleModal']"));
    private final Button navbarAboutUs = new Button(By.cssSelector("a[data-target='#videoModal']"));
    private final Button navbarCart = new Button(By.id("cartur"));
    private final Button navbarLogin = new Button(By.id("login2"));
    private final Button navbarSignUp = new Button(By.id("signin2"));
    private final Button navbarMainLogoIcon = new Button(By.id("nava"));
    private final Button navBarLogout = new Button(By.id("logout2"));
    //Login Locators
    private final TextBox loginUsername = new TextBox(By.id("loginusername"));
    private final TextBox loginPassword = new TextBox(By.id("loginpassword"));
    private final Button loginBtn = new Button(By.cssSelector("button[onclick='logIn()']"));
    private final Button loginCloseBtn = new Button(By.cssSelector("div[id='logInModal'] span[aria-hidden='true']"));
    //SignUp Locators
    private final TextBox signupUsername = new TextBox(By.id("sign-username"));
    private final TextBox signupPassword = new TextBox(By.id("sign-password"));
    private final Button signupBtn = new Button(By.cssSelector("button[onclick='register()']"));
    private final Button signupCloseBtn = new Button(By.cssSelector("div[id='signInModal'] span[aria-hidden='true']"));
    //Contact Locators
    private final TextBox contactEmail = new TextBox(By.id("recipient-email"));
    private final TextBox contactName = new TextBox(By.id("recipient-name"));
    private final TextBox textMessage = new TextBox(By.id("message-text"));
    private final Button sendMessageBtn = new Button(By.cssSelector("button[onClick='send()']"));
    private final Button contactCloseBtn = new Button(By.cssSelector("div[id='exampleModal'] span[aria-hidden='true']"));
    //About Us Locators
    private final Button aboutUsCloseBtn = new Button(By.cssSelector("div[id='videoModal'] div[class='modal-header'] span[aria-hidden='true']"));

    //Others
    //private Button closeBtn = new Button(By.cssSelector("div[id='signInModal'] span[aria-hidden='true']"));
    private final Label welcomeUser = new Label(By.id("nameofuser"));
    private final By prodCategory = By.cssSelector("a.list-group-item[id='itemc']");
    private final By prodName = By.cssSelector("h4.card-title");
    private final Label aboutusText = new Label(By.cssSelector("div[aria-label='Modal Window']"));

    //Actions
    @Step("Open DemoBlaze site")
    public void openSite() {
        DriverManager.navigateToUrl(getPropertyValue("baseURL"), true);
    }

    @Step("Navigate to navbar: {navbarName}")
    public DemoBlaze navigateToNavbar(String navbarName) {
        switch (navbarName.toLowerCase()) {
            case "home":
                this.navBarHome.click();
                break;
            case "contact":
                this.navbarContact.click();
                break;
            case "about us":
                this.navbarAboutUs.click();
                break;
            case "cart":
                this.navbarCart.click();
                break;
            case "login":
                this.navbarLogin.click();
                break;
            case "signup":
                this.navbarSignUp.click();
                break;
            case "mainlogoicon":
                this.navbarMainLogoIcon.click();
                break;
            case "logout":
                    this.navBarLogout.click();
            break;
            default:
                throw new NoSuchElementException("Navbar not found: " + navbarName);
        }
        return this;
    }

    @Step("Login with username: {username} and password: {password}")
    public DemoBlaze login(String username, String password) {
        this.loginUsername.sendData(username);
        this.loginPassword.sendData(password);
        this.loginBtn.click();
        return this;
    }

    public String returnUsername() {
        return this.welcomeUser.getText();
    }

    @Step("Signup with username: {username} and password: {password}")
    public DemoBlaze signUp(String username, String password) {
        this.signupUsername.sendData(username);
        this.signupPassword.sendData(password);
        this.signupBtn.click();
        AlertHandler.accept();
        return this;
    }

    @Step("Contact Us with email: {email}, name: {name}, message: {message}")
    public void contactUs(String email, String name, String message) {
        this.contactEmail.sendData(email);
        this.contactName.sendData(name);
        this.textMessage.sendData(message);
        this.sendMessageBtn.click();
        AlertHandler.accept();
    }


    public void selectCategory(String categoryName) {
        List<WebElement> categories = DriverManager.getDriver().findElements(prodCategory);
        for(WebElement category : categories)
        {
            if(category.getText().equalsIgnoreCase(categoryName))
            {
                category.click();
                return;
            }
        }
        throw new NoSuchElementException("Category not found: " + categoryName);
    }
    public void selectProduct(String productName) {
        Waits.waitForElementVisible(DriverManager.getDriver(), By.xpath("//a[normalize-space()='" + productName + "']")); //ask!!
        List<WebElement> products = DriverManager.getDriver().findElements(prodName);
        for(WebElement product : products)
        {
            if(product.getText().equalsIgnoreCase(productName))
            {
                product.click();
                return;
            }
        }
        throw new NoSuchElementException("Product not found: " + productName);
    }

    //Implement method to identify which category (Laptop, Monitor, Phone) then choose the product name from category
    @Step("Choose product from category: {category} with name: {productName}")
    public void chooseProduct(String category, String productName){
        this.selectCategory(category);
        this.selectProduct(productName);
    }

    @Step("Closing: {buttonName}")
    public void closeButton(String buttonName){
        switch (buttonName.toLowerCase()) {
            case "login":
                this.loginCloseBtn.click();
                break;
            case "signup":
                this.signupCloseBtn.click();
                break;
            case "contact":
                this.contactCloseBtn.click();
                break;
            case "about us":
                this.aboutUsCloseBtn.click();
                break;
            default:
                throw new NoSuchElementException("Button not found: " + buttonName);
        }
    }


    public DemoBlaze getTextAboutUs() {
        Waits.waitForElementVisible(DriverManager.getDriver(), aboutusText.getLocator());
        System.out.println(aboutusText.getText());
        return this;
    }

}
