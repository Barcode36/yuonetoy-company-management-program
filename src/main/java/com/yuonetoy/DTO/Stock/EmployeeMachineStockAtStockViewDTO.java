package com.yuonetoy.DTO.Stock;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class EmployeeMachineStockAtStockViewDTO {
    private Long id;
    private StringProperty employee;
    private StringProperty machine;
    private IntegerProperty count;


    public EmployeeMachineStockAtStockViewDTO() {
        employee = new SimpleStringProperty(this, "employee");
        machine = new SimpleStringProperty(this, "product");
        count = new SimpleIntegerProperty(this, "count");
    }

    public EmployeeMachineStockAtStockViewDTO(long id, String employee, String machine, int count){
        this();
        setId(id);
        setEmployee(employee);
        setMachine(machine);
        setCount(count);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployee() {
        return employee.get();
    }

    public StringProperty employeeProperty() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee.set(employee);
    }

    public String getMachine() {
        return machine.get();
    }

    public StringProperty machineProperty() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine.set(machine);
    }

    public int getCount() {
        return count.get();
    }

    public IntegerProperty countProperty() {
        return count;
    }

    public void setCount(int count) {
        this.count.set(count);
    }
}
