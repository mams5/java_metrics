package context;

public class codeContext {
    // número máximo de linhas de código úteis que um método deve ter
    public static final int METHOD_LINE_BENCHMARK = 30;

    // Variáveis globais
    public static int totalMethods = 0;
    public static int methodsBelowBenchmark = 0;

    // Método utilitário para calcular a porcentagem
    public static double getCompliancePercentage() {
        if (totalMethods == 0) return 0.0;
        return ((double) methodsBelowBenchmark / totalMethods) * 100.0;
    }
}