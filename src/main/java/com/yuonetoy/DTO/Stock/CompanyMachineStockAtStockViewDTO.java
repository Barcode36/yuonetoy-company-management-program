package com.yuonetoy.DTO.Stock;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class CompanyMachineStockAtStockViewDTO {
    private Long id;
    private StringProperty company;
    private StringProperty machine;
    private IntegerProperty count;

    public CompanyMachineStockAtStockViewDTO() {
        company = new SimpleStringProperty(this, "company");
        machine = new SimpleStringProperty(this, "product");
        count = new SimpleIntegerProperty(this, "count");
    }

    public CompanyMachineStockAtStockViewDTO(Long id, String company, String machine, Integer count) {
        this();
        this.id = id;
        setCompany(company);
        setMachine(machine);
        setCount(count);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company.get();
    }

    public StringProperty companyProperty() {
        return company;
    }

    public void setCompany(String company) {
        this.company.set(company);
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

    public void setCount(int count) {
        this.count.set(count);
    }

    @Override
    public String toString() {
        return machine.getName() + " (남은 재고 : " + count + "개)";
    }
}
