package utils;

import java.time.Duration;

import org.openqa.selenium.Dimension;
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

    /** 
     * Seleciona o navegador a ser executado
     * @param navegador Seleciona qual navegador será utilizado
     * @param headless Define se executará com interface grafica
     */
    public static WebDriver iniciarNavegador(String navegador, boolean headless) {
        switch (navegador.toUpperCase()) {
        
            case "FIREFOX":
                FirefoxProfile profileFirefox = new FirefoxProfile();
                profileFirefox.setPreference("layout.css.devPixelsPerPx", "1.35");

                FirefoxOptions optionsFirefox = new FirefoxOptions();
                optionsFirefox.setProfile(profileFirefox);
                if (headless) {
                    optionsFirefox.addArguments("--headless");
                }

                driver = new FirefoxDriver(optionsFirefox);
                break;

            case "EDGE":
                EdgeOptions optionsEdge = new EdgeOptions();
                optionsEdge.addArguments("force-device-scale-factor=1.35");
                if (headless) {
                    optionsEdge.addArguments("--headless");
                }

                driver = new EdgeDriver(optionsEdge);
                break;

            case "CHROME":
            default:
                ChromeOptions optionsChrome = new ChromeOptions();
                optionsChrome.addArguments("force-device-scale-factor=1.35");
                if (headless) {
                    optionsChrome.addArguments("--headless");
                }

                driver = new ChromeDriver(optionsChrome);
                break;
        }

        maximize();
        return driver;
    }

    /** 
     * Maximiza a janela do navegador
     */
    private static void maximize() {
            driver.manage().window().maximize();
//    		driver.manage().window().setSize(new Dimension(1920, 1080));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
    }


    /** 
     * Fecha o navegador utilizado
     * @param opcao Seleciona se navegador fechara
     */
    public static void fecharNavegador(boolean opcao) {
        if (opcao) {
            driver.quit();
            driver = null;
        }
    }
}
