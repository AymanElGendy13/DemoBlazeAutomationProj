import DriverSetup.DriverManager;
import Pages.DemoBlaze;
import Utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.*;
import Listeners.TestNGListeners;

@Listeners(TestNGListeners.class)
public class EndToEndTest {


    DemoBlaze demoBlaze;
    JsonUtils testDataOne,testDataTwo;

    @BeforeClass
    public void beforeClass() {
        testDataOne = new JsonUtils("e2eScenarioOne-test-data");
        testDataTwo = new JsonUtils("e2eScenarioTwo-test-data");
    }

    @BeforeMethod
    public void setUp() {
        //Driver instance
        DriverManager.createDriverInstance("chrome");
        //Initialize pages object
        demoBlaze = new DemoBlaze();
        //Perform actions
        demoBlaze.openSite();
        DriverManager.maximizeWindow();
    }

    // Login --> Choose Category & Product --> Check Cart --> Place Order --> Purchase --> OK
    @Test(enabled =true)
    public void standardUserPurchaseFlow() {

        // Login
        demoBlaze.navigateToNavbar("login")
                .login(
                        testDataOne.getJsonData("login.username"),
                        testDataOne.getJsonData("login.password")
                );

        // Verify login
        String welcomeText = demoBlaze.returnUsername();
        Assert.assertEquals(welcomeText,
                testDataOne.getJsonData("assertions.welcomeText"),
                "Username is not correct");

        // First product
        demoBlaze.chooseProduct(
                testDataOne.getJsonData("chooseProduct[0].category"),
                testDataOne.getJsonData("chooseProduct[0].product")
        );
        demoBlaze.getProductPage().addToCartAndVerify();

        // Verify first product name
        String prodName = demoBlaze.getProductPage().getProductName();
        Assert.assertEquals(prodName,
                testDataOne.getJsonData("assertions.productName"),
                "Product name is not correct");

        demoBlaze.navigateToNavbar("mainlogoicon");

        // Second product
        demoBlaze.chooseProduct(
                testDataOne.getJsonData("chooseProduct[1].category"),
                testDataOne.getJsonData("chooseProduct[1].product")
        );
        demoBlaze.getProductPage().addToCartAndVerify();

        // Checkout process
        demoBlaze.navigateToNavbar("cart")
                .getCartPage()
                .placeOrder()
                .setTotalOrder(
                        testDataOne.getJsonData("order.name"),
                        testDataOne.getJsonData("order.country"),
                        testDataOne.getJsonData("order.city"),
                        testDataOne.getJsonData("order.creditCard"),
                        testDataOne.getJsonData("order.month"),
                        testDataOne.getJsonData("order.year")
                )
                .clickPurchaseOk();
    }

    @Test(enabled = true)
    public void registeredUserFullJourney()
    // Signup --> Login --> Choose Prod --> Contact --> Choose Prod --> Check Cart --> Place Order --> Purchase --> OK
    {
        demoBlaze.navigateToNavbar("signup")
                .signUp("asadsadsad", "123123")
                .closeButton("signup");

        // Login
        demoBlaze.navigateToNavbar("login")
                .login(
                        testDataTwo.getJsonData("login.username"),
                        testDataTwo.getJsonData("login.password")
                );

        // Verify login
        String welcomeText = demoBlaze.returnUsername();
        Assert.assertEquals(welcomeText,
                testDataTwo.getJsonData("assertions.welcomeText"),
                "Username is not correct");

        // First product
        demoBlaze.chooseProduct(
                testDataTwo.getJsonData("chooseProduct[0].category"),
                testDataTwo.getJsonData("chooseProduct[0].product")
        );

        demoBlaze.getProductPage().addToCartAndVerify();

        // Contact Us
        demoBlaze.navigateToNavbar("contact")
                .contactUs(
                        testDataTwo.getJsonData("contact.email"),
                        testDataTwo.getJsonData("contact.name"),
                        testDataTwo.getJsonData("contact.message")
                );

        demoBlaze.navigateToNavbar("home")
                .chooseProduct(
                testDataTwo.getJsonData("chooseProduct[1].category"),
                testDataTwo.getJsonData("chooseProduct[1].product")
        );
        demoBlaze.getProductPage().addToCartAndVerify();

        String prodName = demoBlaze.getProductPage().getProductName();
        Assert.assertEquals(prodName,
                testDataTwo.getJsonData("assertions.productName"),
                "Product name is not correct");

        //demoBlaze.getCartPage().deleteProduct("MacBook Pro");

        demoBlaze.navigateToNavbar("about us")
                .getTextAboutUs()
                .closeButton("about us");

        demoBlaze.navigateToNavbar("cart")
                .getCartPage()
                .placeOrder()
                .setTotalOrder(
                       testDataTwo.getJsonData("order.name"),
                       testDataTwo.getJsonData("order.country"),
                       testDataTwo.getJsonData("order.city"),
                       testDataTwo.getJsonData("order.creditCard"),
                       testDataTwo.getJsonData("order.month"),
                       testDataTwo.getJsonData("order.year"))
                .clickPurchaseOk();

        demoBlaze.navigateToNavbar("logout");

    }

    @AfterMethod
    public void tearDown() {
        DriverManager.tearDown();
    }

}