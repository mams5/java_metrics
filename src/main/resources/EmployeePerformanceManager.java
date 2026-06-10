package metrics.synthetic;

import java.util.*;

public class EmployeePerformanceManager {

    private List<Employee> employees;

    public EmployeePerformanceManager() {
        employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        if (employee != null) {
            employees.add(employee);
        }
    }

    public Employee findEmployeeById(String id) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                return employee;
            }
        }

        return null;
    }

    public boolean removeEmployee(String id) {
        Employee employee = findEmployeeById(id);

        if (employee != null) {
            employees.remove(employee);
            return true;
        }

        return false;
    }

    public double calculateAverageScore() {
        if (employees.isEmpty()) {
            return 0;
        }

        double total = 0;

        for (Employee employee : employees) {
            total += employee.getPerformanceScore();
        }

        return total / employees.size();
    }

    public Employee findBestEmployee() {
        Employee best = null;

        for (Employee employee : employees) {

            if (best == null ||
                employee.getPerformanceScore() > best.getPerformanceScore()) {

                best = employee;
            }
        }

        return best;
    }

    public Employee findWorstEmployee() {
        Employee worst = null;

        for (Employee employee : employees) {

            if (worst == null ||
                employee.getPerformanceScore() < worst.getPerformanceScore()) {

                worst = employee;
            }
        }

        return worst;
    }

    public List<Employee> findEmployeesAboveScore(double score) {
        List<Employee> result = new ArrayList<>();

        for (Employee employee : employees) {
            if (employee.getPerformanceScore() >= score) {
                result.add(employee);
            }
        }

        return result;
    }

    public Map<String, Integer> countEmployeesByDepartment() {
        Map<String, Integer> result = new HashMap<>();

        for (Employee employee : employees) {
            result.merge(
                    employee.getDepartment(),
                    1,
                    Integer::sum
            );
        }

        return result;
    }

    public double calculateDepartmentAverage(String department) {

        double total = 0;
        int count = 0;

        for (Employee employee : employees) {

            if (employee.getDepartment().equals(department)) {
                total += employee.getPerformanceScore();
                count++;
            }

        }

        return count == 0 ? 0 : total / count;
    }

    public void applyBonus(double threshold, double bonus) {

        for (Employee employee : employees) {

            if (employee.getPerformanceScore() >= threshold) {

                employee.setSalary(
                        employee.getSalary() + bonus
                );

            }

        }
    }

    public List<Employee> getEmployeesSortedByScore() {

        List<Employee> copy = new ArrayList<>(employees);

        copy.sort(
                Comparator.comparingDouble(
                        Employee::getPerformanceScore
                ).reversed()
        );

        return copy;
    }

    public int getEmployeeCount() {
        return employees.size();
    }
}