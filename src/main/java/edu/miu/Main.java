package edu.miu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.miu.model.Employee;
import edu.miu.repository.EmployeeRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("=== All Employees (with Pension info if available) ===");
        printAllEmployeesSorted();

        System.out.println("\n=== Quarterly Upcoming Enrollees Report ===");
        printQuarterlyUpcomingEnrollees();
    }

    private static void printAllEmployeesSorted() throws IOException {
        List<Employee> sorted = EmployeeRepository.getAllEmployees()
                .stream()
                .sorted(Comparator.comparing(Employee::getYearlySalary).reversed()
                        .thenComparing(Employee::getLastName))
                .collect(Collectors.toList());

//        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty print


        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // makes LocalDate look like 2023-01-17
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty print

        String json = mapper.writeValueAsString(sorted);
        System.out.println(json);


    }

    private static void printQuarterlyUpcomingEnrollees() throws IOException {
        LocalDate today = LocalDate.now();
        LocalDate nextQuarterStart = today.with(today.getMonth().plus(3))
                .with(TemporalAdjusters.firstDayOfMonth());
        Month nextQuarterEndMonth = nextQuarterStart.plusMonths(2).getMonth();
        LocalDate nextQuarterEnd = nextQuarterStart.withMonth(nextQuarterEndMonth.getValue())
                .with(TemporalAdjusters.lastDayOfMonth());

        List<Employee> upcoming = EmployeeRepository.getAllEmployees()
                .stream()
                .filter(e -> e.getPensionPlan() == null) // Not yet enrolled
                .filter(e -> {
                    LocalDate eligibleDate = e.getEmploymentDate().plusYears(3);
                    return !eligibleDate.isBefore(nextQuarterStart) &&
                            !eligibleDate.isAfter(nextQuarterEnd);
                })
                .sorted(Comparator.comparing(Employee::getEmploymentDate).reversed())
                .collect(Collectors.toList());

//        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        String json = mapper.writeValueAsString(upcoming);
//        System.out.println(json);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // makes LocalDate look like 2023-01-17
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty print

        String json = mapper.writeValueAsString(upcoming);
       System.out.println(json);



    }
}