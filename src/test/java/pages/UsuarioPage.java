package pages;

import java.time.Duration;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Actions;
import utils.Log;


public class UsuarioPage {
	private WebDriver driver;
	private Actions actions;
	private WebDriverWait wait;

	public UsuarioPage(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(this.driver);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void acessarUsuarios() {
	actions.esperarElementoVisivel(By.id("ROLE_USUARIOS"), 10);
	actions.clicarBotaoPegandoPeloId("ROLE_USUARIOS");
	Log.registrar("Tela de Usuários");
	}
	
	public void acessarUsuarioAdm () {
		actions.clicarBotaoPegandoPeloCss("#usuarios .m-0");
	}
	
	public void observacaoModal (String obs) {
		actions.escreverPegandoPeloName("observacoes", obs);
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
	
	public void editarUsuario(String nomeEsperado, String elemento) {
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
	    Log.registrar("Tabela localizada");

	    List<WebElement> linhas = driver.findElements(By.xpath("//table/tbody/tr"));

	    for (int i = 1; i <= linhas.size(); i++) {
	        try {
	            WebElement nomeColuna = linhas.get(i - 1).findElement(By.xpath("./td[1]"));
	            String txt = nomeColuna.getText();
	            Log.registrar("Nome na linha: " + i + ": " + txt);

	            if (txt.equals(nomeEsperado)) {
	                Log.registrar("Usuario encontrado: " + txt);
	                WebElement botaoEditar = linhas.get(i - 1).findElement(By.xpath(elemento));
	                botaoEditar.click();
	                Log.registrar("Botão de edição clicado");
	                break;
	            }
	        } catch (NoSuchElementException e) {
	            Log.registrar("Elemento não encontrado na linha " + i + ": " + e.getMessage());
	        }
	    }
	}
	
	public void pesquisarUsuario (String nome) {
		actions.escreverPegandoPeloXpath("//*[@id=\"pr_id_16-table\"]/thead/tr[1]/th[6]/span/input", nome);
	}
	
	public void pesquisarUsuarioAdm (String nome) {
		actions.escreverPegandoPeloCss(".p-inputtext", nome);
	}
	
	public void excluirUsuario(String nomeEsperado) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));

		List<WebElement> linhas = driver.findElements(By.xpath("//table/tbody/tr"));

		for (int i = 1; i <= linhas.size(); i++) {
			try {
				WebElement nomeColuna = driver.findElement(By.xpath("//tr[" + i + "]/td[1]"));
				String item = nomeColuna.getText();
				Log.registrar("Nome do usuario na linha: " + i + ": " + item);
				
				if (item.equals(nomeEsperado)) {
					WebElement botaoExcluir = driver.findElement(By.xpath("//tr[" + i + "]/td[6]/div/button[2]/span"));
					botaoExcluir.click();
					actions.clicarBotaoPegandoPeloXpath("//span[contains(.,'Sim')]");
					Log.registrar("Usuario excluido com sucesso");
					break;
				}

			} catch (NoSuchElementException e) {
				Log.registrar("Elemento não encontrado na linha " + i + ": " + e.getMessage());
			}

		}
	}
	
	public void excluirUsuarioAdm(String nomeEsperado) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));

		List<WebElement> linhas = driver.findElements(By.xpath("//table/tbody/tr"));

		for (int i = 1; i <= linhas.size(); i++) {
			try {
				WebElement nomeColuna = driver.findElement(By.xpath("//tr[" + i + "]/td[1]"));
				String item = nomeColuna.getText();
				Log.registrar("Nome do usuario ADM na linha: " + i + ": " + item);
				
				if (item.equals(nomeEsperado)) {
					WebElement botaoExcluir = driver.findElement(By.xpath("//tr["+i+"]/td[8]/div/button[2]/span"));
					botaoExcluir.click();
					actions.clicarBotaoPegandoPeloXpath("//span[contains(.,'Sim')]");
					Log.registrar("Usuario excluido com sucesso");
					break;
				}

			} catch (NoSuchElementException e) {
				Log.registrar("Elemento não encontrado na linha " + i + ": " + e.getMessage());
			}

		}
	}
	
	public void acessarEscalaProfissional(String nomeEsperado) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));

		List<WebElement> linhas = driver.findElements(By.xpath("//table/tbody/tr"));

		for (int i = 1; i <= linhas.size(); i++) {
			try {
				WebElement nomeColuna = driver.findElement(By.xpath("//tr[" + i + "]/td[1]"));
				String item = nomeColuna.getText();
				Log.registrar("Nome do usuario na linha: " + i + ": " + item);
				
				if (item.equals(nomeEsperado)) {
					actions.clicarBotaoPegandoPeloXpath("//tr[\" + i + \"]/td[1]");
					Log.registrar("Usuario "+nomeEsperado+" acessado.");
					break;
				}

			} catch (NoSuchElementException e) {
				Log.registrar("Elemento não encontrado na linha " + i + ": " + e.getMessage());
			}

		}
	}
	
	public void criarEscala (String horaInicio, String horaFim) {
		actions.esperar(1000);
		actions.clicarBotaoPegandoPeloCss(".fc-adcEscala-button");
		
		// Semana
		for (int i = 1; i <= 7; i++) {
		    actions.clicarBotaoPegandoPeloXpath("//p-selectbutton/div/div[" + i + "]/span");
		}
		
		// Horario
		actions.escreverPegandoPeloXpath("//input[@id='timeonly']", horaInicio);
		actions.escreverPegandoPeloXpath("(//input[@id='timeonly'])[2]", horaFim);
		
		// Salvar
		actions.clicarBotaoPegandoPeloXpath("//app-modal-criar-escala/div/div/div[2]/p-button/button/span");
	}
	
	public void adicionarBloqueioMedico (String data, String horaInicio, String horaFim) {
		// Acessar Bloqueio
		actions.clicarBotaoPegandoPeloXpath("//div/ul/li[2]/a/span");
		
		// Criar bloqueio
		actions.clicarBotaoPegandoPeloCss(".p-button-label:nth-child(2)");
		
		actions.escreverPegandoPeloXpath("//input[@name='diaEspecifico']", data);
		Log.registrar("Data inserida: "+ data +"");
		
		// horario
		actions.escreverPegandoPeloXpath("//input[@name='horaInicial']", horaInicio);
		actions.escreverPegandoPeloXpath("//input[@name='horaFinal']", horaFim);
		
		actions.escreverPegandoPeloName("observacao", "Bloqueio");
		
		// Salvar
		actions.clicarBotaoPegandoPeloXpath("//div[7]/p-button/button/span");
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
		observacaoModal("CRIADO POR TESTE AUTOMATIZADO");
		dropdown(permissao);
		
	}
}