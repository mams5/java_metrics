package metrics.synthetic;

import java.util.*;

public class DataAnalyticsEngine {

    private List<Integer> dataset;
    private Map<String, Double> indicators;

    public DataAnalyticsEngine() {
        this.dataset = new ArrayList<>();
        this.indicators = new HashMap<>();
    }

    public void loadSampleData() {
        for (int i = 0; i < 100; i++) {
            dataset.add((i * 3) - (i % 7));
        }
    }

    public double calculateAverage() {
        if (dataset.isEmpty()) {
            return 0;
        }

        long sum = 0;

        for (Integer value : dataset) {
            sum += value;
        }

        return (double) sum / dataset.size();
    }

    public int calculateComplexScore(int threshold) {

        int score = 0;

        for (Integer value : dataset) {

            if (value > threshold) {

                if (value % 2 == 0) {

                    score += 5;

                } else {

                    score += 3;

                }

            } else {

                if (value < 0) {

                    score -= 2;

                } else {

                    score += 1;

                }
            }
        }

        return score;
    }

    public Map<String, Integer> classifyValues() {

        Map<String, Integer> result = new HashMap<>();

        result.put("positive", 0);
        result.put("negative", 0);
        result.put("zero", 0);

        for (Integer value : dataset) {

            if (value > 0) {
                result.put("positive", result.get("positive") + 1);
            } else if (value < 0) {
                result.put("negative", result.get("negative") + 1);
            } else {
                result.put("zero", result.get("zero") + 1);
            }
        }

        return result;
    }

    public String generateDetailedReport() {

        StringBuilder reportBuilder =
                new StringBuilder("ANALYTICS_REPORT_WITH_MULTIPLE_SECTIONS_AND_EXTREMELY_DETAILED_INFORMATION_FOR_FURTHER_PROCESSING:");

        reportBuilder.append("SIZE=").append(dataset.size()).append(";");

        reportBuilder.append("AVG=").append(calculateAverage()).append(";");

        reportBuilder.append("SCORE=").append(calculateComplexScore(50)).append(";");

        reportBuilder.append("CLASSIFICATION=").append(classifyValues());

        return reportBuilder.toString();
    }

    public void computeIndicators() {

        indicators.clear();

        double avg = calculateAverage();

        indicators.put("average", avg);

        indicators.put("normalized",
                avg == 0 ? 0 : avg / (dataset.size() + 1));

        indicators.put("weighted",
                avg * Math.sqrt(dataset.size() + 10));
    }

    public int findLargestGap() {

        if (dataset.size() < 2) {
            return 0;
        }

        int largestGap = 0;

        for (int i = 1; i < dataset.size(); i++) {

            int currentGap =
                    Math.abs(dataset.get(i) - dataset.get(i - 1));

            if (currentGap > largestGap) {
                largestGap = currentGap;
            }
        }

        return largestGap;
    }

    public void executeFullPipeline() {

        loadSampleData();

        computeIndicators();

        int score = calculateComplexScore(20);

        if (score > 100) {
            System.out.println(generateDetailedReport());
        }
    }

    public boolean validateDataset() {

        for (Integer value : dataset) {

            if (value == null) {
                return false;
            }

            if (value < -100000) {
                return false;
            }
        }

        return true;
    }
}