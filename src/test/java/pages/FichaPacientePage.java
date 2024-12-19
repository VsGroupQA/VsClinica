package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Actions;
import utils.Log;


public class FichaPacientePage {
	private WebDriver driver;
	private Actions actions;
	private WebDriverWait wait;

	public FichaPacientePage(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(this.driver);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void acessarPacientes() {
		actions.esperarElementoVisivel(By.id("ROLE_PACIENTES"), 10);
		actions.clicarBotaoPegandoPeloId("ROLE_PACIENTES");
		Log.registrar("Tela de Usuários");
		}
	
	public void pesquisarPaciente (String paciente) {
		actions.esperar(500);
		actions.escreverPegandoPeloName("nomePaciente", paciente);
		Log.registrar("Pequisando: " + paciente);
	}
	
	public void acessarPaciente(String nomeEsperado) {
		actions.esperar(500);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
	    Log.registrar("Tabela localizada");

	    List<WebElement> linhas = driver.findElements(By.xpath("//table/tbody/tr"));

	    for (int i = 1; i <= linhas.size(); i++) {
	        try {
	            WebElement nomeColuna = linhas.get(i - 1).findElement(By.xpath("./td[3]"));
	            String txt = nomeColuna.getText();
	            Log.registrar("Nome na linha: " + i + ": " + txt);

	            if (txt.equals(nomeEsperado)) {
	                Log.registrar("Paciente encontrado: " + txt);
	                WebElement botaoEditar = linhas.get(i - 1).findElement(By.cssSelector(".ng-star-inserted:nth-child("+i+") > td:nth-child(3)"));
	                actions.esperar(500);
	                botaoEditar.click();
	                Log.registrar("Botão de edição clicado");
	                break;
	            }
	        } catch (NoSuchElementException e) {
	            Log.registrar("Elemento não encontrado na linha " + i + ": " + e.getMessage());
	        }
	    } 
	}
	
	
	public void editarPaciente() {
		actions.esperar(300);
		actions.clicarBotaoPegandoPeloCss(".pi-pencil");
	}
	
	public void obsevacaoModal(String obs) {
		actions.escreverPegandoPeloName("observacoes", obs);
	}
	
	public void salvarModalPaciente() {
		actions.clicarBotaoPegandoPeloCss(".col-3 .p-button-label");
	}
	
	public void preencherObservacao (String obs) {
		actions.clicarBotaoPegandoPeloXpath("//div[2]/div/form/ul/li/div/div[2]/textarea");
		actions.escreverPegandoPeloXpath("//div[2]/div/form/ul/li/div/div[2]/textarea", obs);
        Log.registrar("Prenchimento: "+ obs +""); 
	}

	
	
	
}