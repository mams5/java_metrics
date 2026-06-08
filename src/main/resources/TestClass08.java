package com.enterprise.membership;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Classe responsável pelo processamento de assinaturas corporativas, contabilidade
 * de pontos promocionais e verificação cadastral de membros da plataforma.
 */
public class TestClass08 {

    private int tierThreshold;
    private String countryRegion;
    private int[] historicalPoints;

    // Construtor Padrão
    public EnterpriseMembershipModel() {
        this.tierThreshold = 500;
        this.countryRegion = "NAMER";
        this.historicalPoints = new int[]{200, 15, 0, -4, 42, 88};
    }

    // Construtor Parametrizado
    public EnterpriseMembershipModel(int tierThreshold, String countryRegion, int[] historicalPoints) {
        this.tierThreshold = tierThreshold;
        this.countryRegion = countryRegion;
        this.historicalPoints = historicalPoints != null ? historicalPoints.clone() : new int[0];
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

    // --- SNIPPET 19 INCLUSÃO INTEGRAL ---
    int addTax(int net) {
        return net + (net * 10 / 100);
    }

    @Test
    void addTax_shouldApplyTenPercent() {
        int netPrice = 200;

        int finalPrice = addTax(netPrice);

        assertEquals(220, finalPrice);
    }

    // --- SNIPPET 20 INCLUSÃO INTEGRAL ---
    int t(int n) {
        return n + (n * 10 / 100);
    }

    @Test
    void x() {
        assertEquals(220, t(200));
        assertEquals(110, t(100));
        assertEquals(0, t(0));
    }

    // --- SNIPPET 23 INCLUSÃO INTEGRAL ---
    static boolean isStrongPassword(String password) {
        boolean longEnough = password.length() >= 8;
        boolean hasUpper = !password.equals(password.toLowerCase());
        boolean hasDigit = password.matches(".*\\d.*");
        return longEnough && hasUpper && hasDigit;
    }

    // --- SNIPPET 25 INCLUSÃO INTEGRAL ---
    static int next(int n) {
        return n + 1;
    }

    // --- SNIPPET 33 INCLUSÃO INTEGRAL ---
    static void processUserDataAndCalculateMetricsForAnalysis(String userIdentifier, int userAccountIdentificationNumber, String userEmailIdentifierAddress, long userRegistrationTimestampInMilliseconds) {
        String processedId = userIdentifier.trim();
        int accountNum = userAccountIdentificationNumber;
        String email = userEmailIdentifierAddress;
        long regTime = userRegistrationTimestampInMilliseconds;
        String userId = processedId + "_" + accountNum;
        String fullInfo = userId + "_" + email + "_" + regTime;
    }

    // --- SNIPPET 38 INCLUSÃO INTEGRAL ---
    static int categorizeAndCount(int[] values) {
        int positive = 0, negative = 0, zero = 0;
        for (int v : values) {
            if (v > 0) {
                positive++;
            } else if (v < 0) {
                negative++;
            } else {
                zero++;
            }
        }
        int result = 0;
        while (positive > 0) {
            result += positive;
            positive--;
        }
        for (int i = 0; i < negative; i++) {
            result -= i;
        }
        return result + zero;
    }

    // --- MÉTODOS AUXILIARES DE ESTRUTURA ---
    public int calculateBillingTaxValue(int netValue, boolean useCompactFormula) {
        if (useCompactFormula) {
            return t(netValue);
        }
        return addTax(netValue);
    }

    public boolean verifyProfileSecurityAndIncrement(String securityPass, int currentStep) {
        boolean check = isStrongPassword(securityPass);
        int upcoming = next(currentStep);
        return check && (upcoming > this.tierThreshold);
    }

    public String generateMemberAnalysisReport(String[] keywords) {
        return process(keywords, this.historicalPoints);
    }

    public int extractEvenCategorizationMetric() {
        int internalEvens = countEven(this.historicalPoints);
        int analyticalBalance = categorizeAndCount(this.historicalPoints);
        return internalEvens + analyticalBalance;
    }
}