package edu.miu.repository;

import edu.miu.model.Employee;
import edu.miu.model.PensionPlan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private static final List<Employee> employees = new ArrayList<>();

    static {
        // Employee 1: Daniel Agar (with Pension)
        Employee e1 = new Employee(1, "Daniel", "Agar", LocalDate.of(2018, 1, 17), 105945.50);
        e1.setPensionPlan(new PensionPlan("EX1089", LocalDate.of(2023, 1, 17), 100.00));

        // Employee 2: Benard Shaw (no pension)
        Employee e2 = new Employee(2, "Benard", "Shaw", LocalDate.of(2022, 9, 3), 197750.00);

        // Employee 3: Carly Agar (with Pension)
        Employee e3 = new Employee(3, "Carly", "Agar", LocalDate.of(2014, 5, 16), 842000.75);
        e3.setPensionPlan(new PensionPlan("SM2307", LocalDate.of(2019, 11, 4), 1555.50));

        // Employee 4: Wesley Schneider (no pension)
        Employee e4 = new Employee(4, "Wesley", "Schneider", LocalDate.of(2022, 7, 21), 74500.00);

        // Employee 5: Anna Wiltord (no pension)
        Employee e5 = new Employee(5, "Anna", "Wiltord", LocalDate.of(2022, 6, 15), 85750.00);

        // Employee 6: Yosef Tesfalem (no pension)
        Employee e6 = new Employee(6, "Yosef", "Tesfalem", LocalDate.of(2022, 10, 31), 100000.00);

        // Add all to the list
        employees.add(e1);
        employees.add(e2);
        employees.add(e3);
        employees.add(e4);
        employees.add(e5);
        employees.add(e6);
    }

    public static List<Employee> getAllEmployees() {
        return employees;
    }


}
