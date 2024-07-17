package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Actions;
import java.util.List;

public class ProcedimentoPage {
    private WebDriver driver;
    private Actions actions;

    public ProcedimentoPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(this.driver);
    }

    public void acessarProcedimentos() {
        actions.esperar(3000);    
        actions.clicarBotaoPegandoPeloId("ROLE_CONFIGURACOES");
        actions.esperar(500);    
        actions.clicarBotaoPegandoPeloXpath("//*[@id=\"configuracoes\"]/div/div/div/div[2]");
        actions.esperar(1000);    
        actions.clicarBotaoPegandoPeloXpath("//tr[2]/td[6]/div/button/span");
    }
    
    public void validarProcedimentosEetapas() {
        acessarProcedimentos();

        List<WebElement> procedimentos = driver.findElements(By.cssSelector(".ng-star-inserted .grid .p-filled"));
        
        for (int i = 1; i <= procedimentos.size(); i++) {
            // Acessa o procedimento
            WebElement procedimento = driver.findElement(By.xpath("//li[" + i + "]/div/div/input"));
            procedimento.click();
            actions.esperar(1000);

            // Print do procedimento acessado
            System.out.println("Acessando procedimento " + i);

            // Acessa as etapas
            WebElement etapas = driver.findElement(By.xpath("//li[" + i + "]/div/div[5]/details/summary[contains(.,'Etapas')]"));
            etapas.click();
            actions.esperar(1000);

            // Verifica as etapas cadastradas
            List<WebElement> listaEtapas = driver.findElements(By.cssSelector("#p-accordiontab-" + (10 + i) + " .col-8 > span"));
            
            for (WebElement etapa : listaEtapas) {
                String nomeEtapa = etapa.getText();
                // Print da etapa acessada
                System.out.println("Procedimento " + i + " - Etapa: " + nomeEtapa);
            }
        }
    }
}
