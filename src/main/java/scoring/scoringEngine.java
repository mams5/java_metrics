package scoring;

public class scoringEngine {

    // Soma nota final de 0 a 5
    public static int finalScore (/* resultados das features*/){
        
        int resultado = 0;
        //realiza soma

        return resultado;
    }
    // Calcula porcentagem de methodSizes
    public static double getCompliancePercentage() {
        if (totalMethods == 0) {
            return 0.0;
        }
        return ((double) methodsBelowBenchmark / totalMethods) * 100.0;
    }

}