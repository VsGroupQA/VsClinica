package test;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import utils.Log;
import utils.Access;
import utils.Browser;
import pages.AgendamentoPage;
import pages.IntegracoesPage;
import pages.loginPage;

public class IntegracoesTest {

	private static WebDriver driver;
	private loginPage loginPage;
	private IntegracoesPage integracao;
	private AgendamentoPage agendamento;
	
	static DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
	
	@BeforeAll
	public static void iniciarLog() {
		Log.criarArquivoLog("Log.Integracoes");
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
		agendamento = new AgendamentoPage(driver);
	}

	@AfterEach
	public void encerrarDriver() {
//		Browser.fecharNavegador();
	}

	@Test
	public void criarIntegracaoBuscarEquipes() {
		Log.registrar("TESTE - Criar integração BUSCAR_EQUIPES");

		integracao.grupoCriarIntegracao("BUSCAR_EQUIPES", Access.urlBuscarEquipe, 
				Access.tokenOmnia, "BUSCAR EQUIPE - TESTE", null, 3, null);
		integracao.botaoSalvarIntegração();
		integracao.validarNotificacao("Integração cadastrada");
	}
	
	@Test
	public void criarIntegracaoBuscarUsuario() {
		Log.registrar("TESTE - Criar integração BUSCAR_USUARIOS");

		integracao.grupoCriarIntegracao("BUSCAR_USUARIOS", Access.urlBuscarUsuario, 
				Access.tokenOmnia, "BUSCAR USUARIO - TESTE", null, 3, null);
		integracao.botaoSalvarIntegração();
		integracao.validarNotificacao("Integração cadastrada");
	}
	
	@Test
	public void criarIntegracaoAlertaAgendamento() {
		Log.registrar("TESTE - Criar integração para alerta de agendamento");
		integracao.grupoCriarIntegracao("ALERTAR_AGENDAMENTOS", Access.urlIntegracao, 
				Access.tokenOmnia, "ALERTA DE AGENDAMENTO - TESTE", Access.variavel, 2, Access.usuarioOmnia);
		integracao.grupoAdicionarTemplate(Access.procedimento, Access.numeroDisparo, Access.nomeTemplate);
		integracao.botaoSalvarIntegração();
		integracao.validarNotificacao("Integração cadastrada");
	}
	
	@Test
	public void disparoAlertaAgendamento() {
		Log.registrar("TESTE - Disparo para alerta de agendamento");
//		integracao.grupoCriarIntegracao("ALERTAR_AGENDAMENTOS", Access.urlIntegracao, 
//				Access.tokenOmnia, "ALERTA DE AGENDAMENTO - DISPARO", Access.variavel, 2, Access.usuarioOmnia);
//		integracao.grupoAdicionarTemplate(Access.procedimento, Access.numeroDisparo, Access.nomeTemplate);
//		integracao.botaoSalvarIntegração();
//		integracao.validarNotificacao("Integração cadastrada");
		
		// modal adicionado em agendamento - remover
		
		agendamento.acessarInicio();
		agendamento.grupoNovoAgendamento("25/07/2024", "17:00", "17:01", true); // data, hora inicio, hora fim (hora atual)
		
		
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