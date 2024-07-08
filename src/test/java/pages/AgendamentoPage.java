package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Actions;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

public class AgendamentoPage {
    private WebDriver driver;
    private Actions actions;

    static DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public AgendamentoPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(this.driver);
    }

    public void modalAgendamento() {
        actions.clicarBotaoPegandoPeloXpath("/html/body/app-root/div/app-calendario/div/app-filtro-pacientes/p-card/div/div/div/div/div[6]/p-button/button");

    	
    }
    
    public void procedimento(String nomeItem) {
    	
        actions.clicarBotaoPegandoPeloXpath("/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[1]/p-dropdown/div/div[2]");
        List<WebElement> items = driver.findElements(By.xpath("/html/body/div[2]/div/div/div/ul/p-dropdownitem/li"));

       
        for (WebElement item : items) {
            if (item.getText().equalsIgnoreCase(nomeItem)) {
            	
                item.click();
                System.out.println("testeeeee");
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
    
    public void paciente (String nomePaciente) {
    	actions.escreverPegandoPeloXpath("//input[@name='paciente']", nomePaciente);
    	actions.esperar(1000);

    	actions.clicarBotaoPegandoPeloXpath("//li[contains(.,'"+ nomePaciente +"')]");
    	// adicironar um try except de NosuchElement = falha no teste
    }
    
    public void dataAgendamento () {
    	String dia = LocalDateTime.now().format(data);
    	actions.escreverPegandoPeloXpath("//*[@id=\"icon\"]", dia);
    }
    
    public void hora () {
    	// CONTINUAR DAQUI
    }
}
