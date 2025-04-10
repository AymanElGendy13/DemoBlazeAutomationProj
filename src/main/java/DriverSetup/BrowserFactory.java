package DriverSetup;

import Utils.PropertiesUtil;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Map;

public class BrowserFactory {

    public static WebDriver getBrowser(String browserName) {
        //String browserName = PropertiesUtil.getPropertyValue("browser");
        return switch (browserName.toLowerCase()) {
            case "chrome" -> new ChromeDriver();
            case "edge" -> new EdgeDriver();
            default -> new FirefoxDriver();
        };
    }


    /*public static WebDriver getBrowser(String browserName) {
        return switch (browserName.toLowerCase()) {
            case "chrome" -> {
                ChromeOptions chromeOptions = getChromeOptions();
                yield new ChromeDriver(chromeOptions);
            }
            case "edge" -> {
                EdgeOptions edgeOptions = getEdgeOptions();
                yield new EdgeDriver(edgeOptions);
            }
            default -> {
                FirefoxOptions firefoxOptions = getFirefoxOptions();
                yield new FirefoxDriver(firefoxOptions);
            }
        };
    }*/

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--disable-infobars");
        firefoxOptions.addArguments("--disable-extensions");
        firefoxOptions.addArguments("--disable-notifications");

        if(!PropertiesUtil.getPropertyValue("executionType").equalsIgnoreCase("local")) {
            firefoxOptions.addArguments("--headless");
        }

        firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        firefoxOptions.setAcceptInsecureCerts(true);
        return firefoxOptions;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--disable-infobars");
        edgeOptions.addArguments("--disable-extensions");
        edgeOptions.addArguments("--disable-notifications");
        if(!PropertiesUtil.getPropertyValue("executionType").equalsIgnoreCase("local")) {
            edgeOptions.addArguments("--headless");
        }
        Map<String,Object> edgePrefs = Map.of("profile.default_content_setting_values.notifications", 2
        ,"credential_enable_service", false
        ,"profile.password_manager_enabled", false
        ,"autofill.profile_enabled", false);
        edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        edgeOptions.setAcceptInsecureCerts(true);
        return edgeOptions;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-infobars");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-notifications");
        if(!PropertiesUtil.getPropertyValue("executionType").equalsIgnoreCase("local")) {
            chromeOptions.addArguments("--headless");
        }

        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        chromeOptions.setAcceptInsecureCerts(true);
        return chromeOptions;
    }

}