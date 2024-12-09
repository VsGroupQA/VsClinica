
package pages;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Actions;
import utils.Log;


public class UsuarioPage {
	private WebDriver driver;
	private Actions actions;

	public UsuarioPage(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(this.driver);
	}
	
	public void acessarUsuarios() {
	actions.esperarElementoVisivel(By.id("ROLE_USUARIOS"), 10);
	actions.clicarBotaoPegandoPeloId("ROLE_USUARIOS");
	Log.registrar("Tela de Usuários");
	}
	
	public void usuarioAdm() {
		actions.esperarElementoVisivel(By.xpath("//p-card[@id='usuarios']/div/div/div/div[2]"), 0);
		actions.clicarBotaoPegandoPeloXpath("//p-card[@id='usuarios']/div/div/div/div[2]");
		Log.registrar("Acessar usuários ADM");
	}
	
	public void abrirModalNovoUsuario() {
	actions.clicarBotaoPegandoPeloCss(".pi-plus");
	Log.registrar("Modal de novo usuários");
	}
	
	public void dropdown(String nomeDrop) {
		actions.clicarBotaoPegandoPeloXpath("//p-dropdown/div/span");
	    actions.esperarElementoVisivel(By.xpath("//p-dropdownitem/li"), 10);
	    
	    List<WebElement> tipoIntegracao = driver.findElements(By.xpath("//p-dropdownitem/li"));
	    for (WebElement integracao : tipoIntegracao) {
	        if (integracao.getText().equalsIgnoreCase(nomeDrop)) {
	            integracao.click();
	            Log.registrar("Nome no dropdown: '" + nomeDrop + "' encontrado e selecionado");
	            break;
	        }
	    }
	}
	
	public void botaoSalvar(Boolean visivel) {
		actions.esperarElementoVisivel(By.xpath("//span[contains(.,'Salvar')]"), 0);
		    int tentativas = 0;
		    int maxTentativas = 1;
		    
		    while (tentativas < maxTentativas) {
		        try {
	            actions.clicarBotaoPegandoPeloXpath("//span[contains(.,'Salvar')]");
		            Log.registrar("Concluir agendamento");

		            if (!visivel) {
		                Assert.fail("O botão de criação foi clicado, mas não deveria permitir agendamento duplicado.");
		            }
		            return;
		        } catch (ElementClickInterceptedException e) {
		            tentativas++;
		            if (tentativas >= maxTentativas) {
		                if (visivel) {
		                    Assert.fail("Falha ao clicar no botão de agendamento após " + maxTentativas + " tentativas: o botão não está disponível no momento. " + e.getMessage());
		                } else {
		                    Assert.assertTrue("Agendamento duplicado detectado corretamente.", true);
		                }
		            } else {
		                actions.esperar(500);
		            }
		        }
		    }
	}
	
	
	
	// GRUPO
	
	/**
	 * Adicionar um novo usuário
	 *
	 * @param nomeUsuario       Nome do usuario medico.
	 * @param emailUsuario 		Email do usuario.
	 * @param loginUsuario    	Login de acesso do medico.
	 * @param senhaUsuario    	Senha de acesso do medico.
	 * @param permissao    		permissão de usuario.
	 */
	public void criarUsuario(String nomeUsuario, String emailUsuario, String loginUsuario, String senhaUsuario, String permissao) {

		abrirModalNovoUsuario();
		actions.escreverPegandoPeloName("nome", nomeUsuario);
		actions.escreverPegandoPeloName("cep", "30270-300");
		actions.escreverPegandoPeloName("email", emailUsuario);
		actions.escreverPegandoPeloName("login", loginUsuario);
		actions.escreverPegandoPeloName("senha", senhaUsuario);
		actions.escreverPegandoPeloName("observacoes", "CRIADO POR TESTE AUTOMATIZADO");
		dropdown(permissao);
		
	}
}