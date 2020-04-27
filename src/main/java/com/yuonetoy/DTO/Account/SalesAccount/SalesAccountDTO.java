package com.yuonetoy.DTO.Account.SalesAccount;

import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.Entitiy.Employee;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class SalesAccountDTO {
    private Long id;
    private StringProperty name;
    private StringProperty address;
    private LocalDate entryDate;
    private ObjectProperty<EmployeeDTO> employee;

    public SalesAccountDTO() {
        name = new SimpleStringProperty(this, "name");
        address = new SimpleStringProperty(this, "address");
        employee = new SimpleObjectProperty<>(this, "employee");
    }

    public SalesAccountDTO(Long id, String name, String address) {
        this();
        this.id = id;
        setName(name);
        setAddress(address);
    }

    public SalesAccountDTO(Long id, String name, String address, LocalDate entryDate, long employeeId, String employeeName) {
        this();
        this.id = id;
        setName(name);
        setAddress(address);
        setEntryDate(entryDate);

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employeeId);
        employeeDTO.setName(employeeName);

        setEmployee(employeeDTO);
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public EmployeeDTO getEmployee() {
        return employee.get();
    }

    public ObjectProperty<EmployeeDTO> employeeProperty() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee.set(employee);
    }

    @Override
    public String toString() {
        return getName();
    }
}