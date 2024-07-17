package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import utils.Log;
import utils.Access;
import utils.Actions;
import utils.Browser;
import pages.ProcedimentoPage;
import pages.loginPage;

public class ProcedimentoTest {

	private static WebDriver driver;
	private loginPage loginPage;
	private Actions actions;
	private ProcedimentoPage procedimento;

	@BeforeAll
	public static void iniciarLog() {
		Log.criarArquivoLog("Log.Procedimentos");
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
		actions = new Actions(driver);
		procedimento = new ProcedimentoPage(driver);
	}

	@AfterEach
	public void encerrarDriver() {
//		Browser.fecharNavegador();
	}

	@Test
	public void teste() {
		actions.esperar(200);
		procedimento.acessarProcedimentos();
		 System.out.println("Verificando procediemntos");
		procedimento.validarProcedimentosEetapas();

	}
}