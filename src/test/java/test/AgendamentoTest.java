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
import pages.AgendamentoPage;
import pages.loginPage;

public class AgendamentoTest {
	private static WebDriver driver;
	private loginPage loginPage;
	private Actions actions;
	private AgendamentoPage agendamento;

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
		actions = new Actions(driver);
		agendamento = new AgendamentoPage(driver);
	}

	@AfterEach
	public void encerrarDriver() {
//        Browser.fecharNavegador();
	}

	@Test
	public void teste() {
		actions.esperar(100);

		agendamento.modalAgendamento();
		
		// procedimento - OK
		agendamento.procedimento( "Transplante de Sombracelha");
		System.out.println("procedimento");
		
		// profissional
		agendamento.profissional("VS GROUP");
		System.out.println("profissional");
		
		// compromisso
		agendamento.compromisso("FINALIZADO");
		System.out.println("compromisso");
		
		//paciente - OK
		agendamento.paciente("Jhonata Venancio - VS GROUP");

		// dia
		agendamento.dataAgendamento();
		System.out.println("dia");
		
		agendamento.hora();
		System.out.println("hora");
		
		agendamento.observacao();
		
		agendamento.criar();
	}
}