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
import utils.Actions;
import utils.Browser;
import pages.AgendamentoPage;
import pages.IntegracoesPage;
import pages.LoginPage;

public class IntegracoesTest {

    private static WebDriver driver;
    private LoginPage loginPage;
    private IntegracoesPage integracao;
    private AgendamentoPage agendamento;
	private Actions actions;
    
    private static LocalDateTime agora = LocalDateTime.now();
    
    private static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter HORA_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm:SS");
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
        driver = Browser.iniciarNavegador(Access.navegador);
        driver.get(Access.url);
        loginPage = new LoginPage(driver);
        loginPage.signIn(Access.usuario, Access.senha);
        integracao = new IntegracoesPage(driver);
        agendamento = new AgendamentoPage(driver);
        actions = new Actions(driver);
    }

    @AfterEach
    public void encerrarDriver() {
//        Browser.fecharNavegador();
    }
    // TAREFA: Excluir todas as integrações / agendamentos criados 2. Verificar erro do cenario de teste 3. completar cenarios
    
    // Integração
    
    @Test
    public void criarIntegracao() {
    	String nomeIntegracao = "CRIAR INTEGRACAO";
        Log.registrar("TESTE - Criar integração");
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
        integracao.validarNotificacao("Integração cadastrada");
    }
    
    @Test
    public void editarIntegracao() {
    	String nomeIntegracao = "EDITAR INTEGRACAO";
    	String nomeIntegracaoEditado = "(editado)";
    	
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
        
        integracao.editarIntegracao(nomeIntegracao);
        integracao.nomeIntegracao(nomeIntegracaoEditado);
        integracao.botaoSalvar("/html/body/app-root/div/app-configuracoes/div/app-integracao/p-dialog/div/div/div[3]/form/div[8]/p-button[1]/button/span");
        integracao.validarNotificacao("Integração editada");
    }
    
    @Test
    public void desativarIntegracao() {
    	String nomeIntegracao = "DESATIVAR INTEGRACAO";
        Log.registrar("TESTE - Desativar integração");
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
        integracao.validarNotificacao("Integração cadastrada");
        integracao.fecharNotificacao();
        
        integracao.desativarIntegracao(nomeIntegracao);
        integracao.validarNotificacao("Integração desativada");
    }
    
    @Test
    public void excluirIntegracao() {
    	String nomeIntegracao = "EXCLUIR INTEGRACAO";
        Log.registrar("TESTE - Excluir integração");
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
        integracao.validarNotificacao("Integração cadastrada");

        integracao.fecharNotificacao();
        integracao.excluirIntegracao(nomeIntegracao);
        integracao.validarNotificacao("Integração deletado");
    }
    
    // Agendador
    
    @Test
    public void criarAgendamentoDeDisparo() {
    	String nomeAgendador = "CRIAR AGENDADOR";
    	Log.registrar("TESTE - Criar agendador de disparo");
		integracao.acessarConfig();
		integracao.acessarIntegracao();
    	integracao.adicionarAgendador(nomeAgendador);
    	integracao.botaoSalvar("//p-button/button/span");
    	integracao.validarNotificacao("Tarefa agendada");
    }
    
    @Test
    public void editarAgendador() {
    	String nomeAgendador = "EDITAR AGENDADOR";
    	Log.registrar("TESTE - Editar agendador de disparo");
		integracao.acessarConfig();
		integracao.acessarIntegracao();
    	
    	integracao.adicionarAgendador(nomeAgendador);
    	integracao.botaoSalvar("//p-button/button/span");
    	integracao.validarNotificacao("Tarefa agendada");
    	integracao.fecharNotificacao();
    	integracao.fecharModal("//p-button[2]/button/span");
    	
    	integracao.editarAgendador(nomeAgendador);
    	integracao.descricaoAgendador("(editado)");
    	integracao.botaoSalvar("//p-button/button/span");
    	integracao.validarNotificacao("Tarefa agendada");
    } 
    
    @Test
    public void desativarAgendador() {
    	String nomeAgendador = "DESATIVAR AGENDADOR";
    	Log.registrar("TESTE - Desativar agendador de disparo");
    	integracao.acessarConfig();
		integracao.acessarIntegracao();
    	
    	integracao.adicionarAgendador(nomeAgendador);
    	integracao.botaoSalvar("//p-button/button/span");
    	integracao.validarNotificacao("Tarefa agendada");
    	integracao.fecharNotificacao();
    	integracao.fecharModal("//p-button[2]/button/span");
    	
    	integracao.desativarAgendador(nomeAgendador);
    	integracao.validarNotificacao("Tarefa desativada");

    }
    
    @Test
    public void excluirAgendador() {
    	String nomeAgendador = "EXCLUIR AGENDADOR";
    	Log.registrar("TESTE - Excluir agendador de disparo");
    	integracao.acessarConfig();
		integracao.acessarIntegracao();

		integracao.adicionarAgendador(nomeAgendador);
    	integracao.botaoSalvar("//p-button/button/span");
    	integracao.validarNotificacao("Tarefa agendada");
    	integracao.fecharNotificacao();
    	integracao.fecharModal("//p-button[2]/button/span");

    	integracao.excluirAgendamento(nomeAgendador);
    	integracao.validarNotificacao("Agendamento deletado");
    	
    }
    
    
    // Cenário
    
 
    public void cenarioDisparoAlertaAgendamento() {
    	String nomeIntegracao = "DISPARO AGENDAMENTO - TESTE";
    	String nomeAgendador = "AGENDADOR ALERTA - TESTE";
        Log.registrar("TESTE - Disparo para alerta de agendamento");
        
        // Cria agendamento
        agendamento.criarAgendamento(
        		dataFormatada,
        		horaAtual,
        		horaMaisUm,
        		true
        );
        integracao.fecharNotficacao();
        
        // Criar integraçã
        integracao.grupoCriarIntegracao(
                "ALERTAR_AGENDAMENTOS",
                Access.urlIntegracao, 
                Access.tokenOmnia, 
                nomeIntegracao,
                Access.variavel,
                3, 
                null
        );
        integracao.grupoAdicionarTemplate(
                Access.procedimento, 
                Access.numeroDisparo, 
                Access.nomeTemplate
                
        );
        integracao.botaoSalvar("/html/body/app-root/div/app-configuracoes/div/app-integracao/p-dialog/div/div/div[3]/form/div[8]/p-button[1]/button/span");
        
        // Validar
        integracao.validarNotificacao("Integração cadastrada");
        integracao.fecharNotficacao();
        
        // Criar agendador
        integracao.grupoAdicionarAgendador(nomeAgendador);
        integracao.botaoSalvar("/html/body/app-root/div/app-configuracoes/div/app-integracao/p-dialog/div/div/div[3]/form/div[8]/p-button[1]/button/span");
        integracao.validarNotificacao("Tarefa agendada");
        integracao.fecharModal();
        
        // Excluir
        integracao.excluirIntegracao(nomeIntegracao);
        integracao.excluirAgendamento(nomeAgendador);
    }
    
//    @Test
    public void cenarioDisparoAlertaAgendamentoLeadOmnia() {
    	String nomeIntegracao = "DISPARO AGENDAMENTO - TESTE";
    	String nomeAgendador = "AGENDADOR ALERTA - TESTE";
        Log.registrar("TESTE - Disparo para alerta de agendamento (Criar Lead Omnia)");
        
        integracao.grupoBuscarEquipeOmnia();
        
        // Cria agendamento
        agendamento.navbar("ROLE_DASHBOARDS");
        agendamento.criarAgendamento(
        		dataFormatada,
        		horaAtual,
        		horaMaisUm,
        		true
        );
        integracao.fecharNotficacao();
        
        // Criar integração
        integracao.grupoCriarIntegracao(
                "ALERTAR_AGENDAMENTOS",
                Access.urlIntegracao, 
                Access.tokenOmnia, 
                nomeIntegracao,
                Access.variavel,
                1, 
                "Equipe Testes Gerais"
        );
        integracao.grupoAdicionarTemplate(
                Access.procedimento, 
                Access.numeroDisparo, 
                Access.nomeTemplate
                
        );
        integracao.botaoSalvar("/html/body/app-root/div/app-configuracoes/div/app-integracao/p-dialog/div/div/div[3]/form/div[8]/p-button[1]/button/span");
        
        // Validar
        integracao.validarNotificacao("Integração cadastrada");
        integracao.fecharNotficacao();
        
        // Criar agendador
        integracao.grupoAdicionarAgendador(nomeAgendador);
        integracao.botaoSalvar("/html/body/app-root/div/app-configuracoes/div/app-integracao/p-dialog/div/div/div[3]/form/div[8]/p-button[1]/button/span");
        integracao.validarNotificacao("Tarefa agendada");
//        integracao.fecharModal();
        
    }
    
//    @Test
    public void cenarioDisparoAgendamentoCancelado() {
    	String nomeIntegracao = "DISPARO AGENDAMENTO - TESTE";
        Log.registrar("TESTE - Disparo para alerta de agendamento");
        
        // Cria agendamento
        agendamento.criarAgendamento(
        		dataFormatada,
        		horaAtual,
        		horaMaisUm,
        		true
        );
        integracao.fecharNotficacao();
        
        // Criar integraçã
        integracao.criarIntegracao(
                "ALERTAR_AGENDAMENTOS",
                Access.urlIntegracao, 
                Access.tokenOmnia, 
                nomeIntegracao,
                Access.variavel,
                3, 
                null
        );
        integracao.grupoAdicionarTemplate(
                Access.procedimento, 
                Access.numeroDisparo, 
                Access.nomeTemplate
                
        );
        integracao.botaoSalvar("/html/body/app-root/div/app-configuracoes/div/app-integracao/p-dialog/div/div/div[3]/form/div[8]/p-button[1]/button/span");
        
        // Validar
        integracao.validarNotificacao("Integração cadastrada");
        integracao.fecharNotficacao();

        // Cancelar agendamento


    }
    
    public void cenarioDisparoAlertaAgendamentoPorEmail() {
    	String nomeIntegracao = "DISPARO EMAIL- TESTE";
    	String nomeAgendador = "AGENDADOR EMAIL - TESTE";
        Log.registrar("TESTE - Disparo para alerta de agendamento por EMAIL");
        
        // Cria agendamento
        agendamento.criarAgendamento(
        		dataFormatada,
        		horaAtual,
        		horaMaisUm,
        		true
        );
        integracao.fecharNotficacao();
        
        // Criar integração
        integracao.grupoCriarIntegracao(
                "NOTIFICACAO_VIA_EMAIL_LEMBRETE_AGENDAMENTO",
                Access.urlIntegracao, 
                Access.tokenOmnia, 
                nomeIntegracao,
                Access.variavel,
                3, 
                null
        );
        integracao.grupoAdicionarTemplate(
                Access.procedimento, 
                Access.numeroDisparo, 
                Access.nomeTemplate
                
        );
        integracao.botaoSalvar("/html/body/app-root/div/app-configuracoes/div/app-integracao/p-dialog/div/div/div[3]/form/div[8]/p-button[1]/button/span");
        
        // Validar
        integracao.validarNotificacao("Integração cadastrada");
        integracao.fecharNotficacao();
        
        // Criar agendador
        integracao.grupoAdicionarAgendador(nomeAgendador);
        integracao.botaoSalvarAgendador();
        integracao.validarNotificacao("Tarefa agendada");
        integracao.fecharModal();
        // trocar agendador por email
    }
    
    public void cenarioDisparoAniversariantes() {
    	String nomeIntegracao = "DISPARO ANIVERSARIO- TESTE";
    	String nomeAgendador = "AGENDADOR ANIVERSARIO - TESTE";
        Log.registrar("TESTE - Disparo para alerta ANIVERSARIANTE");
        
        // Criar um paciente com aniversario com dia de hoje
        
        // Criar integração
        integracao.grupoCriarIntegracao(
                "NOTIFICACAO_VIA_EMAIL_LEMBRETE_AGENDAMENTO",
                Access.urlIntegracao, 
                Access.tokenOmnia, 
                nomeIntegracao,
                Access.variavel,
                3, 
                null
        );
        integracao.grupoAdicionarTemplate(
                Access.procedimento, 
                Access.numeroDisparo, 
                Access.nomeTemplate
                
        );
        integracao.botaoSalvar("/html/body/app-root/div/app-configuracoes/div/app-integracao/p-dialog/div/div/div[3]/form/div[8]/p-button[1]/button/span");
        
        // Validar
        integracao.validarNotificacao("Integração cadastrada");
        integracao.fecharNotficacao();
        
        // Criar agendador
        integracao.grupoAdicionarAgendador(nomeAgendador);
        integracao.botaoSalvar("//p-button/button/span");
        integracao.validarNotificacao("Tarefa agendada");
    }
}

