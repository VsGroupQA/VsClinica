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
    }

    @AfterEach
    public void encerrarDriver() {
        Browser.fecharNavegador();
    }
    // TAREFA: Excluir todas as integrações / agendamentos criados 2. Verificar erro do cenario de teste 3. completar cenarios
    
    // Integração
    
    @Test
    public void criarIntegracao() {
    	String nomeIntegracao = "CRIAR INTEGRACAO - TESTE";
        Log.registrar("TESTE - Criar integração");
        integracao.grupoCriarIntegracao(
                "BUSCAR_EQUIPES",
                Access.urlBuscarEquipe, 
                Access.tokenOmnia,
                nomeIntegracao,
                null,
                3, 
                null
        );
        integracao.botaoSalvarIntegracao();
        integracao.validarNotificacao("Integração cadastrada");
        integracao.excluirIntegracao(nomeIntegracao);
    }
    
    @Test
    public void editarIntegracao() {
    	String nomeIntegracao = "EDITAR INTEGRACAO";
    	String nomeIntegracaoEditado = "(editado)";
    	
        Log.registrar("TESTE - Editar criação de integração");
        integracao.grupoCriarIntegracao(
                "BUSCAR_USUARIOS",
                Access.urlBuscarEquipe, 
                Access.tokenOmnia,
                nomeIntegracao,
                null,
                3, 
                null
        );
        integracao.botaoSalvarIntegracao();
        
        integracao.editarIntegracao(nomeIntegracao);
        integracao.nomeIntegracao(nomeIntegracaoEditado);
        integracao.botaoSalvarIntegracao();
        integracao.validarNotificacao("Integração cadastrada");
        
    }
    
    @Test
    public void desativarIntegracao() {
    	String nomeIntegracao = "DESATIVAR INTEGRACAO - TESTE";
        Log.registrar("TESTE - Criar integração");
        integracao.grupoCriarIntegracao(
                "BUSCAR_EQUIPES",
                Access.urlBuscarEquipe, 
                Access.tokenOmnia,
                nomeIntegracao,
                null,
                3, 
                null
        );
        integracao.botaoSalvarIntegracao();
        integracao.validarNotificacao("Integração cadastrada");
        integracao.desativarIntegracao(nomeIntegracao);
        integracao.validarNotificacao("Integração desativada");
        
    }
    
    @Test
    public void excluirIntegracao() {
    	String nomeIntegracao = "EXCLUIR INTEGRACAO - TESTE";
        Log.registrar("TESTE - Excluir integração");
        integracao.grupoCriarIntegracao(
                "BUSCAR_EQUIPES",
                Access.urlBuscarEquipe, 
                Access.tokenOmnia,
                nomeIntegracao,
                null,
                3, 
                null
        );
        integracao.botaoSalvarIntegracao();
        integracao.validarNotificacao("Integração cadastrada");
        
        integracao.excluirIntegracao(nomeIntegracao);
        integracao.validarNotificacao("Integração deletadao");
        
    }
    
    // Agendador
    
    @Test
    public void criarAgendador() {
    	String nomeIntegracao = "AGENDADOR - TESTE";
    	Log.registrar("TESTE - Criar novo agendador de disparo");
    	integracao.acessarIntegracao();
    	integracao.grupoAdicionarAgendador(nomeIntegracao);
    	integracao.botaoSalvarAgendador();
    	integracao.validarNotificacao("Tarefa agendada");
    	
    	// validar se esta funcionando > Remover integração criada
    }
    
    @Test
    public void editarAgendador() {
    	String nomeIntegracao = "EDITAR AGENDADOR - TESTE";
    	Log.registrar("TESTE - Editar agendador de disparo");
    	integracao.grupoAdicionarAgendador(nomeIntegracao);
    	integracao.botaoSalvarAgendador();
    	integracao.validarNotificacao("Tarefa agendada");

    	integracao.fecharAgendador();
    	integracao.editarAgendador(nomeIntegracao);
    	
    	integracao.descricaoAgendador("(editado)");
    	integracao.botaoSalvarAgendador();
    	integracao.validarNotificacao("Tarefa agendada");
    	
    } 
    
    @Test
    public void desativarAgendador() {
    	String nomeIntegracao = "DESATIVAR AGENDADOR - TESTE";
    	Log.registrar("TESTE - Desativar agendamento programado de disparo");
    	integracao.grupoAdicionarAgendador(nomeIntegracao);
    	integracao.botaoSalvarAgendador();
    	integracao.validarNotificacao("Tarefa agendada");

    	integracao.fecharAgendador();
    	integracao.desativarAgendamento(nomeIntegracao);
    	integracao.validarNotificacao("Tarefa desativada");
    	// excluir agendador criado
    }
    
    @Test
    public void excluirAgendador() {
    	String nomeAgendador = "EXCLUIR AGENDADOR - TESTE";
    	Log.registrar("TESTE - Excluir agendamento programado de disparo");
    	integracao.grupoAdicionarAgendador(nomeAgendador);
    	integracao.botaoSalvarAgendador();
    	integracao.validarNotificacao("Tarefa agendada");

    	integracao.fecharAgendador();
    	integracao.excluirAgendamento(nomeAgendador);
    	integracao.validarNotificacao("Tarefa agendada"); // Precisa ajustar o nome internamente
    	
    }
    
    
    // Cenário
    
    @Test
    public void cenarioDisparoAlertaAgendamento() {
    	String nomeIntegracao = "DISPARO AGENDAMENTO - TESTE";
    	String nomeAgendador = "AGENDADOR ALERTA - TESTE";
        Log.registrar("TESTE - Disparo para alerta de agendamento");
        
        // Cria agendamento
        agendamento.acessarInicio();
        agendamento.grupoNovoAgendamento(
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
        integracao.botaoSalvarIntegracao();
        
        // Validar
        integracao.validarNotificacao("Integração cadastrada");
        integracao.fecharNotficacao();
        
        // Criar agendador
        integracao.grupoAdicionarAgendador(nomeAgendador);
        integracao.botaoSalvarAgendador();
        integracao.validarNotificacao("Tarefa agendada");
        integracao.fecharModal();
        
        // Excluir
        integracao.excluirIntegracao(nomeIntegracao);
        integracao.excluirAgendamento(nomeAgendador);
    }
    
    @Test
    public void cenarioDisparoAlertaAgendamentoLeadOmnia() {
    	String nomeIntegracao = "DISPARO AGENDAMENTO - TESTE";
    	String nomeAgendador = "AGENDADOR ALERTA - TESTE";
        Log.registrar("TESTE - Disparo para alerta de agendamento (Criar Lead Omnia)");
        
        integracao.grupoBuscarEquipeOmnia();
        
        // Cria agendamento
        agendamento.acessarInicio();
        agendamento.grupoNovoAgendamento(
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
        integracao.botaoSalvarIntegracao();
        
        // Validar
        integracao.validarNotificacao("Integração cadastrada");
        integracao.fecharNotficacao();
        
        // Criar agendador
        integracao.grupoAdicionarAgendador(nomeAgendador);
        integracao.botaoSalvarAgendador();
        integracao.validarNotificacao("Tarefa agendada");
//        integracao.fecharModal();
        
    }
    
    @Test
    public void cenarioDisparoAgendamentoCancelado() {
    	
    }
    
    public void cenarioDisparoAlertaAgendamentoPorEmail() {
    	
    }
    
    public void cenarioDisparoAniversariantes() {
    	
    }
}

