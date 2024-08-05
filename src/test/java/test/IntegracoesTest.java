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
    
    private static final String DISPARO_CANCELADO = "AAA";
    
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
        desativarTodasIntegracoesCriadas();
        Browser.fecharNavegador();
    }

    private void desativarTodasIntegracoesCriadas() {
        Log.registrar("Desativando todas as integrações criadas...");
        integracao.desativarIntegracao("BUSCAR_EQUIPES");
        integracao.desativarIntegracao("BUSCAR_USUARIOS");
        integracao.desativarIntegracao("ALERTAR_AGENDAMENTOS");
        integracao.desativarIntegracao("DISPARO DE AGENDAMENTO - TESTE");
        integracao.desativarIntegracao("ALERTA DE AGENDAMENTO - DISPARO");
        integracao.desativarIntegracao(DISPARO_CANCELADO);
        Log.registrar("Todas as integrações criadas foram desativadas.");
    }

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
    }

    @Test
    public void criarIntegracaoAlertaAgendamento() {
        Log.registrar("TESTE - Criar integração para alerta de agendamento");
        integracao.grupoCriarIntegracao(
                "ALERTAR_AGENDAMENTOS", 
                Access.urlIntegracao, 
                Access.tokenOmnia, 
                "ALERTA DE AGENDAMENTO - TESTE", 
                Access.variavel,
                2, 
                Access.usuarioOmnia
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
    public void disparoAlertaAgendamento() {
        Log.registrar("TESTE - Disparo para alerta de agendamento");
        integracao.grupoCriarIntegracao(
                "ALERTAR_AGENDAMENTOS",
                Access.urlIntegracao, 
                Access.tokenOmnia, 
                "ALERTA DE AGENDAMENTO - DISPARO",
                Access.variavel,
                2, 
                Access.usuarioOmnia
        );
        integracao.grupoAdicionarTemplate(
                Access.procedimento, 
                Access.numeroDisparo, 
                Access.nomeTemplate
        );
        integracao.botaoSalvarIntegracao();
        integracao.validarNotificacao("Integração cadastrada");
        
        agendamento.acessarInicio();
        agendamento.grupoNovoAgendamento(dataFormatada, horaAtual, horaMaisUm, true);
        integracao.grupoAdicionarAgendador("DISPARO DE AGENDAMENTO - TESTE");
        
        // ESPERA E VERIFICAÇÃO adicionar verificação no log
        Log.registrar("Verificação de disparo de agendamento concluído.");
    }

    @Test
    public void criarDisparoAgendamentoCancelado() {
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
       
    }
}
// 1. Verificar desativação  2. Adicionar novos metodos 3. corrigir testes
