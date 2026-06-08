package com.enterprise.sales;

import java.util.Arrays;

/**
 * Classe responsável pelo processamento de faturamento de pedidos corporativos,
 * gerenciamento de cotas de envio e validação de tokens de segurança de vendas.
 */
public class TestClass09 {

    private int corporateDiscountTier;
    private String dispatchZoneToken;
    private int[] regionalOverdueBuffer;

    // Construtor Padrão
    public SalesOrderProcessor() {
        this.corporateDiscountTier = 250;
        this.dispatchZoneToken = "SECURE_ZONE_ALPHA";
        this.regionalOverdueBuffer = new int[]{0, 0, 14, 3, -1, 30};
    }

    // Construtor Parametrizado
    public SalesOrderProcessor(int corporateDiscountTier, String dispatchZoneToken, int[] regionalOverdueBuffer) {
        this.corporateDiscountTier = corporateDiscountTier;
        this.dispatchZoneToken = dispatchZoneToken;
        this.regionalOverdueBuffer = regionalOverdueBuffer != null ? Arrays.copyOf(regionalOverdueBuffer, regionalOverdueBuffer.length) : new int[0];
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
    public int compileOrderPackageMetrics(int packWidth, int packHeight, int unitValue) {
        int boxArea = area(packWidth, packHeight);
        return compute(boxArea, unitValue, 3);
    }

    public boolean validateSalesConsoleToken(String userAccessKey) {
        return isStrongPassword(userAccessKey) && this.dispatchZoneToken.startsWith("SECURE");
    }

    public int evaluateNextOverduePipelineCents() {
        int lateIndex = findFirstOverdue(this.regionalOverdueBuffer);
        int finalFactor = (lateIndex == -1) ? next(0) : next(lateIndex);
        int incrementalValue = aggregate(finalFactor, 10, 5, 2000);
        return calculateInvoiceTotalCents(incrementalValue, 500, this.corporateDiscountTier);
    }

    public String generateInvoiceTrackingCode(int repeatSize) {
        return repeatA(repeatSize).concat("-").concat(this.dispatchZoneToken);
    }
}