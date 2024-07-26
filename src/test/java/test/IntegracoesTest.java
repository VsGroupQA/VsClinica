package test;

import java.time.LocalDate;
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
import utils.Browser;
import pages.AgendamentoPage;
import pages.IntegracoesPage;
import pages.loginPage;

public class IntegracoesTest {

    private static WebDriver driver;
    private loginPage loginPage;
    private IntegracoesPage integracao;
    private AgendamentoPage agendamento;
    
    private static LocalDateTime agora = LocalDateTime.now();
    
    static DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");
    LocalDate dataAmanha = LocalDate.now().plusDays(1);
    String dataFormatada = dataAmanha.format(dataFormatter);
    String horaMaisUm = agora.plusMinutes(1).format(horaFormatter);
    String horaAtual = agora.format(horaFormatter);
    
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
        Browser.fecharNavegador();
    }
    
    /*TESTE COMPONENTES*/

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
    
    /*TESTE CENÁRIO*/
    
    @Test
    public void disparoAlertaAgendamento() {
        Log.registrar("TESTE - Disparo para alerta de agendamento");
        
        // validar integração de buscar antes
//		integracao.grupoCriarIntegracao("ALERTAR_AGENDAMENTOS", Access.urlIntegracao, 
//				Access.tokenOmnia, "ALERTA DE AGENDAMENTO - DISPARO", Access.variavel, 2, Access.usuarioOmnia);
//		integracao.grupoAdicionarTemplate(Access.procedimento, Access.numeroDisparo, Access.nomeTemplate);
//		integracao.botaoSalvarIntegração();
//		integracao.validarNotificacao("Integração cadastrada");
		
//		integracao.deletarIntegracao();
 		
 		// modal adicionado em agendamento - remover

        agendamento.acessarInicio();
        agendamento.grupoNovoAgendamento(dataFormatada, horaAtual, horaMaisUm, true); // data, hora inicio, hora fim (hora atual)
   
        integracao.grupoAdicionarAgendador("Novo");
        // abrir nova aba logado no omnia
    }
   
}
