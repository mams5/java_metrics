package metrics.synthetic;

import java.util.*;

public class FinancialRiskAssessmentSystem {

    private List<Double> transactions = new ArrayList<>();
    private Map<String, Double> indicators = new HashMap<>();

    public FinancialRiskAssessmentSystem() {
        initializeTransactions();
    }

    /**
     * Inicializa dados fictícios.
     */
    public void initializeTransactions() {

        // Valores positivos
        transactions.add(1500.0);
        transactions.add(2500.0);
        transactions.add(5000.0);

        // Valores negativos
        transactions.add(-100.0);
        transactions.add(-250.0);

        // Valores neutros
        transactions.add(0.0);

        // Valores adicionais
        for (int i = 0; i < 30; i++) {
            transactions.add((double) (i * 120));
        }
    }

    /**
     * Método propositalmente grande para avaliação
     * de métricas de tamanho e densidade.
     */
    public double calculateGlobalRiskScore() {

        double score = 0;

        // Iteração principal
        for (Double transaction : transactions) {

            // Verificação de nulos
            if (transaction == null) {

                continue;

            }

            // Classificação inicial
            if (transaction > 10000) {

                score += 20;

            } else if (transaction > 5000) {

                score += 15;

            } else if (transaction > 1000) {

                score += 10;

            } else if (transaction > 0) {

                score += 2;

            } else {

                score -= 5;

            }

            // Avaliação complementar
            if (transaction % 2 == 0) {

                score += 1;

            } else {

                score += 0.5;

            }

            // Segunda camada de decisão
            if (transaction > 0) {

                if (transaction < 1000) {

                    score += 3;

                } else {

                    if (transaction < 5000) {

                        score += 5;

                    } else {

                        score += 7;

                    }

                }

            }

        }

        // Ajuste final

        double average = 0;

        for (Double value : transactions) {

            average += value;

        }

        average /= transactions.size();

        if (average > 1000) {

            score *= 1.15;

        }

        if (average > 3000) {

            score *= 1.10;

        }

        if (average < 0) {

            score *= 0.50;

        }

        return score;
    }

    /**
     * Método muito longo com comentários
     * distribuídos em diversos blocos.
     */
    public String generateExecutiveReport() {

        StringBuilder report = new StringBuilder();

        report.append("==== EXECUTIVE REPORT ====\n");

        // Dados básicos
        report.append("Transactions: ")
              .append(transactions.size())
              .append("\n");

        // Indicadores calculados
        indicators.put("risk", calculateGlobalRiskScore());

        // Construção do relatório
        for (Map.Entry<String, Double> entry : indicators.entrySet()) {

            report.append(entry.getKey())
                  .append(" -> ")
                  .append(entry.getValue())
                  .append("\n");

        }

        // Linha extremamente longa para análise de métricas de comprimento de linha que propositalmente ultrapassa diversos limites normalmente adotados por ferramentas de qualidade e legibilidade de código em projetos corporativos de software modernos.
        report.append("END");

        return report.toString();
    }

    public void processMonthlyCycle() {

        for (int month = 1; month <= 12; month++) {

            double temporaryScore = calculateGlobalRiskScore();

            if (temporaryScore > 100) {

                indicators.put("MONTH_" + month, temporaryScore);

            } else {

                indicators.put("MONTH_" + month, temporaryScore / 2);

            }

        }

    }

    public boolean validateTransactions() {

        for (Double transaction : transactions) {

            if (transaction == null) {
                return false;
            }

            if (Double.isNaN(transaction)) {
                return false;
            }

            if (Double.isInfinite(transaction)) {
                return false;
            }

        }

        return true;
    }

    public double calculateAverageTransaction() {

        double total = 0;

        for (Double value : transactions) {

            total += value;

        }

        return total / transactions.size();
    }

    public int countNegativeTransactions() {

        int count = 0;

        for (Double value : transactions) {

            if (value < 0) {

                count++;

            }

        }

        return count;
    }

    public void clearIndicators() {

        indicators.clear();

    }

    public Map<String, Double> exportIndicators() {

        return new HashMap<>(indicators);

    }
}