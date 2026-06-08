package report;

import analyzer.lineLengh;
import context.codeContext;
import scoring.scoringEngine;

public class reportGenerator {

    // Gera e exibe o relatório final com as métricas extraídas.
    public static void generateReport() {
        System.out.println("==================================================");
        System.out.println("         RELATÓRIO DE MÉTRICAS DE CÓDIGO          ");
        System.out.println("==================================================");
        System.out.println("Métrica avaliada: Tamanho dos Métodos");
        System.out.println("Benchmark definido: " + codeContext.METHOD_LINE_BENCHMARK + " linhas úteis por método.");
        System.out.println("--------------------------------------------------");
        System.out.println("Total de unidades funcionais (métodos): " + codeContext.totalMethods);
        System.out.println("Métodos em conformidade (abaixo do limite): " + codeContext.methodsBelowBenchmark);
        System.out.println("--------------------------------------------------");
        
        // Exibe a porcentagem final calculada pelo contexto
        System.out.printf("Porcentagem de métodos adequados: %.2f%%\n", scoringEngine.getCompliancePercentage());
        System.out.println("==================================================");
    }

    public static void generateLineLengthReport() {
        System.out.println("==================================================");
        System.out.println("         RELATÓRIO DE MÉTRICAS DE CÓDIGO          ");
        System.out.println("==================================================");
        System.out.println("Métrica avaliada: Comprimento das Linhas");
        System.out.println("--------------------------------------------------");
        System.out.println("Total de linhas úteis analisadas : " + codeContext.totalUsefulLines);
        System.out.println("Linhas adequadas (abaixo de 80)  : " + codeContext.linesBelowBenchmark);
        System.out.println("Linhas acima do limite           : " + lineLengh.getViolations().size());
        System.out.println("--------------------------------------------------");
        System.out.printf("Porcentagem de linhas adequadas: %.2f%%\n", scoringEngine.getLineLengthPercentage());
        System.out.println("==================================================");

        if (!lineLengh.getViolations().isEmpty()) {
            System.out.println("\nLinhas que podem ser melhoradas:");
            System.out.println("--------------------------------------------------");
            for (lineLengh.Violation v : lineLengh.getViolations()) {
                System.out.printf("Linha %d | %d chars (+%d acima do limite)%n",
                        v.lineNumber, v.lineLength, v.excess);
                System.out.println("  Conteúdo  : " + v.content);
                System.out.println("  Sugestão  : " + v.suggestion);
                System.out.println();
            }
        }
    }
}