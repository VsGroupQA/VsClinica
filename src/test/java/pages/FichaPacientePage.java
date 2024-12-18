package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.Actions;
import utils.Log;


public class FichaPacientePage {
	private WebDriver driver;
	private Actions actions;

	public FichaPacientePage(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(this.driver);
	}
	
	public void acessarPacientes() {
		actions.esperarElementoVisivel(By.id("ROLE_PACIENTES"), 10);
		actions.clicarBotaoPegandoPeloId("ROLE_PACIENTES");
		Log.registrar("Tela de Usuários");
		}
	
	public void pesquisarPaciente (String paciente) {
		actions.esperar(400);
		actions.escreverPegandoPeloName("nomePaciente", paciente);
		Log.registrar("Pequisando: " + paciente);
	}
	
}