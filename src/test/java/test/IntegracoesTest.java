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
import pages.LoginPage;

public class IntegracoesTest {

    private static WebDriver driver;
    private LoginPage loginPage;
    private IntegracoesPage integracao;
    private AgendamentoPage agendamento;
    
    private static LocalDateTime agora = LocalDateTime.now();
    
    private static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter HORA_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
//    private static final DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm:SS");
    private LocalDate dataAmanha = LocalDate.now().plusDays(1);
    private String dataFormatada = dataAmanha.format(DATA_FORMATTER);
    private String horaMaisUm = agora.plusMinutes(1).format(HORA_FORMATTER);
    private String horaAtual = agora.format(HORA_FORMATTER);
    
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
        driver = Browser.iniciarNavegador(Access.navegador, Access.headless);
        driver.get(Access.url);
        loginPage = new LoginPage(driver);
        loginPage.signIn(Access.usuario, Access.senha);
        integracao = new IntegracoesPage(driver);
        agendamento = new AgendamentoPage(driver);
    }

    @AfterEach
    public void encerrarDriver() {
        Browser.fecharNavegador(Access.quit);
    }
    
    // Integração
    
    @Test
    public void criarIntegracao() {
    	String nomeIntegracao = "CRIAR INTEGRACAO";
        Log.registrar("TESTE - Criar integração");
        // Criar
        integracao.criarIntegracao(
                "BUSCAR_EQUIPES",
                Access.urlBuscarEquipe, 
                Access.tokenOmnia,
                nomeIntegracao,
                null,
                3, 
                null
        );
        integracao.botaoSalvar("/html/body/app-root/div/app-configuracoes/div/app-integracao/p-dialog/div/div/div[3]/form/div[8]/p-button[1]/button/span");
        // Validar
        integracao.validarNotificacao("Integração cadastrada");
    }
    
    @Test
    public void editarIntegracao() {
    	String nomeIntegracao = "EDITAR INTEGRACAO";
    	String nomeIntegracaoEditado = "(editado)";
    	
    	//Criar
        Log.registrar("TESTE - Editar integração");
        integracao.criarIntegracao(
                "BUSCAR_USUARIOS",
                Access.urlBuscarEquipe, 
                Access.tokenOmnia,
                nomeIntegracao,
                null,
                3, 
                null
        );
        integracao.botaoSalvar("/html/body/app-root/div/app-configuracoes/div/app-integracao/p-dialog/div/div/div[3]/form/div[8]/p-button[1]/button/span");
        integracao.fecharNotificacao();
        
        // Editar
        integracao.editarIntegracao(nomeIntegracao);
        integracao.nomeIntegracao(nomeIntegracaoEditado);
        integracao.botaoSalvar("/html/body/app-root/div/app-configuracoes/div/app-integracao/p-dialog/div/div/div[3]/form/div[8]/p-button[1]/button/span");
        // Validar
        integracao.validarNotificacao("Integração editada");
    }
    
    @Test
    public void desativarIntegracao() {
    	String nomeIntegracao = "DESATIVAR INTEGRACAO";
        Log.registrar("TESTE - Desativar integração");
        // Criar
        integracao.criarIntegracao(
                "BUSCAR_EQUIPES",
                Access.urlBuscarEquipe, 
                Access.tokenOmnia,
                nomeIntegracao,
                null,
                3, 
                null
        );
        integracao.botaoSalvar("/html/body/app-root/div/app-configuracoes/div/app-integracao/p-dialog/div/div/div[3]/form/div[8]/p-button[1]/button/span");
        integracao.fecharNotificacao();
        
        // Desativar
        integracao.desativarIntegracao(nomeIntegracao);
        // Validar
        integracao.validarNotificacao("Integração desativada");
    }
    
    @Test
    public void excluirIntegracao() {
    	String nomeIntegracao = "EXCLUIR INTEGRACAO";
        Log.registrar("TESTE - Excluir integração");
        
        // Criar 
        integracao.criarIntegracao(
                "BUSCAR_EQUIPES",
                Access.urlBuscarEquipe, 
                Access.tokenOmnia,
                nomeIntegracao,
                null,
                3, 
                null
        );
        integracao.botaoSalvar("/html/body/app-root/div/app-configuracoes/div/app-integracao/p-dialog/div/div/div[3]/form/div[8]/p-button[1]/button/span");
        integracao.fecharNotificacao();
        
        // Excluir
        integracao.excluirIntegracao(nomeIntegracao);
        
        // Validar
        integracao.validarNotificacao("Integração deletado");
    }
    
    // Agendador
    
    @Test
    public void criarAgendador() {
    	String nomeAgendador = "CRIAR AGENDADOR";
    	Log.registrar("TESTE - Criar agendador de disparo");
    	
    	// Criar agendador
    	integracao.adicionarAgendador(nomeAgendador);
    	integracao.botaoSalvar("//p-button/button/span");
    	// Validar
    	integracao.validarNotificacao("Tarefa agendada");
    	// Exluir
    	integracao.fecharNotificacao();
    	integracao.fecharModal("//p-button[2]/button/span");
    	integracao.excluirAgendamento(nomeAgendador);
    }
    
    @Test
    public void editarAgendador() {
    	String nomeAgendador = "EDITAR AGENDADOR";
    	Log.registrar("TESTE - Editar agendador de disparo");

    	// Criar
    	integracao.adicionarAgendador(nomeAgendador);
    	integracao.botaoSalvar("//p-button/button/span");
    	integracao.validarNotificacao("Tarefa agendada");
    	// Editar
    	integracao.fecharNotificacao();
    	integracao.fecharModal("//p-button[2]/button/span");
    	integracao.editarAgendador(nomeAgendador);
    	integracao.descricaoAgendador("(editado)");
    	integracao.botaoSalvar("//p-button/button/span");
    	// Validar
    	integracao.validarNotificacao("Tarefa agendada");
    	// Exluir
    	integracao.excluirAgendamento(nomeAgendador);
    } 
    
    @Test
    public void desativarAgendador() {
    	String nomeAgendador = "DESATIVAR AGENDADOR";
    	Log.registrar("TESTE - Desativar agendador de disparo");
    	
    	// Criar
    	integracao.adicionarAgendador(nomeAgendador);
    	integracao.botaoSalvar("//p-button/button/span");
    	integracao.validarNotificacao("Tarefa agendada");
    	// Desativar
    	integracao.fecharNotificacao();
    	integracao.fecharModal("//p-button[2]/button/span");
    	integracao.desativarAgendador(nomeAgendador);
    	// Validar
    	integracao.validarNotificacao("Tarefa desativada");
    	integracao.fecharNotificacao();
    	// Exluir
    	integracao.excluirAgendamento(nomeAgendador);
    }
    
    @Test
    public void excluirAgendador() {
    	String nomeAgendador = "EXCLUIR AGENDADOR";
    	Log.registrar("TESTE - Excluir agendador de disparo");
    	
    	// Criar
		integracao.adicionarAgendador(nomeAgendador);
    	integracao.botaoSalvar("//p-button/button/span");
    	// Validar
    	integracao.validarNotificacao("Tarefa agendada");
    	// Excluir
    	integracao.fecharNotificacao();
    	integracao.fecharModal("//p-button[2]/button/span");
    	integracao.excluirAgendamento(nomeAgendador);
    	
    }
    
    
    // Cenário
    // Fazer com BDD CUCUMBER
    
    public void disparoAlertaAgendamento() {
    	String nomeIntegracao = "DISPARO AGENDAMENTO";
    	String nomeAgendador = "AGENDADOR ALERTA";
        Log.registrar("TESTE - Disparo para alertar de agendamento próximo");
        
        // Agendamento criado para o dia atual
        agendamento.criarAgendamento(
        		dataFormatada,
        		horaAtual,
        		horaMaisUm,
        		true
        );
        integracao.fecharNotificacao();
        
        // Criar integração de disparo
        integracao.criarIntegracao(
                "ALERTAR_AGENDAMENTOS",
                Access.urlIntegracao, 
                Access.tokenOmnia, 
                nomeIntegracao,
                Access.variavel,
                3, 
                null
        );
        //Adiciona template
        integracao.adicionarTemplate(
                Access.procedimento, 
                Access.numeroDisparo, 
                Access.nomeTemplate
                
        );
        integracao.botaoSalvar("/html/body/app-root/div/app-configuracoes/div/app-integracao/p-dialog/div/div/div[3]/form/div[8]/p-button[1]/button/span");
        
        // Validar
        integracao.validarNotificacao("Integração cadastrada");
        integracao.fecharNotificacao();
        
        // Criar agendador
        integracao.adicionarAgendador(nomeAgendador);
        integracao.botaoSalvar("/html/body/app-root/div/app-configuracoes/div/app-integracao/p-dialog/div/div/div[3]/form/div[8]/p-button[1]/button/span");
        integracao.validarNotificacao("Tarefa agendada");
        integracao.fecharModal("//p-button[2]/button/span");

    }
    
//    @Test
    public void cenarioDisparoAlertaAgendamentoLeadOmnia() {
    	
    }
    
//    @Test
    public void cenarioDisparoAgendamentoCancelado() {
    	

    }
    
    public void cenarioDisparoAlertaAgendamentoPorEmail() {
    	
    }
    
    public void cenarioDisparoAniversariantes() {
    	
    }
}

