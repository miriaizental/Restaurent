/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantsystem.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import restaurantsystem.component.employee.AddEmployee;
import restaurantsystem.component.employee.DeleteEmployee;
import restaurantsystem.component.employee.UpdateEmployee;
import restaurantsystem.model.Employee;

/**
 *
 * @author Sopnopriyo
 */
public class EmployeeService {

    public EmployeeService() {
    }

    public List<Employee> getAll() {
        List<Employee> employeeList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileInputStream("storage/employee.txt"))) {
            while (scanner.hasNextLine()) {

                String employeeLine = scanner.nextLine();

                String employeeInfo[] = employeeLine.split(",");

                Employee employee = new Employee(employeeInfo[0], employeeInfo[1], Double.parseDouble(employeeInfo[2]));

                employeeList.add(employee);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DeleteEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }
        return employeeList;
    }

    public void create(Employee employee) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("storage/employee.txt", true))) {
            pw.println(employee.getId() + "," + employee.getName() + "," + employee.getSalary());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AddEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized boolean update(String sourceId, Employee updatedEmployee) {
        // Read all the items
        List<Employee> employeeList = getAll();

        int indexToUpdate = -1;
        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);

            if (employee.getId().equalsIgnoreCase(sourceId)) {
                indexToUpdate = i;
            }
        }

        if (indexToUpdate == -1) {
           return false;
        }

        employeeList.set(indexToUpdate, updatedEmployee);

        try {
            Files.delete(Paths.get("storage/employee.txt"));
        } catch (IOException ex) {
            Logger.getLogger(UpdateEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (PrintWriter pw = new PrintWriter(new FileOutputStream("storage/employee.txt"))) {
            employeeList.forEach(employee -> {
                pw.println(employee.getId() + "," + employee.getName() + "," + employee.getSalary());
            });
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UpdateEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }

    public synchronized void delete(String employeeID) {
        List<Employee> employeeList = getAll();

        // find the employee to be deleted
        for (int i = 0; i < employeeList.size(); i++) {

            Employee employee = employeeList.get(i);

            if (employee.getId().equalsIgnoreCase(employeeID)) {
                employeeList.remove(employee);
            }
        }

        try {
            // Delete the entire file
            Files.delete(Paths.get("storage/employee.txt"));
        } catch (IOException ex) {
            Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Create a new file and write new data into the file
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("storage/employee.txt"))) {
            employeeList.forEach(employee -> {
                pw.println(employee.getId() + "," + employee.getName() + "," + employee.getSalary());
            });
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
