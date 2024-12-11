package utils;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Assert;

public class Actions {

    private WebDriver driver;

    public Actions(WebDriver driver) {
        this.driver = driver;
    }

    private WebElement localizarElementoComTentativas(By by) {
        int tentativas = 0;
        while (tentativas < 3) {
            try {
                return driver.findElement(by);
            } catch (Exception e) {
                esperar(5000);
                tentativas++;
            }
        }
        Log.registrar("Elemento não localizado após 3 tentativas");
        throw new RuntimeException("Elemento não localizado após 3 tentativas: " + by.toString());
    }

    private void executarAcaoComTentativas(Runnable acao) {
        int tentativas = 0;
        while (tentativas < 3) {
            try {
                acao.run();
                
                return;
            } catch (Exception e) {
                esperar(1000);
                tentativas++;
            }
        }
        throw new RuntimeException("Ação falhou após várias tentativas");
    }

    // CSS
    public WebElement pegarElementoPeloCss(String css) {
        return localizarElementoComTentativas(By.cssSelector(css));
    }

    public void clicarBotaoPegandoPeloCss(String css) {
        executarAcaoComTentativas(() -> pegarElementoPeloCss(css).click());
    }

    public void escreverPegandoPeloCss(String css, String texto) {
        executarAcaoComTentativas(() -> pegarElementoPeloCss(css).sendKeys(texto));
    }

    public String lerTextoPegandoPeloCss(String css) {
        return localizarElementoComTentativas(By.cssSelector(css)).getText();
    }

    // ID
    public WebElement pegarElementoPeloId(String id) {
        return localizarElementoComTentativas(By.id(id));
    }

    public void clicarBotaoPegandoPeloId(String id) {
        executarAcaoComTentativas(() -> pegarElementoPeloId(id).click());
    }

    public void escreverPegandoPeloId(String id, String texto) {
        executarAcaoComTentativas(() -> pegarElementoPeloId(id).sendKeys(texto));
    }

    // XPATH
    public WebElement pegarElementoPeloXpath(String xpath) {
        return localizarElementoComTentativas(By.xpath(xpath));
    }
    
    public void clicarBtnXpathSemTratamentoErro (String xpath) {
    	 driver.findElement(By.xpath(xpath)).click();
    }

    public void clicarBotaoPegandoPeloXpath(String xpath) {
        executarAcaoComTentativas(() -> pegarElementoPeloXpath(xpath).click());
    }

    public void escreverPegandoPeloXpath(String xpath, String texto) {
        executarAcaoComTentativas(() -> pegarElementoPeloXpath(xpath).sendKeys(texto));
    }

    // NAME
    public WebElement pegarElementoPeloName(String name) {
        return localizarElementoComTentativas(By.name(name));
    }

    public void clicarBotaoPegandoPeloName(String name) {
        executarAcaoComTentativas(() -> pegarElementoPeloName(name).click());
    }

    public void escreverPegandoPeloName(String name, String texto) {
        executarAcaoComTentativas(() -> pegarElementoPeloName(name).sendKeys(texto));
    }

    // ESPERA
    public void esperar(int tempoEspera) {
        try {
            Thread.sleep(tempoEspera);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.err.println("Problema com a espera. Erro: " + e);
        }
    }

    // RECARREGAR PÁGINA
    public void recarregarPagina() {
        driver.navigate().refresh();
    }

    // Método de espera explícita
    public void esperarElementoVisivel(By by, int tempoEspera) {
        new WebDriverWait(driver, Duration.ofSeconds(tempoEspera))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    // NOTIFICACAO
    
    /**
     * Valida a notificação exibida após a ação.
     *
     * @param msg Mensagem esperada na notificação.
     */
    public void validarNotificacao(String msg) {
        executarAcaoComTentativas(() -> {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement notificacao = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-detail")));
            String textoNotificacao = notificacao.getText();
            Assert.assertEquals(msg, textoNotificacao);
            Log.registrar("Notificação de sucesso exibida: " + textoNotificacao);
        });
    }
    
    /**
     * Fechar notificação.
     */
    public void fecharNotificacao () {
    	esperar(300);
		clicarBotaoPegandoPeloCss(".p-toast-icon-close-icon");
	}
} 
