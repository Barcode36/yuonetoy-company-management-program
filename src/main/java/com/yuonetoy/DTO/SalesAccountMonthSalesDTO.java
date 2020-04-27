package com.yuonetoy.DTO;

import javafx.beans.property.*;

import java.time.LocalDate;

public class SalesAccountMonthSalesDTO {
    private StringProperty salesAccountArea;
    private StringProperty companyType;
    private StringProperty secondCompanyType;
    private StringProperty employee;
    private StringProperty salesAccount;
    private LongProperty[] sales;
    private StringProperty state;
    private ObjectProperty<LocalDate> withdrawalDate;

    public SalesAccountMonthSalesDTO() {
        sales = new LongProperty[12];
        salesAccountArea = new SimpleStringProperty(this, "salesAccountArea");
        companyType = new SimpleStringProperty(this, "companyType");
        state = new SimpleStringProperty(this, "state");
        secondCompanyType = new SimpleStringProperty(this, "secondCompanyType");
        employee = new SimpleStringProperty(this, "employee");
        salesAccount = new SimpleStringProperty(this, "salesAccount");
        for (int i = 0; i< 12 ; i++){
            sales[i] = new SimpleLongProperty(this, "sales" + i);
        }
        state = new SimpleStringProperty(this,"state");
        withdrawalDate = new SimpleObjectProperty<LocalDate>(this,"withdrawalDate");
    }

    public String getState() {
        return state.get();
    }

    public StringProperty stateProperty() {
        return state;
    }

    public void setState(String state) {
        this.state.set(state);
    }

    public LocalDate getWithdrawalDate() {
        return withdrawalDate.get();
    }

    public ObjectProperty<LocalDate> withdrawalDateProperty() {
        return withdrawalDate;
    }

    public void setWithdrawalDate(LocalDate withdrawalDate) {
        this.withdrawalDate.set(withdrawalDate);
    }

    public String getSalesAccountArea() {
        return salesAccountArea.get();
    }
    public void setSalesAccountArea(String salesAccountArea) {
        this.salesAccountArea.set(salesAccountArea);
    }

    public String getCompanyType() {
        return companyType.get();
    }

    public void setCompanyType(String companyType) {
        this.companyType.set(companyType);
    }

    public String getSecondCompanyType() {
        return secondCompanyType.get();
    }

    public void setSecondCompanyType(String secondCompanyType) {
        this.secondCompanyType.set(secondCompanyType);
    }

    public String getEmployee() {
        return employee.get();
    }

    public void setEmployee(String employee) {
        this.employee.set(employee);
    }

    public String getSalesAccount() {
        return salesAccount.get();
    }

    public void setSalesAccount(String salesAccount) {
        this.salesAccount.set(salesAccount);
    }

    public LongProperty[] getSales() {
        return sales;
    }

    public void setSales(int index, long sales) {
        this.sales[index].set(sales);
    }

    public long getSales(int index) {
        return sales[index].get();
    }
}
