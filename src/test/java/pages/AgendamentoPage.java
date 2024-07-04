package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Actions;
import java.util.List;

public class AgendamentoPage {
    private WebDriver driver;
    private Actions actions;

    public AgendamentoPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(this.driver);
    }

    public void modalAgendamento() {
    	// abrir novo agendamento
        actions.clicarBotaoPegandoPeloXpath("/html/body/app-root/div/app-calendario/div/app-filtro-pacientes/p-card/div/div/div/div/div[6]/p-button/button");

    	
    }
    
    public void dropdown(String campo, String itensDrop, String nomeItem) {
    	
        // abrir dropdown
        actions.clicarBotaoPegandoPeloXpath(campo);
        // /html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[1]/p-dropdown/div/div[2]
        
        // obter todos os itens do dropdown
        List<WebElement> items = driver.findElements(By.xpath(itensDrop));
        // /html/body/div[2]/div/div/div/ul/p-dropdownitem/li

        // iterar sobre os itens do dropdown
        for (WebElement item : items) {
            if (item.getText().equalsIgnoreCase(nomeItem)) {
            	// Transplante de Sombracelha
                item.click();
                break; // adicionar if
            }
        }
    }
}
