package com.enterprise.logistics;

import java.util.Arrays;

/**
 * Classe responsável pelo gerenciamento de despacho logístico, processamento
 * analítico de frotas e monitoramento de thresholds de carga positiva.
 */
public class TestClass10 {

    private int activeSafetyBound;
    private double alertFactor;
    private int[] fleetLoadData;

    // Construtor Padrão
    public LogisticsFleetPipeline() {
        this.activeSafetyBound = 200;
        this.alertFactor = 1.25;
        this.fleetLoadData = new int[]{150, 42, -10, 0, 88};
    }

    // Construtor Parametrizado
    public LogisticsFleetPipeline(int activeSafetyBound, double alertFactor, int[] fleetLoadData) {
        this.activeSafetyBound = activeSafetyBound;
        this.alertFactor = alertFactor;
        this.fleetLoadData = fleetLoadData != null ? Arrays.copyOf(fleetLoadData, fleetLoadData.length) : new int[0];
    }

    // --- SNIPPET 15 INCLUSÃO INTEGRAL ---
    static int countEven(int[] values) {
        int count = 0;
        for (int value : values) {
            if (value % 2 == 0) {
                count++;
            }
        }
        return count;
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

    // --- SNIPPET 30 INCLUSÃO INTEGRAL ---
    static int run(int[] data) {
        int field = 0;
        for (int value : data) {
            if (value > 0) {
                field += value;
            }
        }
        return field;
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
        int z=y-1;
        return z;
    }

    // --- MÉTODOS AUXILIARES DE ESTRUTURA ---
    public int compileFleetLoadScore() {
        int absolutePositiveSum = run(this.fleetLoadData);
        int evenCount = countEven(this.fleetLoadData);
        return score(absolutePositiveSum, evenCount);
    }

    public int runAdvancedLogisticsEstimation(int baseLoad, int variableModifier) {
        int stepOne = process(baseLoad, variableModifier, 2);
        int stepTwo = calc(stepOne);
        return process(stepTwo);
    }

    public int evaluateInvoiceTotalWithFees(int priorityItemCost) {
        int baseCost = calc(priorityItemCost);
        return calcInvTot(baseCost, 85, this.activeSafetyBound);
    }

    public String generateFleetStatusSummary(String[] destinationCodes) {
        return process(destinationCodes, this.fleetLoadData);
    }
}