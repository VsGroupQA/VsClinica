package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Actions {

    private WebDriver driver;

    public Actions(WebDriver driver) {
        this.driver = driver;
    }

    // CSS
    public WebElement pegarElementoPeloCss(String css) {
        return driver.findElement(By.cssSelector(css));
    }

    public void clicarBotaoPegandoPeloCss(String css) {
        driver.findElement(By.cssSelector(css)).click();
    }

    public void escreverPegandoPeloCss(String css, String texto) {
        driver.findElement(By.cssSelector(css)).sendKeys(texto);
    }
    
    public String lerTextoPegandoPeloCss(String css) {
        return driver.findElement(By.cssSelector(css)).getText();
    }

    // ID
    public WebElement pegarElementoPeloId(String id) {
        return driver.findElement(By.id(id));
    }

    public void clicarBotaoPegandoPeloId(String id) {
        driver.findElement(By.id(id)).click();
    }

    public void escreverPegandoPeloId(String id, String texto) {
        driver.findElement(By.id(id)).sendKeys(texto);
    }

    // XPATH
    public WebElement pegarElementoPeloXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public void clicarBotaoPegandoPeloXpath(String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    public void escreverPegandoPeloXpath(String xpath, String texto) {
        driver.findElement(By.xpath(xpath)).sendKeys(texto);
    }
    
    // NOME
    public WebElement pegarElementoPeloName(String name) {
        return driver.findElement(By.name(name));
    }
    
    public void clicarBotaoPegandoPeloName(String name) {
        driver.findElement(By.name(name)).click();
    }

    public void escreverPegandoPeloName(String name, String texto) {
        driver.findElement(By.name(name)).sendKeys(texto);
    }

    // ESPERA
    public void esperar(int tempoEspera) { // milesegundos
        try {
            Thread.sleep(tempoEspera);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.registrar("Problema com a esperada erro: "+ e +"");
        }
    }

    // RECARREGAR PÁGINA
    public void recarregarPagina() {
        driver.navigate().refresh();
    }
    
}