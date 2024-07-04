package test;


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
import pages.loginPage;

public class AgendamentoTest {
    private static WebDriver driver;
    private loginPage loginPage;
    private Actions actions;
    private AgendamentoPage agendamento;

    @BeforeAll
    public static void iniciarLog() {
        Log.criarArquivoLog("Log.Agendamento");
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
        actions = new Actions(driver);
        agendamento = new AgendamentoPage(driver);
    }

    @AfterEach
    public void encerrarDriver() {
//        Browser.fecharNavegador();
    }
    

    @Test
    public void teste() {
    	actions.esperar(100);
    	String dropdown = "/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[1]/p-dropdown/div/div[2]";
    	String item = "/html/body/div[2]/div/div/div/ul/p-dropdownitem/li";
    	String procurado = "Transplante de Sombracelha";
    	
    	agendamento.modalAgendamento();
    	agendamento.dropdown(dropdown, item, procurado);
    	
    	// profissional
    	
    	String dropProfissional = "//*[@id=\"pr_id_17_label\"]";
    	String itemProfissional = "/html/body/div[2]/div/div/div/ul/p-dropdownitem/li";
    	String procuradoProf = "VS GROUP";
    	
    	agendamento.modalAgendamento();
    	agendamento.dropdown(dropProfissional, itemProfissional, procuradoProf);
    	
    }
}