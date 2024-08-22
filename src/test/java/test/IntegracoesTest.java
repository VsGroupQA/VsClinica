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
                Access.opcao, 
                Access.leadUsuario
        );
        integracao.botaoSalvarIntegracao();
        integracao.validarNotificacao("Integração cadastrada");
        integracao.deletarIntegracao(nomeIntegracao);
    }
    
    public void editarIntegracao() {
    	String nomeIntegracao = "EDITAR INTEGRACAO - TESTE";
        Log.registrar("TESTE - Criar integração");
        integracao.grupoCriarIntegracao(
                "BUSCAR_EQUIPES",
                Access.urlBuscarEquipe, 
                Access.tokenOmnia,
                nomeIntegracao,
                null,
                Access.opcao, 
                Access.leadUsuario
        );
        integracao.botaoSalvarIntegracao();
        integracao.validarNotificacao("Integração cadastrada");
        
        // Iterar nas integracaoes criadas > alterar o nome > salvar novamente > validar not
        
        integracao.deletarIntegracao(nomeIntegracao);
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
        integracao.deletarIntegracao(nomeIntegracao);
        integracao.validarNotificacao("Integração cadastrada"); // Mudar essa not nessa versão
        // Validar integração desativada e em seguida excluir
    }
    
    @Test
    public void excluirIntegracao() {
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
        integracao.deletarIntegracao(nomeIntegracao);
        integracao.validarNotificacao("Integração cadastrada");
        // trocar desativar por excluir
    }
    
    // Agendador
    
    @Test
    public void criarAgendador() {
    	Log.registrar("TESTE - Criar novo agendador de disparo");
    	integracao.acessarIntegracao();
    	integracao.grupoAdicionarAgendador("AGENDADOR - TESTE");
    	integracao.botaoSalvarAgendador();
    	integracao.validarNotificacao("Tarefa agendada");
    	// validar se esta funcionando > Remover integração criada
    }
    
    public void editarAgendador() {
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
        integracao.deletarIntegracao(nomeIntegracao);
        // editar agendamento
    } 
    
    public void desativarAgendador() {
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
        integracao.deletarIntegracao(nomeIntegracao);
        // desativar agendados
    }
    
    public void excluirAgendado() {
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
        integracao.deletarIntegracao(nomeIntegracao);
        // excluir agendador
    }
    
    
    // Cenário
    
    @Test
    public void cenarioDisparoAlertaAgendamento() {
    	String nomeIntegracao = "DISPARO AGENDAMENTO - TESTE";
    	String nomeAgendador = "AGENDADOR ALERTA - TESTE";
        Log.registrar("TESTE - Disparo para alerta de agendamento");
        
        // Cria Usuario Equipe
        integracao.grupoBuscarEquipeUsuarioOmnia();
        
        // Cria agendamento
        agendamento.acessarInicio();
        agendamento.grupoNovoAgendamento(
        		dataFormatada,
        		horaAtual,
        		horaMaisUm,
        		true
        );
        integracao.fecharNotficacao();
        integracao.grupoCriarIntegracao(
                "ALERTAR_AGENDAMENTOS",
                Access.urlIntegracao, 
                Access.tokenOmnia, 
                nomeIntegracao,
                Access.variavel,
                Access.opcao, 
                Access.leadUsuario
        );
        integracao.grupoAdicionarTemplate(
                Access.procedimento, 
                Access.numeroDisparo, 
                Access.nomeTemplate
                
        );
        integracao.botaoSalvarIntegracao();
        integracao.validarNotificacao("Integração cadastrada");
        integracao.fecharNotficacao();
        
        // Criar agendador
        integracao.grupoAdicionarAgendador(nomeAgendador);
        integracao.botaoSalvarAgendador();
        integracao.validarNotificacao("Tarefa agendada");
        integracao.fecharModal();
        
        // Excluir integrações criadas
        integracao.desativarIntegracao(nomeIntegracao);
        integracao.desativarAgendamento(nomeAgendador);
        // excluir todas integrações criadas
        
        integracao.deletarIntegracao("BUSCAR EQUIPE - TESTE");
        integracao.deletarIntegracao("BUSCAR USUARIO - TESTE");
    }
    
    @Test
    public void cenarioDisparoAgendamentoCancelado() {
    	String nomeIntegracao = "AGENDAMENTO CANCELADO- TESTE";
        Log.registrar("TESTE - Disparo para alerta de agendamento cancelado");
        
        // Cria Usuario Equipe
        integracao.grupoBuscarEquipeUsuarioOmnia();
        
        // Cria agendamento
        agendamento.acessarInicio();
        agendamento.grupoNovoAgendamento(
        		dataFormatada,
        		horaAtual,
        		horaMaisUm,
        		true
        );
        integracao.fecharNotficacao();
        integracao.grupoCriarIntegracao(
                "ALERTAR_AGENDAMENTOS",
                Access.urlIntegracao, 
                Access.tokenOmnia, 
                nomeIntegracao,
                Access.variavel,
                Access.opcao, 
                Access.leadUsuario

        );
        integracao.grupoAdicionarTemplate(
                Access.procedimento, 
                Access.numeroDisparo, 
                Access.nomeTemplate
                
        );
        integracao.botaoSalvarIntegracao();
        integracao.validarNotificacao("Integração cadastrada");
        integracao.fecharNotficacao();
        
        //CANCELAR AGENDAMENTO
        
        
        // Excluir integrações criadas
        integracao.desativarIntegracao(nomeIntegracao);
        // excluir todas integrações criadas
        integracao.deletarIntegracao("BUSCAR EQUIPE - TESTE");
        integracao.deletarIntegracao("BUSCAR USUARIO - TESTE");
    	
    }
    
}

