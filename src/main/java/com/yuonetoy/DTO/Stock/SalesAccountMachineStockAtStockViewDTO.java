package com.yuonetoy.DTO.Stock;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class SalesAccountMachineStockAtStockViewDTO {
    private Long id;
    private StringProperty salesAccountName;
    private StringProperty salesAccountMachine;
    private IntegerProperty count;

    public SalesAccountMachineStockAtStockViewDTO() {
        salesAccountName = new SimpleStringProperty(this, "salesAccountName");
        salesAccountMachine = new SimpleStringProperty(this, "salesAccountMachine");
        count = new SimpleIntegerProperty(this, "count");
    }

    public SalesAccountMachineStockAtStockViewDTO(Long id, String salesAccountName, String salesAccountMachine, int count) {
        this();
        setId(id);
        setSalesAccountName(salesAccountName);
        setSalesAccountMachine(salesAccountMachine);
        setCount(count);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalesAccountName() {
        return salesAccountName.get();
    }

    public StringProperty salesAccountNameProperty() {
        return salesAccountName;
    }

    public void setSalesAccountName(String salesAccountName) {
        this.salesAccountName.set(salesAccountName);
    }

    public String getSalesAccountMachine() {
        return salesAccountMachine.get();
    }

    public StringProperty salesAccountMachineProperty() {
        return salesAccountMachine;
    }

    public void setSalesAccountMachine(String salesAccountMachine) {
        this.salesAccountMachine.set(salesAccountMachine);
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
