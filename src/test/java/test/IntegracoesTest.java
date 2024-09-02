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
                Access.opcao, 
                Access.leadUsuario
        );
        integracao.botaoSalvarIntegracao();
        integracao.validarNotificacao("Integração cadastrada");
        integracao.excluirIntegracao(nomeIntegracao);
    }
    
    @Test
    public void editarIntegracao() {
    	String nomeIntegracao = "EDITAR INTEGRACAO - TESTE";
    	String nomeIntegracaoEditado = "(editado)";
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
    	String nomeIntegracao = "EXCLUIR AGENDADOR - TESTE";
    	Log.registrar("TESTE - Excluir agendamento programado de disparo");
    	integracao.grupoAdicionarAgendador(nomeIntegracao);
    	integracao.botaoSalvarAgendador();
    	integracao.validarNotificacao("Tarefa agendada");

    	integracao.fecharAgendador();
    	integracao.excluirAgendamento(nomeIntegracao);
    	integracao.validarNotificacao("Agendamento deletado");
    	
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
        
        integracao.excluirIntegracao("BUSCAR EQUIPE - TESTE");
        integracao.excluirIntegracao("BUSCAR USUARIO - TESTE");
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
        
        //Notificação atrapalhando o click 
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
        
        // Excluir integrações criadas
        integracao.desativarIntegracao(nomeIntegracao);
        // excluir todas integrações criadas
        integracao.excluirIntegracao("BUSCAR EQUIPE - TESTE");
        integracao.excluirIntegracao("BUSCAR USUARIO - TESTE");
    	
    }
    
    public void cenarioDisparoAlertaAgendamentoPorEmail() {
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
        
        integracao.excluirIntegracao("BUSCAR EQUIPE - TESTE");
        integracao.excluirIntegracao("BUSCAR USUARIO - TESTE");
    }
    
    public void cenarioDisparoAniversariantes() {
    	String nomeIntegracao = "DISPARO AGENDAMENTO - TESTE";
    	String nomeAgendador = "AGENDADOR ALERTA - TESTE";
        Log.registrar("TESTE - Disparo para alerta de agendamento");
        
        // Cria Usuario Equipe
        integracao.grupoBuscarEquipeUsuarioOmnia();
        
        // Criar usuário com aniversario de hoje
        
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
        
        integracao.excluirIntegracao("BUSCAR EQUIPE - TESTE");
        integracao.excluirIntegracao("BUSCAR USUARIO - TESTE");
    }
}

