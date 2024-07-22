package utils;

public class Access {

    // URL
    public static String url = setUrl(1);
    
    // NAVEGADOR
    public static String navegador = "firefox";
    
    // ACESSO ADMIN
    public static String usuario = "admin.vs";
    public static String senha = "Luandabh001";
    
    // ACESSO CLINICO
    public static String usuarioMedico = "vena";
    public static String senhaMedico = "123";
    
    // AGENDAMENTO
    public static String procedimento = "Transplante de Sombracelha";
    public static String compromisso = "FINALIZADO";
    public static String paciente = "Jhonata Venancio - VS GROUP";
    public static String medico = "VS GROUP";
    
    // INTEGRAÇÃO
    public static String urlBuscarEquipe = "https://omnia-dev.vsomnia.com.br:8443/equipe-usuario/equipes";
    public static String urlBuscarUsuario = "https://omnia-dev.vsomnia.com.br:8443/equipe-usuario/usuarios";
    public static String urlIntegracao = "https://omnia-dev.vsomnia.com.br:8443/disparoEmMassa/enviar";
    public static String tokenOmnia = "16082001";
    public static String numeroDisparo = "553182952805";
    public static String nomeTemplate = "DisparoRejuHML";
    public static String variavel = "A=NOME_CLIENTE;B=PROCEDIMENTO;C=DIA;D=DIA_SEMANA;E=HORA;F=NASCIMENTO;G=COMPROMISSO;";


    public static String setUrl(int number) {
        switch (number) {
            case 1:
            	return "https://homologacao-rejuvenesce.vsgestaoclinica.com.br/";
            case 2:
                return "https://staging-rejuvensce.vsgestaoclinica.com.br/";
            case 3:
            	return "https://rejuvenesce.vsgestaoclinica.com.br/";
            default:
            	return "https://staging-rejuvensce.vsgestaoclinica.com.br/";
        }
    }
}
