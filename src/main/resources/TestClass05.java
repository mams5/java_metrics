package com.enterprise.inventory;

import java.util.Arrays;

/**
 * Classe responsável pelo controle físico e contábil de armazéns logísticos,
 * auditoria de pacotes e validação de credenciais de operadores de pátio.
 */
public class TestClass05 {

    private int storageUnitId;
    private int[] itemStockBuffer;
    private String operatorAccessKey;

    // Construtor Padrão
    public WarehouseInventoryController() {
        this.storageUnitId = 1001;
        this.itemStockBuffer = new int[]{5, 12, 0, 8, -2, 14};
        this.operatorAccessKey = "OP-MASTER-77";
    }

    // Construtor Parametrizado
    public WarehouseInventoryController(int storageUnitId, int[] itemStockBuffer, String operatorAccessKey) {
        this.storageUnitId = storageUnitId;
        this.itemStockBuffer = itemStockBuffer != null ? Arrays.copyOf(itemStockBuffer, itemStockBuffer.length) : new int[0];
        this.operatorAccessKey = operatorAccessKey;
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

    // --- MÉTODOS AUXILIARES DE ESTRUTURA ---
    public int calculateMaxPalletCapacity(int floorWidth, int floorHeight) {
        int groundArea = area(floorWidth, floorHeight);
        return compute(groundArea, 2, 5);
    }

    public boolean authenticateOperator(String digitalSignature) {
        return isStrongPassword(digitalSignature) && this.operatorAccessKey.length() > 5;
    }

    public int runStockDiscrepancyCheck() {
        int activePositives = run(this.itemStockBuffer);
        int balancedEvens = countEven(this.itemStockBuffer);
        return calculateInvoiceTotalCents(activePositives * 10, balancedEvens * 2, this.storageUnitId);
    }

    public int determineNextInspectionBlock() {
        int lateIndex = findFirstOverdue(this.itemStockBuffer);
        if (lateIndex == -1) {
            return next(this.storageUnitId);
        }
        return next(lateIndex);
    }
}