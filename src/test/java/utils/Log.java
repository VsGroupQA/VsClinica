package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {

	private static FileWriter fileWriter;
	private static PrintWriter writer;
	static DateTimeFormatter data = DateTimeFormatter.ofPattern(" dd MM yyyy - HH.mm.ss");

	public static void criarArquivoLog(String nomeArquivo) {
		try {
			String caminhoCompleto = System.getProperty("user.dir") + "/src/test/java/log/";
			File diretorio = new File(caminhoCompleto);
			if (!diretorio.exists()) {
				if (diretorio.mkdirs()) {
					System.out.println("Diretório criado com sucesso: " + caminhoCompleto);
				} else {
					System.err.println("Falha ao criar diretório: " + caminhoCompleto);
					return;
				}
			}
			
			String nome =  LocalDateTime.now().format(data) + ".txt" + "-" + nomeArquivo;
			fileWriter = new FileWriter(caminhoCompleto + nome);
			writer = new PrintWriter(fileWriter);
			System.out.println("ARQUIVO DE LOG CRIADO: " + caminhoCompleto + nome);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void registrar(String mensagem) {
		if (writer != null) {
			writer.println(LocalDateTime.now().format(data) + " - " + mensagem);
			System.out.println(mensagem);
			writer.flush();
		} else {
			System.err.println("PASTA PARA ADICIONAR LOG NÃO CRIADA");
		}
	}

	public static void encerrarLog() {
		if (writer != null) {
			writer.close();
			System.out.println("Arquivo de log fechado.");
		}
	}
}