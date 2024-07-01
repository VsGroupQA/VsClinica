package utils;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class Browser {

    private static WebDriver driver;

    public static WebDriver iniciarNavegador(String navegadorDesejado) {
        switch (navegadorDesejado.toUpperCase()) {
        
            case "FIREFOX":
                FirefoxProfile profileFirefox = new FirefoxProfile();
                profileFirefox.setPreference("layout.css.devPixelsPerPx", "1.35");

                FirefoxOptions optionsFirefox = new FirefoxOptions();
                optionsFirefox.setProfile(profileFirefox);

                driver = new FirefoxDriver(optionsFirefox);
                break;

            case "EDGE":
                EdgeOptions optionsEdge = new EdgeOptions();
                optionsEdge.addArguments("force-device-scale-factor=1.35");

                driver = new EdgeDriver(optionsEdge);
                break;

            case "CHROME":
            default:
                ChromeOptions optionsChrome = new ChromeOptions();
                optionsChrome.addArguments("force-device-scale-factor=1.35");

                driver = new ChromeDriver(optionsChrome);
                break;
        }

        maximize();
        return driver;
    }

    private static void maximize() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
    }

    public static void fecharNavegador() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}