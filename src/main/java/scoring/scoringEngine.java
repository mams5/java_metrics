package scoring;

import context.codeContext;

public class scoringEngine {

    // Soma nota final de 0 a 5
    public static int finalScore (/* resultados das features*/){
        
        int resultado = 0;
        //realiza soma

        return resultado;
    }
    // Calcula porcentagem de methodSizes
    public static double getCompliancePercentage() {
        if (codeContext.totalMethods == 0) {
            return 0.0;
        }
        return ((double) codeContext.methodsBelowBenchmark / codeContext.totalMethods) * 100.0;
    }

    public static double calculateNdec(double structuresPerTenLines, double averageDepth) {
        return 1.0 / (1.0 + (structuresPerTenLines * averageDepth));
    }

}