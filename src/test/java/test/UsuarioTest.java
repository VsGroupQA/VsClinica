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
import utils.Actions;
import utils.Browser;
import pages.UsuarioPage;
import pages.LoginPage;

public class UsuarioTest {

    private static WebDriver driver;
    private LoginPage loginPage;
    private UsuarioPage usuario;
    private Actions actions;

    
    private LocalDateTime agora = LocalDateTime.now();
    private static final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HHmmSS");
    private static final DateTimeFormatter formatoDia = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String hora = agora.format(formatoHora);
    String dia = agora.format(formatoDia);
    
    
    
    @BeforeAll
    public static void iniciarLog() {
        Log.criarArquivoLog("Log.Usuario");
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
        usuario = new UsuarioPage(driver);
        actions = new Actions(driver);
    }

    @AfterEach
    public void encerrarDriver() {
        Browser.fecharNavegador(Access.quit);
    }
    
    @Test
    public void criarUsuarioMedico() {
    	String user = "user" + hora;
    	String nome = "Testevaldo " + hora;
    	Log.registrar("===== TESTE REALIZADO ===== - CRIAR USUÁRIO MEDICO");
    	
    	usuario.acessarUsuarios();
    	usuario.criarUsuario(
    			nome, 
    			"teste@teste.com", 
    			user, 
    			"123", 
    			"Default"
    			);
    	usuario.botaoSalvar(true);
    	actions.validarNotificacao("Cadastro criado com sucesso");
    }
    
    @Test
    public void criarUsuarioDuplicado() {
    	String user = "user" + hora;
    	String nome = "Testevaldo " + hora;
    	
    	Log.registrar("===== TESTE REALIZADO ===== - CRIAR USUÁRIO MEDICO DUPLICADO");
    	
    	usuario.acessarUsuarios();
    	usuario.criarUsuario(
    			nome, 
    			"teste@teste.com", 
    			user, 
    			"123", 
    			"Default"
    			);
    	usuario.botaoSalvar(true);
    	actions.validarNotificacao("Cadastro criado com sucesso");
    	actions.fecharNotificacao();
    	// Duplicado
    	usuario.criarUsuario(
    			nome, 
    			"teste@teste.com", 
    			user, 
    			"123", 
    			"Default"
    			);
    	usuario.botaoSalvar(true);
    	actions.validarNotificacao("login "+ user +" já existente.");
    }
    
    @Test
    public void editarUsuarioMedico () {
    	String user = "user" + hora;
    	String nome = "Testevaldo " + hora;
    	
    	Log.registrar("===== TESTE REALIZADO ===== - EDITAR USUÁRIO MEDICO");
    	usuario.acessarUsuarios();
    	usuario.criarUsuario(
    			nome, 
    			"teste@teste.com", 
    			user, 
    			"123", 
    			"Default"
    			);
    	usuario.botaoSalvar(true);
    	actions.validarNotificacao("Cadastro criado com sucesso");
    	actions.fecharNotificacao();
    	
    	usuario.pesquisarUsuario(nome);
    	usuario.editarUsuario(nome, "./td[6]/div/button");
    	usuario.observacaoModal("EDITADO POR TESTE AUTOMATIZADO");
    	usuario.botaoSalvar(true);
    	actions.validarNotificacao("Cadastro atualizado com sucesso");
    
    }
    
    @Test
    public void excluirUsuarioMedico () {
    	String user = "user" + hora;
    	String nome = "Testevaldo " + hora;
    	
    	Log.registrar("===== TESTE REALIZADO ===== - EXCLUIR USUÁRIO MEDICO");
    	usuario.acessarUsuarios();
    	usuario.criarUsuario(
    			nome, 
    			"teste@teste.com", 
    			user, 
    			"123", 
    			"Default"
    			);
    	usuario.botaoSalvar(true);
    	actions.validarNotificacao("Cadastro criado com sucesso");
    	actions.fecharNotificacao();
    	
    	usuario.pesquisarUsuario(nome);
    	usuario.excluirUsuario(nome);
    	actions.validarNotificacao("Profissional excluído com sucesso.");
    
    }
    
    @Test
    public void criarEscalaMedica() {
    	String user = "user" + hora;
    	String nome = "Testevaldo " + hora;
    	
    	Log.registrar("===== TESTE REALIZADO ===== - CRIAR ESCALA MEDICA");
    	usuario.acessarUsuarios();
    	usuario.criarUsuario(
    			nome, 
    			"teste@teste.com", 
    			user, 
    			"123", 
    			"Default"
    			);
    	usuario.botaoSalvar(true);
    	actions.validarNotificacao("Cadastro criado com sucesso");
    	actions.fecharNotificacao();
    	
    	usuario.pesquisarUsuario(nome);
    	usuario.acessarEscalaProfissional(nome);
    	usuario.criarEscala("8:00", "18:00");
    	actions.validarNotificacao("Disponibilidade criada com sucesso.");
    }
    
    @Test
    public void adicionarBloqueioMedico () {
    	String user = "user" + hora;
    	String nome = "Testevaldo " + hora;
    	
    	Log.registrar("===== TESTE REALIZADO ===== - ADICIONAR BLOQUEIO MEDICO");
    	usuario.acessarUsuarios();
    	usuario.criarUsuario(
    			nome, 
    			"teste@teste.com", 
    			user, 
    			"123", 
    			"Default"
    			);
    	usuario.botaoSalvar(true);
    	actions.validarNotificacao("Cadastro criado com sucesso");
    	actions.fecharNotificacao();
    	
    	usuario.pesquisarUsuario(nome);
    	usuario.acessarEscalaProfissional(nome);
    	actions.esperar(300);
    	usuario.adicionarBloqueioMedico(dia, "12:00", "13:00");
    	actions.validarNotificacao("Salvo com sucesso.");
    	
    }
    
    @Test
    public void criarUsuarioAdm () {
    	String user = "user" + hora;
    	String nome = "Testevaldo " + hora;
    	Log.registrar("===== TESTE REALIZADO ===== - CRIAR USUÁRIO ADM");
    	
    	usuario.acessarUsuarios();
    	usuario.acessarUsuarioAdm();
    	usuario.criarUsuario(
    			nome, 
    			"teste@teste.com", 
    			user, 
    			"123", 
    			"Default"
    			);
    	usuario.botaoSalvar(true);
    	actions.validarNotificacao("Usuário cadastrado com sucesso.");
    	
    }
    
    @Test
    public void criarUsuarioAdmDuplicado () {
    	String user = "user" + hora;
    	String nome = "Testevaldo " + hora;
    	
    	Log.registrar("===== TESTE REALIZADO ===== - CRIAR USUÁRIO ADM DUPLICADO");
    	
    	usuario.acessarUsuarios();
    	usuario.acessarUsuarioAdm();
    	usuario.criarUsuario(
    			nome, 
    			"teste@teste.com", 
    			user, 
    			"123", 
    			"Default"
    			);
    	usuario.botaoSalvar(true);
    	actions.validarNotificacao("Usuário cadastrado com sucesso.");
    	actions.fecharNotificacao();
    	
    	// Duplicado
    	usuario.criarUsuario(
    			nome, 
    			"teste@teste.com", 
    			user, 
    			"123", 
    			"Default"
    			);
    	usuario.botaoSalvar(true);
    	actions.validarNotificacao("login "+ user +" já existente.");
    }
    
    @Test
    public void editarUsuarioAdm () {
    	String user = "user" + hora;
    	String nome = "Testevaldo " + hora;
    	
    	Log.registrar("===== TESTE REALIZADO ===== - EDITAR USUÁRIO ADM");
    	usuario.acessarUsuarios();
    	usuario.acessarUsuarioAdm();
    	usuario.criarUsuario(
    			nome, 
    			"teste@teste.com", 
    			user, 
    			"123", 
    			"Default"
    			);
    	usuario.botaoSalvar(true);
    	actions.validarNotificacao("Usuário cadastrado com sucesso.");
    	actions.fecharNotificacao();
    	
    	usuario.pesquisarUsuarioAdm(nome);
    	usuario.editarUsuario(nome, "//td[8]/div/button/span");
    	usuario.observacaoModal("EDITADO POR TESTE AUTOMATIZADO");
    	usuario.botaoSalvar(true);
    	actions.validarNotificacao("Usuário editado com sucesso.");
    	
    }
    
    @Test
    public void excluirUsuarioAdm () {
    	String user = "user" + hora;
    	String nome = "Testevaldo " + hora;
    	
    	Log.registrar("===== TESTE REALIZADO ===== - EXCLUIR USUÁRIO ADM");
    	usuario.acessarUsuarios();
    	usuario.acessarUsuarioAdm();
    	usuario.criarUsuario(
    			nome, 
    			"teste@teste.com", 
    			user, 
    			"123", 
    			"Default"
    			);
    	usuario.botaoSalvar(true);
    	actions.validarNotificacao("Usuário cadastrado com sucesso.");
    	actions.fecharNotificacao();
    	
    	usuario.pesquisarUsuarioAdm(nome);
    	usuario.excluirUsuarioAdm(nome);
    	actions.validarNotificacao("Usuário excluído com sucesso.");
    }
    
 
}