package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Access;
import utils.Actions;
import utils.Log;

import java.time.Duration;
import java.util.List;

public class IntegracoesPage {
    private WebDriver driver;
    private Actions actions;
    private WebDriverWait wait;

    public IntegracoesPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(this.driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /* COMPONENTES */

    public void acessarIntegracao() {
        actions.esperar(1000);
        Log.registrar("Esperando");
        actions.clicarBotaoPegandoPeloId("ROLE_CONFIGURACOES");
        Log.registrar("Clicando no botão de configurações");
        actions.esperar(1000);
        Log.registrar("Esperando 1 segundo");
        actions.clicarBotaoPegandoPeloXpath("//p-card[@id='Integracoes']/div/div/div/div[2]/p[2]");
        Log.registrar("Clicando na opção de integrações");
    }

    public void adicionarNovaIntegracao() {
        actions.clicarBotaoPegandoPeloXpath("//button/span");
        Log.registrar("Clicando no botão de adicionar nova integração");
    }

    public void selecionarTipoIntegracao(String nomeDoTipo) {
        Log.registrar("Selecionando tipo de integração: " + nomeDoTipo);
        actions.clicarBotaoPegandoPeloXpath("//p-dropdown/div/span");
        List<WebElement> tipoIntegracao = driver.findElements(By.xpath("//p-dropdownitem/li"));
        for (WebElement integracoes : tipoIntegracao) {
            if (integracoes.getText().equalsIgnoreCase(nomeDoTipo)) {
                integracoes.click();
                Log.registrar("Tipo de integração " + nomeDoTipo + " encontrado e selecionado");
                break;
            }
        }
    }

    public void urlIntegracao(String url) {
        actions.escreverPegandoPeloName("nome", url);
        Log.registrar("Inserindo URL da integração: " + url);
    }

    public void tokenFornecedor(String informarToken) {
        actions.escreverPegandoPeloName("token", informarToken);
        Log.registrar("Inserindo token do fornecedor");
    }

    public void adicionarTemplate() {
        actions.clicarBotaoPegandoPeloCss("div.col-12:nth-child(5) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > p-inputswitch:nth-child(1) > div:nth-child(1) > span:nth-child(2)");
        Log.registrar("Adicionando template");
    }

    public void selecionarLead(int parametro, String equipeOuUsuario) {
        if (equipeOuUsuario == null) {
            Log.registrar("equipeOuUsuario é null, método será ignorado.");
            return;
        }
        switch (parametro) {
        case 0: // Aquario
            actions.clicarBotaoPegandoPeloCss("#active > div > span");
            Log.registrar("Enviar lead para aquario em integração");
            break;
        case 1: // Equipe
            selecionarEquipeOuUsuario(equipeOuUsuario, true);
            Log.registrar("Criar lead e enviar para equipe: " + equipeOuUsuario);
            break;
        case 2: // Usuário
            selecionarEquipeOuUsuario(equipeOuUsuario, false);
            Log.registrar("Criar lead e enviar para usuário: " + equipeOuUsuario);
            break;
        case 3:
            Log.registrar("Parâmetro nulo.");
            break;
        default:
            Log.registrar("Parâmetro inválido: " + parametro);
            throw new IllegalArgumentException("Parâmetro inválido: " + parametro);
        }
    }

    private void selecionarEquipeOuUsuario(String nome, boolean ehEquipe) {
        actions.clicarBotaoPegandoPeloXpath("//p-inputswitch/div/span");
        Log.registrar("Criar lead em especifico");
        actions.esperar(2000);
        Log.registrar("Esperando 2 segundos");
        actions.clicarBotaoPegandoPeloXpath(ehEquipe ? "//div[2]/p-radiobutton/div/div[2]" : "//p-radiobutton/div/div[2]");
        Log.registrar(ehEquipe ? "Selecionar Equipe" : "Selecionar Usuário");

        actions.clicarBotaoPegandoPeloXpath("//div[2]/div/p-dropdown/div/span");
        List<WebElement> dropdownItems = driver.findElements(By.xpath("//p-dropdownitem/li"));

        for (WebElement item : dropdownItems) {
            try {
                if (item.getText().equalsIgnoreCase(nome)) {
                    item.click();
                    Log.registrar("Item " + nome + " selecionado");
                    break;
                }
            } catch (StaleElementReferenceException e) {
                dropdownItems = driver.findElements(By.xpath("//p-dropdownitem/li"));
                Log.registrar("StaleElementReferenceException: Recarregando dropdown items");
            }
        }
    }

    public void variaveis(String variavel) {
        if (variavel == null) {
            Log.registrar("Variável é null, método variaveis será ignorado.");
            return;
        }
        actions.escreverPegandoPeloName("infoAdicionais", variavel);
        Log.registrar("Inserindo variáveis adicionais: " + variavel);
    }

    public void nomeIntegracao(String descricao) {
        actions.escreverPegandoPeloName("nomeIntegracao", descricao);
        Log.registrar("Inserindo nome da integração: " + descricao);
    }

    public void validarNotificacao(String msg) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement notificacao = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-detail")));
            String textoNotificacao = notificacao.getText();
            Assert.assertEquals(msg, textoNotificacao);
            Log.registrar("Notificação de sucesso exibida: " + textoNotificacao);
        } catch (NoSuchElementException e) {
            Log.registrar("Falha ao encontrar a notificação de sucesso: " + e.getMessage());
            Assert.fail("Falha ao encontrar a notificação de sucesso: " + e.getMessage());
        }
    }

    public void botaoSalvarIntegracao() {
        actions.clicarBotaoPegandoPeloXpath("/html/body/app-root/div/app-configuracoes/div/app-integracao/p-dialog/div/div/div[3]/form/div[8]/p-button[1]/button/span");
        Log.registrar("Clicando no botão de salvar integração");
    }
    
    public void botaoSalvarAgendador() {
    	actions.clicarBotaoPegandoPeloXpath("//p-button/button/span");
    	Log.registrar("Clicando no botão de salvar agendador");
    }

    public void numeroDisparo(String numero) {
        if (numero == null) {
            Log.registrar("Parâmetro 'numero' é null, método ignorado.");
            return;
        }
        actions.escreverPegandoPeloName("numeroDisparo", numero);
        Log.registrar("Número de template adicionado: " + numero);
    }

    public void nomeTemplateOmnia(String nomeTemplate) {
        actions.escreverPegandoPeloName("infoTemplate", nomeTemplate);
        Log.registrar("Inserindo nome do template Omnia: " + nomeTemplate);
    }

    public void procedimento(String nomeProcedimento) {
        actions.clicarBotaoPegandoPeloXpath("//div[5]/div[2]/div[2]/div/p-dropdown/div/span");
        Log.registrar("Clicando no dropdown de procedimentos");
        List<WebElement> dropdownItems = driver.findElements(By.xpath("//p-dropdownitem/li"));
        for (WebElement item : dropdownItems) {
            if (item.getText().equalsIgnoreCase(nomeProcedimento)) {
                item.click();
                Log.registrar("Procedimento " + nomeProcedimento + " selecionado");
                break;
            }
        }
    }

    public void adicionarNovoAgendador() {
        actions.clicarBotaoPegandoPeloXpath("//p-card[@id='agendadorTarefas']/div/div/div/div[2]/p");
        Log.registrar("Clicando no botão de adicionar novo agendador");
    }

    public void descricaoAgendador(String nomeDescricao) {
        actions.escreverPegandoPeloName("descricao", nomeDescricao);
        Log.registrar("Inserindo descrição do agendador: " + nomeDescricao);
    }

    public void selecionarTipoAlerta(String nomeDoTipo) {
        actions.clicarBotaoPegandoPeloXpath("/html/body/app-root/div/app-configuracoes/div/app-integracao/app-agendador-tarefas/p-dialog/div/div/div[3]/div/form/div[3]/div/p-dropdown/div/span");
        Log.registrar("Clicando no dropdown de tipo de alerta");
        List<WebElement> tipoAlerta = driver.findElements(By.xpath("//p-dropdownitem/li"));
        for (WebElement alerta : tipoAlerta) {
            if (alerta.getText().equalsIgnoreCase(nomeDoTipo)) {
                alerta.click();
                Log.registrar("Tipo de alerta " + nomeDoTipo + " selecionado");
                break;
            }
        }
    }

    public void horarioExecucao(String hora) {
        actions.escreverPegandoPeloName("horarioExec", hora);
        Log.registrar("Inserindo horário de execução: " + hora);
    }

    public void deletarIntegracao(String nomeEsperado) {
        Log.registrar("Tentando deletar integração com o nome: " + nomeEsperado);
        actions.esperar(1000);
        List<WebElement> linhas = driver.findElements(By.xpath("//table/tbody/tr"));

        for (int i = 1; i <= linhas.size(); i++) {
        	try {
            WebElement nomeColuna = driver.findElement(By.xpath("//tr[" + i + "]/td[2]"));
            String item = nomeColuna.getText();
            Log.registrar("Nome da integração na linha " + i + ": " + item);
            if (item.equals(nomeEsperado)) {
                WebElement botaoExcluir = driver.findElement(By.xpath("//tr[" + i + "]/td[6]/div/button[2]/span"));
                botaoExcluir.click();
                Log.registrar("Integração excluída com sucesso");
                break;
            }
            
        }catch (NoSuchElementException e) {
            Log.registrar("Elemento não encontrado na linha " + i + ": " + e.getMessage());
        }
        
        }
    }

    public void desativarIntegracao(String nomeEsperado) {
        Log.registrar("Tentando desativar integração com o nome: " + nomeEsperado);
        actions.esperar(1000);
        Log.registrar("Esperando");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
        Log.registrar("Tabela de integrações carregada");

        List<WebElement> linhas = driver.findElements(By.xpath("//table/tbody/tr"));

        for (int i = 1; i <= linhas.size(); i++) {
            try {
                WebElement nomeColuna = driver.findElement(By.xpath("//tr[" + i + "]/td[2]"));
                String txt = nomeColuna.getText();
                Log.registrar("Nome da integração na linha " + i + ": " + txt);

                if (txt.equals(nomeEsperado)) {
                    Log.registrar("Integração encontrada: " + txt);
                    WebElement botaoDesativar = driver.findElement(By.xpath("//tr[" + i + "]/td[6]/div/p-inputswitch/div/span"));
                    botaoDesativar.click();
                    Log.registrar("Clicando no botão de desativar integração");

                    break;
                }
            } catch (NoSuchElementException e) {
                Log.registrar("Elemento não encontrado na linha " + i + ": " + e.getMessage());
            }
        }
    }

    public void desativarAgendamento(String nomeEsperado) {
    	Log.registrar("Tentando desativar integração com o nome: " + nomeEsperado);
        actions.esperar(1000);
        Log.registrar("Esperando");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
        Log.registrar("Tabela de integrações carregada");

        List<WebElement> linhas = driver.findElements(By.xpath("//table/tbody/tr"));

        for (int i = 1; i <= linhas.size(); i++) {
            try {
                WebElement nomeColuna = driver.findElement(By.xpath("//app-agendador-tarefas/p-table/div/div/table/tbody/tr["+i+"]/td"));
                String txt = nomeColuna.getText();
                Log.registrar("Nome da integração na linha " + i + ": " + txt);

                if (txt.equals(nomeEsperado)) {
                    Log.registrar("Integração encontrada: " + txt);
                    WebElement botaoDesativar = driver.findElement(By.xpath("//table[@id='pr_id_9-table']/tbody/tr["+i+"]/td[6]/div/p-inputswitch/div/span"));
                    botaoDesativar.click();
                    Log.registrar("Clicando no botão de desativar integração");

                    break;
                }
            } catch (NoSuchElementException e) {
                Log.registrar("Elemento não encontrado na linha " + i + ": " + e.getMessage());
            }
        }
    }
    
    public void fecharModal() {
    	actions.clicarBotaoPegandoPeloXpath("//p-button[2]/button/span");
    }
    
    public void fecharNotficacao() {
    	actions.clicarBotaoPegandoPeloCss(".p-toast-icon-close-icon > path");
    }
    
    // GRUPO/* GRUPO */

    public void grupoCriarIntegracao(String tipo, String endpoint, String token, String nomeIntegracao,String variavel, int caseLead, String equipeUsuario) {
        Log.registrar("Iniciando criação de integração em grupo");
        actions.esperar(2200);
        try {
            acessarIntegracao();
            adicionarNovaIntegracao();
            selecionarTipoIntegracao(tipo);
            urlIntegracao(endpoint);
            tokenFornecedor(token);
            nomeIntegracao(nomeIntegracao);
            variaveis(variavel);
            selecionarLead(caseLead, equipeUsuario);
            Log.registrar("Criação de integração em grupo concluída");

        } catch (ElementNotInteractableException e) {
            Log.registrar("Erro ao interagir com o elemento: " + e.getMessage());
            Assert.fail("Teste falhou devido à ElementNotInteractableException: " + e.getMessage());

        } catch (WebDriverException e) {
            Log.registrar("Erro inesperado no WebDriver: " + e.getMessage());
            Assert.fail("Teste falhou devido a um erro no WebDriver: " + e.getMessage());
        }
    }

    public void grupoAdicionarTemplate(String procedimento, String numero, String template) {
        Log.registrar("Iniciando adição de template em grupo");
        actions.esperar(1000);
        Log.registrar("Esperando 1 segundo");
        adicionarTemplate();
        Log.registrar("Esperando 1 segundo");
        procedimento(procedimento);
        numeroDisparo(numero);
        nomeTemplateOmnia(template);
        Log.registrar("Adição de template em grupo concluída");
    }

    public void grupoAdicionarAgendador(String descricao) {
        Log.registrar("Iniciando adição de agendador em grupo");
        acessarIntegracao();
        adicionarNovoAgendador();
        descricaoAgendador(descricao);
        selecionarTipoAlerta("ALERTAR_AGENDAMENTOS");
        actions.esperar(2000);
        Log.registrar("Adição de agendador em grupo concluída");
    }
    
    public void grupoBuscarEquipeUsuarioOmnia() {
    	acessarIntegracao();
    	grupoCriarIntegracao(
                "BUSCAR_EQUIPES",
                Access.urlBuscarEquipe, 
                Access.tokenOmnia,
                "BUSCAR EQUIPE - TESTE",
                null,
                3, 
                null
        );
    	botaoSalvarIntegracao();
    	actions.esperar(500);
    	grupoCriarIntegracao(
                "BUSCAR_USUARIOS",
                Access.urlBuscarUsuario, 
                Access.tokenOmnia, 
                "BUSCAR USUARIO - TESTE",
                null, 
                3, 
                null
        );
    	botaoSalvarIntegracao();
    }
    
}
