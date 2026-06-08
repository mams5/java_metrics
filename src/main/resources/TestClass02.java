package com.enterprise.analytics;

/**
 * Classe responsável pelo processamento de métricas numéricas e agregação de dados financeiros.
 */
public class TestClass02 {

    private int baseLimit;
    private double taxRate;
    private int[] transactionData;

    // Construtor Padrão
    public FinancialMetricsAggregator() {
        this.baseLimit = 500;
        this.taxRate = 0.15;
        this.transactionData = new int[]{0, 10, -5, 20};
    }

    // Construtor Customizado
    public FinancialMetricsAggregator(int baseLimit, double taxRate, int[] transactionData) {
        this.baseLimit = baseLimit;
        this.taxRate = taxRate;
        this.transactionData = transactionData != null ? transactionData.clone() : new int[0];
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

    // --- SNIPPET 27 INCLUSÃO INTEGRAL ---
    static int compute(int a, int b, int c) {
        return a + b * c;
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
    public int executeSequenceAnalysis(int primary, int secondary) {
        int stepOne = process(primary); 
        int stepTwo = calc(secondary);
        return compute(stepOne, stepTwo, this.baseLimit);
    }

    public int compileAggregateTotal() {
        int initialSum = run(this.transactionData);
        return aggregate(initialSum, 5, 2, this.baseLimit);
    }

    public int estimateInvoice(int basePrice, int penalty) {
        int baseComputed = process(basePrice, 10, 3);
        return calcInvTot(baseComputed, 25, penalty);
    }

    public String generateStringAnalysisReport(String[] labels) {
        return process(labels, this.transactionData);
    }
}