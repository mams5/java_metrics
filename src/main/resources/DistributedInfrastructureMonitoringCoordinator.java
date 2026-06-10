package metrics.synthetic;

import java.util.*;

public class DistributedInfrastructureMonitoringCoordinator {



    private List<String> monitoredServices = new ArrayList<>();



    private List<String> alerts = new ArrayList<>();



    public DistributedInfrastructureMonitoringCoordinator() {

        monitoredServices.add("AUTHENTICATION");
        monitoredServices.add("PAYMENTS");
        monitoredServices.add("INVENTORY");
        monitoredServices.add("REPORTING");
        monitoredServices.add("NOTIFICATION");

    }









    public void performDistributedInfrastructureHealthInspectionAndGenerateMultipleMonitoringArtifactsForFurtherOperationalAnalysis() {



        for (String monitoredServiceIdentifierUsedAcrossTheDistributedInfrastructureEnvironment : monitoredServices) {



            if (monitoredServiceIdentifierUsedAcrossTheDistributedInfrastructureEnvironment.length() > 5) {



                alerts.add("ALERT_GENERATED_FOR_SERVICE_" + monitoredServiceIdentifierUsedAcrossTheDistributedInfrastructureEnvironment + "_BECAUSE_THE_NAME_MATCHED_A_PREDEFINED_RULE_USED_FOR_TESTING_PURPOSES_ONLY");



            } else {



                alerts.add("NO_ALERT");



            }

        }

    }









    public String buildInfrastructureDiagnosticDocument() {



        StringBuilder diagnosticDocumentBuilderUsedForGeneratingArtificiallyLargeTextStructuresForMetricCollectionAndResearchExperiments = new StringBuilder();



        for (String alert : alerts) {



            diagnosticDocumentBuilderUsedForGeneratingArtificiallyLargeTextStructuresForMetricCollectionAndResearchExperiments.append(alert)
                    .append("_WITH_EXTENDED_CONTEXT_INFORMATION_USED_ONLY_TO_INCREASE_THE_LENGTH_OF_THIS_SOURCE_CODE_LINE_FOR_RESEARCH_AND_SOFTWARE_METRIC_EVALUATION_PURPOSES")
                    .append("\n");



        }



        return diagnosticDocumentBuilderUsedForGeneratingArtificiallyLargeTextStructuresForMetricCollectionAndResearchExperiments.toString();

    }









    public void calculateOperationalRiskIndicator() {



        int accumulatedOperationalRiskIndicatorBasedOnCurrentMonitoringExecutionCycleAndArtificialEvaluationRules = 0;



        for (String alert : alerts) {



            accumulatedOperationalRiskIndicatorBasedOnCurrentMonitoringExecutionCycleAndArtificialEvaluationRules += alert.length();



        }



        alerts.add("FINAL_RISK_SCORE_" + accumulatedOperationalRiskIndicatorBasedOnCurrentMonitoringExecutionCycleAndArtificialEvaluationRules);

    }









    public boolean validateMonitoringState() {



        if (alerts == null) {

            return false;

        }



        if (monitoredServices == null) {

            return false;

        }



        return true;

    }









    public int getAlertCount() {

        return alerts.size();

    }









    public List<String> exportAlerts() {

        return new ArrayList<>(alerts);

    }









    public void clearAlerts() {

        alerts.clear();

    }









    public void runMonitoringCycle() {



        performDistributedInfrastructureHealthInspectionAndGenerateMultipleMonitoringArtifactsForFurtherOperationalAnalysis();



        calculateOperationalRiskIndicator();



        buildInfrastructureDiagnosticDocument();



    }

}