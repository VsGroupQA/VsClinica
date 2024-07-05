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
        actions.clicarBotaoPegandoPeloXpath("/html/body/app-root/div/app-calendario/div/app-filtro-pacientes/p-card/div/div/div/div/div[6]/p-button/button");

    	
    }
    
    public void procedimento(String campo, String itensDrop, String nomeItem) {
    	
        actions.clicarBotaoPegandoPeloXpath(campo);
        System.out.println("1");
        List<WebElement> items = driver.findElements(By.xpath(itensDrop));
    
        System.out.println("2");
       
        for (WebElement item : items) {
            if (item.getText().equalsIgnoreCase(nomeItem)) {
            	
                item.click();
                System.out.println("3");
                break; 
                
            
            }
        }
    }
    
    public void profissional () {
    	// abre dorpdown
    	actions.clicarBotaoPegandoPeloCss(".px-0 .p-dropdown-label");
    	// selecionar item
    	actions.clicarBotaoPegandoPeloCss(".p-element:nth-child(13) > .p-dropdown-item");
    	
    }
    
    public void compromisso () {
    	// abre dropdown
    	actions.clicarBotaoPegandoPeloCss("p-dropdown.ng-pristine:nth-child(2) > div:nth-child(1) > div:nth-child(3)");
    	// seleciona item
    	actions.clicarBotaoPegandoPeloCss("p-dropdownitem.p-element:nth-child(2) > li:nth-child(1)");
    }
}
