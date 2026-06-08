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

    // Calcula porcentagem de lineLength
    public static double getLineLengthPercentage() {
        if (codeContext.totalUsefulLines == 0) {
            return 100.0;
        }
        return ((double) codeContext.linesBelowBenchmark / codeContext.totalUsefulLines) * 100.0;
    }
    
    public static double getNpcScore() {
        if (codeContext.totalUsefulLines == 0 && codeContext.totalMethods == 0) return 0.0;

        double benchmark = (codeContext.totalUsefulLines / 10.0) + (codeContext.totalMethods * 2.0);

        if (benchmark == 0) return 0.0; // Previne divisão por zero

        double npc = 1.0 - ((codeContext.totalCommentLines - benchmark) / benchmark);

        return Math.max(0.0, Math.min(1.0, npc));
    }

}