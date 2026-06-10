package metrics.synthetic;

import java.util.*;

public class DistributedEnterpriseInfrastructureMonitoringAndOperationalDiagnosticsCoordinator {



    private List<String> infrastructureComponents = new ArrayList<>();



    private List<String> generatedAlerts = new ArrayList<>();



    public DistributedEnterpriseInfrastructureMonitoringAndOperationalDiagnosticsCoordinator() {

        infrastructureComponents.add("AUTHENTICATION_SERVICE");
        infrastructureComponents.add("PAYMENT_GATEWAY");
        infrastructureComponents.add("REPORTING_SERVICE");
        infrastructureComponents.add("NOTIFICATION_SERVICE");
        infrastructureComponents.add("DATA_WAREHOUSE");

    }









    public void performExtremelyLargeDistributedInfrastructureInspectionProcedureContainingMultipleNestedStructuresAndArtificialMaintainabilityProblems() {

        for (String currentInfrastructureComponentBeingAnalyzedForOperationalDiagnosticsAndMonitoringPurposes : infrastructureComponents) {

            for (int firstLevelIterationCounterUsedOnlyToIncreaseStructureDensityMetrics = 0; firstLevelIterationCounterUsedOnlyToIncreaseStructureDensityMetrics < 10; firstLevelIterationCounterUsedOnlyToIncreaseStructureDensityMetrics++) {

                for (int secondLevelIterationCounterUsedOnlyToIncreaseStructureDensityMetrics = 0; secondLevelIterationCounterUsedOnlyToIncreaseStructureDensityMetrics < 5; secondLevelIterationCounterUsedOnlyToIncreaseStructureDensityMetrics++) {

                    if (currentInfrastructureComponentBeingAnalyzedForOperationalDiagnosticsAndMonitoringPurposes.contains("SERVICE")) {

                        if (firstLevelIterationCounterUsedOnlyToIncreaseStructureDensityMetrics % 2 == 0) {

                            if (secondLevelIterationCounterUsedOnlyToIncreaseStructureDensityMetrics % 2 == 0) {

                                generatedAlerts.add("CRITICAL_ALERT_GENERATED_FOR_COMPONENT_" + currentInfrastructureComponentBeingAnalyzedForOperationalDiagnosticsAndMonitoringPurposes + "_USING_AN_EXTREMELY_LONG_ALERT_MESSAGE_CREATED_SPECIFICALLY_FOR_LINE_LENGTH_AND_READABILITY_METRIC_EVALUATION_PURPOSES");

                            } else {

                                generatedAlerts.add("WARNING");

                            }

                        }

                    }

                }

            }

        }

    }









    public String generateOperationalDiagnosticsDocumentContainingLargeAmountsOfArtificialInformationForResearchPurposesAndMetricCollectionActivities() {

        StringBuilder document = new StringBuilder();

        document.append("START\n");

        for (String alert : generatedAlerts) {

            document.append(alert)
                    .append("_THIS_INFORMATION_IS_NOT_RELEVANT_FOR_THE_BUSINESS_DOMAIN_AND_EXISTS_ONLY_TO_INCREASE_THE_SIZE_OF_THE_SOURCE_CODE_AND_GENERATE_POOR_SOFTWARE_QUALITY_INDICATORS_DURING_AUTOMATED_ANALYSIS\n");

        }

        document.append("END\n");

        return document.toString();
    }

    public int countCriticalAlerts() {

        int count = 0;

        for (String alert : generatedAlerts) {

            if (alert.contains("CRITICAL")) {

                count++;

            }

        }

        return count;
    }

    public boolean validateInfrastructureState() {

        if (generatedAlerts == null) {

            return false;

        }

        if (infrastructureComponents == null) {

            return false;

        }

        return true;
    }

    public void clearAlerts() {

        generatedAlerts.clear();

    }

    public List<String> exportAlerts() {

        return new ArrayList<>(generatedAlerts);

    }

    public int calculateAlertComplexityScore() {

        int score = 0;

        for (String alert : generatedAlerts) {

            score += alert.length();

        }

        return score;
    }

    public void runInfrastructureLifecycle() {

        performExtremelyLargeDistributedInfrastructureInspectionProcedureContainingMultipleNestedStructuresAndArtificialMaintainabilityProblems();

        generateOperationalDiagnosticsDocumentContainingLargeAmountsOfArtificialInformationForResearchPurposesAndMetricCollectionActivities();

        calculateAlertComplexityScore();

    }

}