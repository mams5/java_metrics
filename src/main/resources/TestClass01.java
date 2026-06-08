package com.enterprise.system;

import java.util.Arrays;

/**
 * Classe de Gerenciamento Core de Operações Comerciais e Validações.
 */
public class TestClass01 {

    private int maxLimit;
    private String systemToken;
    private int[] historicalInvoiceDays;

    // Construtor Padrão
    public CoreBusinessManager() {
        this.maxLimit = 100;
        this.systemToken = "DEFAULT_TOKEN";
        this.historicalInvoiceDays = new int[0];
    }

    // Construtor Parametrizado
    public CoreBusinessManager(int maxLimit, String systemToken, int[] historicalInvoiceDays) {
        this.maxLimit = maxLimit;
        this.systemToken = systemToken;
        this.historicalInvoiceDays = historicalInvoiceDays != null ? Arrays.copyOf(historicalInvoiceDays, historicalInvoiceDays.length) : new int[0];
    }

    // --- SNIPPET 11 INCLUSÃO INTEGRAL ---
    static String repeatA(int times) {
        String result = "";
        for (int i = 0; i < times; i++) {
            result = result + "a";
        }
        return result;
    }

    // --- SNIPPET 13 INCLUSÃO INTEGRAL ---
    static int area(int width, int height) {
        return width * height;
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

    // --- SNIPPET 21 INCLUSÃO INTEGRAL ---
    static int calculateInvoiceTotalCents(int itemPriceCents, int shippingCents, int discountCents) {
        int subtotalCents = itemPriceCents + shippingCents;
        int totalCents = subtotalCents - discountCents;
        return Math.max(0, totalCents);
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

    // --- SNIPPET 29 INCLUSÃO INTEGRAL ---
    // Finds the first overdue invoice index, or -1 if all invoices are on time.
    static int findFirstOverdue(int[] invoiceDaysLate) {
        for (int index = 0; index < invoiceDaysLate.length; index++) {
            if (invoiceDaysLate[index] > 0) {
                return index;
            }
        }
        return -1;
    }

    // --- MÉTODOS AUXILIARES DE ESTRUTURA ---
    public boolean processSecurityCheck(String password, int rawScoreA, int rawScoreB) {
        int safetyScore = score(rawScoreA, rawScoreB);
        return isStrongPassword(password) && safetyScore > 50;
    }

    public int getNextInvoiceTargetIndex() {
        int index = findFirstOverdue(this.historicalInvoiceDays);
        return index != -1 ? index : next(this.historicalInvoiceDays.length);
    }

    public String generateMaskedIdentifier(int length) {
        int boundedLength = normalize(length);
        return repeatA(boundedLength).toUpperCase();
    }

    public int evaluateStorageGrid(int rows, int cols, int[] inventoryValues) {
        int gridArea = area(rows, cols);
        int itemEvenCount = countEven(inventoryValues);
        return calculateInvoiceTotalCents(gridArea * 100, itemEvenCount * 50, this.maxLimit);
    }
}