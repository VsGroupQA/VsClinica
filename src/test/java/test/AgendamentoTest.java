package test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import utils.Log;
import utils.Access;
//import utils.Actions;
import utils.Browser;
import pages.AgendamentoPage;
import pages.loginPage;

public class AgendamentoTest {
	private static WebDriver driver;
	private loginPage loginPage;
//	private Actions actions;
	private AgendamentoPage agendamento;
	
    static DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm");
	String horario = LocalDateTime.now().format(hora);
	String dia = LocalDateTime.now().format(data);
    
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
//		actions = new Actions(driver);
		agendamento = new AgendamentoPage(driver);
	}

	@AfterEach
	public void encerrarDriver() {
        Browser.fecharNavegador();
	}

	@Test
	public void agendamento() {
		
		agendamento.modalAgendamento();
		agendamento.procedimento( "Transplante de Sombracelha");
		agendamento.profissional("VS GROUP");
		agendamento.compromisso("FINALIZADO");
		agendamento.paciente("Jhonata Venancio - VS GROUP");
		agendamento.dataAgendamento(dia);
		agendamento.horaAgendamento(horario);		
		agendamento.observacao(horario, dia);
		
		agendamento.criarAgendamento();
		agendamento.validarNotificacao();
	}
	
	public void agendamentoSemCamposObrigatorios () {
		
	}
	
	public void agendamentEmMassa() {
		
	}
	
	public void agendamentoMesmoHorario() {
		
	}
	
	public void agendamentoPelaLista() {
		
	}
	
	public void agendamentoPelaFicha() {
		
	}
}