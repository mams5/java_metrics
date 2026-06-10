package metrics.synthetic;

import java.util.*;

public class WorkflowExecutionManager {

    private Queue<String> pendingTasks;
    private List<String> completedTasks;

    public WorkflowExecutionManager() {
        pendingTasks = new LinkedList<>();
        completedTasks = new ArrayList<>();
    }

    public void initializeWorkflow() {

        pendingTasks.add("IMPORT");
        pendingTasks.add("VALIDATE");
        pendingTasks.add("PROCESS");
        pendingTasks.add("EXPORT");
        pendingTasks.add("ARCHIVE");
    }

    public void executeTasks() {

        while (!pendingTasks.isEmpty()) {

            String task = pendingTasks.poll();

            try {

                processTask(task);

                completedTasks.add(task);

            } catch (RuntimeException ex) {

                System.out.println("Task failed: " + task);

            }
        }
    }

    public void processTask(String task) {

        switch (task) {

            case "IMPORT":
                simulateWork(5);
                break;

            case "VALIDATE":
                simulateWork(10);
                break;

            case "PROCESS":
                simulateWork(15);
                break;

            case "EXPORT":
                simulateWork(8);
                break;

            default:
                simulateWork(2);
        }
    }

    public void simulateWork(int iterations) {

        long accumulator = 0;

        for (int i = 0; i < iterations; i++) {

            for (int j = 0; j < iterations; j++) {

                accumulator += (i * j);

                if (accumulator % 7 == 0) {

                    accumulator += 3;

                } else {

                    accumulator -= 1;

                }
            }
        }
    }

    public double calculateCompletionRate() {

        int total =
                pendingTasks.size() + completedTasks.size();

        if (total == 0) {
            return 0;
        }

        return (double) completedTasks.size() / total;
    }

    public String generateAuditLog() {

        StringBuilder log =
                new StringBuilder();

        for (String task : completedTasks) {

            log.append("TASK_EXECUTED_SUCCESSFULLY_WITH_IDENTIFIER_")
               .append(task)
               .append("_AT_TIME_")
               .append(System.nanoTime())
               .append("\n");
        }

        return log.toString();
    }

    public int countTasksContainingLetter(char letter) {

        int count = 0;

        for (String task : completedTasks) {

            if (task.indexOf(letter) >= 0) {
                count++;
            }
        }

        return count;
    }

    public boolean validateWorkflowState() {

        if (completedTasks == null) {
            return false;
        }

        for (String task : completedTasks) {

            if (task == null || task.isBlank()) {
                return false;
            }
        }

        return true;
    }

    public Map<String, Integer> buildStatistics() {

        Map<String, Integer> stats = new HashMap<>();

        stats.put("completed", completedTasks.size());

        stats.put("pending", pendingTasks.size());

        stats.put("containsE",
                countTasksContainingLetter('E'));

        return stats;
    }

    public void runWorkflowLifecycle() {

        initializeWorkflow();

        executeTasks();

        buildStatistics();

        generateAuditLog();
    }
}