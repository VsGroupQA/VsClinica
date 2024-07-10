package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
    static DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:m");
    
    public AgendamentoPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(this.driver);
    }

    public void modalAgendamento() {
        actions.clicarBotaoPegandoPeloXpath("/html/body/app-root/div/app-calendario/div/app-filtro-pacientes/p-card/div/div/div/div/div[5]/p-button/button");
    }
    
    public void procedimento(String nomeItem) {
        actions.clicarBotaoPegandoPeloXpath("/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[1]/p-dropdown/div/div[2]");
        List<WebElement> items = driver.findElements(By.xpath("/html/body/div[2]/div/div/div/ul/p-dropdownitem/li"));

        for (WebElement item : items) {
            if (item.getText().equalsIgnoreCase(nomeItem)) {
                item.click();
                break; 
            }
        }
    }
    
    public void profissional(String nomeProfissional) {
        // Abre dropdown
        actions.clicarBotaoPegandoPeloCss(".px-0 .p-dropdown-label");
        
        // Itera sobre os itens do dropdown e seleciona o profissional desejado
        List<WebElement> profissionais = driver.findElements(By.cssSelector(".p-element .p-dropdown-item"));
        for (WebElement profissional : profissionais) {
            if (profissional.getText().equalsIgnoreCase(nomeProfissional)) {
                profissional.click();
                break;
            }
        }
    }
    
    public void compromisso(String nomeCompromisso) {
        // Abre dropdown
        actions.clicarBotaoPegandoPeloCss("p-dropdown.ng-pristine:nth-child(2) > div:nth-child(1) > div:nth-child(3)");
        
        // Itera sobre os itens do dropdown e seleciona o compromisso desejado
        List<WebElement> compromissos = driver.findElements(By.cssSelector("p-dropdownitem.p-element > li"));
        for (WebElement compromisso : compromissos) {
            if (compromisso.getText().equalsIgnoreCase(nomeCompromisso)) {
                compromisso.click();
                break;
            }
        }
    }
    
    public void paciente(String nomePaciente) {
        try {
            actions.escreverPegandoPeloXpath("//input[@name='paciente']", nomePaciente);
            actions.esperar(1000);
            actions.clicarBotaoPegandoPeloXpath("//li[contains(.,'" + nomePaciente + "')]");
        } catch (NoSuchElementException e) {
            System.err.println("Paciente não encontrado: " + nomePaciente);
            throw e;
        }
    }
    
    public void dataAgendamento() {
        String dia = LocalDateTime.now().format(data);
        actions.escreverPegandoPeloXpath("//*[@id=\"icon\"]", dia);
    }
    
    public void hora() {
    	String horario = LocalDateTime.now().format(hora);
    	
        // Hora início
        actions.escreverPegandoPeloXpath("//div[5]/div/p-calendar/span/input", horario);
        System.out.println("adiciondo hora inicio" + horario);
        
        // Hora fim
        actions.escreverPegandoPeloXpath("//div[2]/p-calendar/span/input", horario);
        System.out.println("adiciondo hora fim" + horario + "aa");
    }
    
    // validar se hora é valida
    public void validarHorario () {
    	
    }
    
    public void criar() {
    	actions.clicarBotaoPegandoPeloXpath("/html/body/div/div/div[2]/app-modal-agendamento/form/div[3]/p-button/button");
    }
    
    public void observacao() {
    	String horaObs = LocalDateTime.now().format(hora);
    	
    	actions.escreverPegandoPeloXpath("/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[10]/span/textarea",
    			"TESTE DE AGENDAMENTO" + horaObs);
    }
}
