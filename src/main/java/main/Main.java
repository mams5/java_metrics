package main;

import analyzer.lineLengh;
import analyzer.methodSizes;
import analyzer.structuresDensity;
import context.codeContext;
import analyzer.propComents;
import analyzer.spacingLines; // Importando sua nova feature
import context.codeContext;
import report.reportGenerator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Ler a entrada do terminal
        Scanner scanner = new Scanner(System.in);

        System.out.println("===Bem vindo ao JavaMetric===");
        System.out.print("Insira o caminho do seu codigo para começar a analise: ");
        
        // Input
        String inputPath = scanner.nextLine();

        // remove aspas que aparecem caso o usuário arraste o arquivo para o terminal
        inputPath = inputPath.replace("\"", "").trim();

        try {
            // Transforma o texto digitado em um Path real
            Path filePath = Paths.get(inputPath);
            
            if (!Files.exists(filePath)) {
                System.out.println("\n[Erro] O arquivo não foi encontrado no caminho especificado.");
                System.out.println("Caminho digitado: " + inputPath);
                return;
            }

            // Lê todas as linhas do arquivo informado
            String sourceCode = Files.readString(filePath);

            System.out.println("\nArquivo carregado com sucesso!");
            System.out.println("Iniciando análise...\n");

            // Features executadas em sequência
            lineLengh.analyze(sourceCode);
            //methodSizes.analyze(sourceCode);
            structuresDensity.analyze(filePath);
            propComents.analyze(sourceCode);
            
            // Executando a sua feature de Espaçamento Vertical (NELB)
            spacingLines.analyze(filePath, sourceCode);

            // Output - Relatório completo unificado
            reportGenerator.generateReport();

        } catch (Exception e) {
            System.err.println("\nErro ao processar a análise: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fecha o scanner para liberar a memória
            scanner.close();
        }
    }
}