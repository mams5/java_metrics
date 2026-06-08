package report;

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

        System.out.println("Métrica avaliada: Proporção de Comentários:");
        System.out.println("Linhas Úteis: " + codeContext.totalUsefulLines);
        System.out.println("Linhas de Comentários: " + codeContext.totalCommentLines);
        System.out.println("--------------------------------------------------");
        
        // Exibe o cálculo detalhado para conferência
        double benchmarkAtual = (codeContext.totalUsefulLines / 10.0) + (codeContext.totalMethods * 2.0);
        System.out.printf("Benchmark calculado: %.2f\n", benchmarkAtual);
        
        // Multiplica por 100 para exibir em formato de nota percentual ou deixa de 0 a 1
        double npc = scoringEngine.getNpcScore();
        System.out.printf("Nota NPC (0 a 1): %.2f\n", npc);
        System.out.println("==================================================");
    }
}