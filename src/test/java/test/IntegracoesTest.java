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
    
    private static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter HORA_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private LocalDate dataAmanha = LocalDate.now().plusDays(1);
    private String dataFormatada = dataAmanha.format(DATA_FORMATTER);
    private String horaMaisUm = agora.plusMinutes(1).format(HORA_FORMATTER);
    private String horaAtual = agora.format(HORA_FORMATTER);
    
    private static final String DISPARO_CANCELADO = "DISPARO";
    private static final String DISPARO_AGENDAMENTOS = "DISPARO";
    private static final String DISPARO_ANIVERSARIO = "DISPARO";
    private static final String DISPARO_EMAIL = "DISPARO";
    private static final String DISPARO_ALERTA_AGENDAMENTO = "DISPARO";
    
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
    
    // Partes
    
    @Test
    public void criarIntegracaoBuscarEquipes() {
        Log.registrar("TESTE - Criar integração BUSCAR_EQUIPES");
        integracao.grupoCriarIntegracao(
                "BUSCAR_EQUIPES",
                Access.urlBuscarEquipe, 
                Access.tokenOmnia,
                "BUSCAR EQUIPE - TESTE",
                null,
                3, 
                null
        );
        integracao.botaoSalvarIntegracao();
        integracao.validarNotificacao("Integração cadastrada");
    }

    @Test
    public void criarIntegracaoBuscarUsuarios() {
        Log.registrar("TESTE - Criar integração BUSCAR_USUARIOS");
        integracao.grupoCriarIntegracao(
                "BUSCAR_USUARIOS",
                Access.urlBuscarUsuario, 
                Access.tokenOmnia, 
                "BUSCAR USUARIO - TESTE",
                null, 
                3, 
                null
        );
        integracao.botaoSalvarIntegracao();
        integracao.validarNotificacao("Integração cadastrada");
        integracao.desativarIntegracao(DISPARO_CANCELADO);
    }

    @Test
    public void criarIntegracaoAlertaAgendamento() {
        Log.registrar("TESTE - Criar integração para alerta de agendamento");
        integracao.grupoCriarIntegracao(
                "ALERTAR_AGENDAMENTOS", 
                Access.urlIntegracao, 
                Access.tokenOmnia, 
                DISPARO_ALERTA_AGENDAMENTO, 
                Access.variavel,
                3, 
                null
        );
        integracao.grupoAdicionarTemplate(
                Access.procedimento, 
                Access.numeroDisparo, 
                Access.nomeTemplate
        );
        integracao.botaoSalvarIntegracao();
        integracao.validarNotificacao("Integração cadastrada");
    }
 
    @Test
    public void criarIntegracaoAgendamentoCancelado() {
        Log.registrar("TESTE - Criar integração para disparo de agendamento cancelado");
        integracao.grupoCriarIntegracao(
                "ALERTAR_AGENDAMENTO_CANCELADO",
                Access.urlIntegracao, 
                Access.tokenOmnia,
                DISPARO_CANCELADO, 
                Access.variavel,
                3, 
                null
        );
        integracao.botaoSalvarIntegracao();
        integracao.validarNotificacao("Integração cadastrada");
        integracao.desativarIntegracao(DISPARO_CANCELADO);
       
    }
    
    @Test
    public void criarIntegracaoAniversario() {
    	 Log.registrar("TESTE - Criar integração para disparo de aniversariante");
         integracao.grupoCriarIntegracao(
                 "NOTIFICACAO_ANIVERSARIANTES_WHATSAPP",
                 Access.urlIntegracao, 
                 Access.tokenOmnia,
                 DISPARO_ANIVERSARIO, 
                 Access.variavel,
                 3, 
                 null
         );
         integracao.botaoSalvarIntegracao();
         integracao.validarNotificacao("Integração cadastrada");
         integracao.desativarIntegracao(DISPARO_ANIVERSARIO);
        
     }
    
    @Test
    public void criarIntegracaoLembreteEmail() {
   	 Log.registrar("TESTE - Criar integração para disparo de aniversariante");
        integracao.grupoCriarIntegracao(
                "NOTIFICACAO_VIA_EMAIL_LEMBRETE_AGENDAMENTO",
                Access.urlIntegracao, 
                Access.tokenOmnia,
                DISPARO_EMAIL, 
                Access.variavel,
                3, 
                null
        );
        integracao.botaoSalvarIntegracao();
        integracao.validarNotificacao("Integração cadastrada");
        integracao.desativarIntegracao(DISPARO_EMAIL);
       
    }
    
    @Test
    public void criarAgendadorDisparo() {
    	Log.registrar("TESTE - Criar novo agendador de disparo");
    	integracao.acessarIntegracao();
    	integracao.grupoAdicionarAgendador("AGENDADOR - TESTE");
    	integracao.botaoSalvarAgendador();
    	integracao.validarNotificacao("Tarefa agendada");

    }
    
    // Cenário
    
    @Test
    public void disparoAlertaAgendamento() {
        Log.registrar("TESTE - Disparo para alerta de agendamento");
        
        agendamento.acessarInicio();
        agendamento.grupoNovoAgendamento(
        		dataFormatada,
        		horaAtual,
        		horaMaisUm,
        		true
        );
        integracao.grupoCriarIntegracao(
                "ALERTAR_AGENDAMENTOS",
                Access.urlIntegracao, 
                Access.tokenOmnia, 
                DISPARO_AGENDAMENTOS,
                Access.variavel,
                3, 
                null
        );
        integracao.grupoAdicionarTemplate(
                Access.procedimento, 
                Access.numeroDisparo, 
                Access.nomeTemplate
                
        );
        integracao.botaoSalvarIntegracao();
        integracao.validarNotificacao("Integração cadastrada");
        
        integracao.grupoAdicionarAgendador(DISPARO_AGENDAMENTOS);
        integracao.botaoSalvarAgendador();
        integracao.validarNotificacao("Tarefa agendada");
        
    }
 
    // 1. Desativar integracoes criadas 2. cenario adicionar equipes e usuario
}

