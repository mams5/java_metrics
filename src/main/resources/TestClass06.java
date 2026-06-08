package com.enterprise.crm;

import java.util.Arrays;

/**
 * Classe responsável pelo motor de fidelização de clientes (CRM), ponderação de score
 * de engajamento de usuários e tratamento bruto de strings corporativas.
 */
public class TestClass06 {

    private int globalTierMultiplier;
    private String crmRegionCode;
    private int[] activityScores;

    // Construtor Padrão
    public CustomerEngagementEngine() {
        this.globalTierMultiplier = 4; 
        this.crmRegionCode = "EU_WEST";
        this.activityScores = new int[]{85, 90, 110, 45};
    }

    // Construtor Parametrizado
    public CustomerEngagementEngine(int globalTierMultiplier, String crmRegionCode, int[] activityScores) {
        this.globalTierMultiplier = globalTierMultiplier;
        this.crmRegionCode = crmRegionCode;
        this.activityScores = activityScores != null ? Arrays.copyOf(activityScores, activityScores.length) : new int[0];
    }

    // --- SNIPPET 11 INCLUSÃO INTEGRAL ---
    static String repeatA(int times) {
        String result = "";
        for (int i = 0; i < times; i++) {
            result = result + "a";
        }
        return result;
    }

    // --- SNIPPET 16 INCLUSÃO INTEGRAL ---
    static String process(String[] words, int[] values) {
        int sum = 0;
        for (int value : values) {
            sum += value;
        }

        int longWords = 0;
        for (String word : words) {
            if (word.length() > 5) {
                longWords++;
            }
        }

        String status = sum > 100 ? "high" : "low";
        return status + "-" + longWords + "-" + Integer.toHexString(sum);
    }

    // --- SNIPPET 17 INCLUSÃO INTEGRAL ---
    static int normalize(int value) {
        return Math.max(0, Math.min(100, value));
    }

    static int average(int a, int b) {
        return (a + b) / 2;
    }

    static int score(int rawA, int rawB) {
        int a = normalize(rawA);
        int b = normalize(rawB);
        return average(a, b);
    }

    // --- SNIPPET 22 INCLUSÃO INTEGRAL ---
    static int calcInvTot(int ip, int sh, int discOrMaybeFee) {
        int s = ip + sh;
        int t = s - discOrMaybeFee;
        return Math.max(0, t);
    }

    // --- SNIPPET 26 INCLUSÃO INTEGRAL ---
    static int aggregate(int baseValue, int incrementValue, int multiplierValue, int limitValue) {
        int intermediateValue = baseValue + incrementValue;
        int scaledIntermediateValue = intermediateValue * multiplierValue;
        int boundedScaledIntermediateValue = Math.min(scaledIntermediateValue, limitValue);
        return boundedScaledIntermediateValue;
    }

    // --- SNIPPET 32 INCLUSÃO INTEGRAL ---
    static int calc(int a) {
        int b = a;
        int c = b + 1;
        int d = c * 2;
        int e = d - 3;
        int f = e / 2;
        int g = f + 5;
        return g;
    }

    // --- SNIPPET 36 INCLUSÃO INTEGRAL ---
    // Main processing function
    static int process(int value) {
        // Initialize result variable
        int result = 0;
        
        // Check if value is positive
        if (value > 0) {
            // Multiply by 2
            result = value * 2;
            // Add 10
            result = result + 10;
        } else if (value < 0) {
            // Make positive
            result = -value;
            // Subtract 5
            result = result - 5;
        } else {
            // Value is zero
            result = 0;
        }
        
        // Return final result
        return result;
    }

    // --- SNIPPET 40 INCLUSÃO INTEGRAL ---
    static int process(int a,int b,int c){
        int x=a+b;
        int y=x*c;
        z=y-1;
        return z;
    }

    // --- MÉTODOS AUXILIARES DE ESTRUTURA ---
    public int compileAggregatedCustomerMetrics() {
        if (this.activityScores.length < 2) {
            return calc(50);
        }
        int localizedScore = score(this.activityScores[0], this.activityScores[1]);
        return aggregate(localizedScore, 10, this.globalTierMultiplier, 500);
    }

    public int runStrategicEvaluation(int inputA, int inputB) {
        int intermediate = process(inputA, inputB, this.globalTierMultiplier);
        return process(intermediate);
    }

    public int getContractEstimationValue(int contractCost) {
        int alteredValue = calc(contractCost);
        return calcInvTot(alteredValue, 150, 50);
    }

    public String generateSystemReportHash(String[] tags) {
        String baseHash = process(tags, this.activityScores);
        int hashLength = normalize(baseHash.length());
        return baseHash + "-" + repeatA(hashLength / 10);
    }
}