package test;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import utils.Log;
import utils.Access;
import utils.Browser;
import pages.IntegracoesPage;
import pages.loginPage;

public class IntegracoesTest {

	private static WebDriver driver;
	private loginPage loginPage;
	private IntegracoesPage integracao;

	@BeforeAll
	public static void iniciarLog() {
		Log.criarArquivoLog("Log.Agendamento");
	}

	@AfterAll
	public static void encerrarLog() {
		Log.encerrarLog();
	}

	@BeforeEach
	public void iniciaDriver() {
		driver = Browser.iniciarNavegador(Access.navegador);
		driver.get(Access.url);
		loginPage = new loginPage(driver);
		loginPage.signIn(Access.usuario, Access.senha);
		integracao = new IntegracoesPage(driver);
	}

	@AfterEach
	public void encerrarDriver() {
		Browser.fecharNavegador();
	}

	@Test
	public void criarIntegracao() {
		integracao.acessarIntegracao();
		integracao.xpto();

	}
	
	public void editarIntegracao() {
		
	}
	
	public void alertaAgendamento() {
		
	}
	
	public void alertaAgendamentoEmail() {
		
	}
	
	public void alertaAniversario() {
		
	}
	
	public void agendamentoCancelado() {
		
	}

}