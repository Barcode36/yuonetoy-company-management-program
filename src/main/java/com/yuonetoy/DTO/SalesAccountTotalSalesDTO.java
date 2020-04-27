package com.yuonetoy.DTO;

import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachine;
import javafx.beans.property.*;

import java.time.LocalDate;

public class SalesAccountTotalSalesDTO {
    private ObjectProperty<LocalDate> entryDate;
    private StringProperty salesAccountArea;
    private StringProperty companyType;
    private StringProperty secondCompanyType;
    private StringProperty employee;
    private StringProperty salesAccount;
    private StringProperty salesAccountMachineName;
    private IntegerProperty machineCount;
    private IntegerProperty productCount;
    private IntegerProperty plusValue;
    private IntegerProperty minusValue;
    private DoubleProperty fees;
    private LongProperty sales;
    private LongProperty feeAmount;
    private LongProperty balance;
    private LongProperty refundAmount;
    private ObjectProperty<LocalDate> date;
    private StringProperty state;
    private ObjectProperty<LocalDate> withdrawalDate;

    public SalesAccountTotalSalesDTO() {
        entryDate = new SimpleObjectProperty<>(this, "entryDate");
        salesAccountArea = new SimpleStringProperty(this, "salesAccountArea");
        companyType = new SimpleStringProperty(this, "companyType");
        secondCompanyType = new SimpleStringProperty(this, "secondCompanyType");
        employee = new SimpleStringProperty(this, "employee");
        salesAccount = new SimpleStringProperty(this, "salesAccount");
        salesAccountMachineName = new SimpleStringProperty(this, "SalesAccountMachine");
        machineCount = new SimpleIntegerProperty(this, "machineCount");
        productCount = new SimpleIntegerProperty(this, "productCount");
        plusValue = new SimpleIntegerProperty(this, "plusValue");
        minusValue = new SimpleIntegerProperty(this, "minusValue");
        fees = new SimpleDoubleProperty(this, "fee");
        sales = new SimpleLongProperty(this, "sales");
        feeAmount = new SimpleLongProperty(this, "feeAmount");
        balance = new SimpleLongProperty(this, "balance");
        refundAmount = new SimpleLongProperty(this, "refundAmount");
        date = new SimpleObjectProperty<>(this, "date");
        state = new SimpleStringProperty(this, "state");
        withdrawalDate = new SimpleObjectProperty<>(this, "withdrawalDate");
    }

    public SalesAccountTotalSalesDTO(LocalDate entryDate, String salesAccountArea, String companyType, String secondCompanyType,
                                       String employee, String salesAccount, String salesAccountMachineName, int machineCount, int productCount,
                                       int plusValue, int minusValue, double fees,
                                       long sales , long balance, long refundAmount, LocalDate date, Boolean isUsing, LocalDate withdrawalDate) {
        this();
        setEntryDate(entryDate);
        setSalesAccountArea(salesAccountArea);
        setCompanyType(companyType);
        setSecondCompanyType(secondCompanyType);
        setEmployee(employee);
        setSalesAccount(salesAccount);
        setSalesAccountMachineName(salesAccountMachineName);
        setMachineCount(machineCount);
        setProductCount(productCount);
        setPlusValue(plusValue);
        setMinusValue(minusValue);
        setFees(fees);
        setSales(sales);
        setFeeAmount(sales - balance);
        setBalance(balance);
        setRefundAmount(refundAmount);
        setDate(date);

        if (isUsing)
            setState("입점");
        else
            setState("철수");

        setWithdrawalDate(withdrawalDate);
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

    public LocalDate getEntryDate() {
        return entryDate.get();
    }

    public ObjectProperty<LocalDate> entryDateProperty() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate.set(entryDate);
    }

    public String getSalesAccountArea() {
        return salesAccountArea.get();
    }

    public StringProperty salesAccountAreaProperty() {
        return salesAccountArea;
    }

    public void setSalesAccountArea(String salesAccountArea) {
        this.salesAccountArea.set(salesAccountArea);
    }

    public String getCompanyType() {
        return companyType.get();
    }

    public StringProperty companyTypeProperty() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType.set(companyType);
    }

    public String getSecondCompanyType() {
        return secondCompanyType.get();
    }

    public StringProperty secondCompanyTypeProperty() {
        return secondCompanyType;
    }

    public void setSecondCompanyType(String secondCompanyType) {
        this.secondCompanyType.set(secondCompanyType);
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

    public String getSalesAccount() {
        return salesAccount.get();
    }

    public StringProperty salesAccountProperty() {
        return salesAccount;
    }

    public void setSalesAccount(String salesAccount) {
        this.salesAccount.set(salesAccount);
    }

    public String getSalesAccountMachineName() {
        return salesAccountMachineName.get();
    }

    public StringProperty salesAccountMachineNameProperty() {
        return salesAccountMachineName;
    }

    public void setSalesAccountMachineName(String salesAccountMachineName) {
        this.salesAccountMachineName.set(salesAccountMachineName);
    }

    public int getMachineCount() {
        return machineCount.get();
    }

    public IntegerProperty machineCountProperty() {
        return machineCount;
    }

    public void setMachineCount(int machineCount) {
        this.machineCount.set(machineCount);
    }

    public int getProductCount() {
        return productCount.get();
    }

    public IntegerProperty productCountProperty() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount.set(productCount);
    }

    public DoubleProperty feesProperty() {
        return fees;
    }

    public int getPlusValue() {
        return plusValue.get();
    }

    public IntegerProperty plusValueProperty() {
        return plusValue;
    }

    public void setPlusValue(int plusValue) {
        this.plusValue.set(plusValue);
    }

    public int getMinusValue() {
        return minusValue.get();
    }

    public IntegerProperty minusValueProperty() {
        return minusValue;
    }

    public void setMinusValue(int minusValue) {
        this.minusValue.set(minusValue);
    }

    public double getFees() {
        return fees.get();
    }

    public void setFees(double fees) {
        this.fees.set(fees);
    }

    public long getBalance() {
        return balance.get();
    }

    public LongProperty balanceProperty() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance.set(balance);
    }

    public long getFeeAmount() {
        return feeAmount.get();
    }

    public LongProperty feeAmountProperty() {
        return feeAmount;
    }

    public void setFeeAmount(long feeAmount) {
        this.feeAmount.set(feeAmount);
    }

    public long getSales() {
        return sales.get();
    }

    public LongProperty salesProperty() {
        return sales;
    }

    public void setSales(long sales) {
        this.sales.set(sales);
    }

    public long getRefundAmount() {
        return refundAmount.get();
    }

    public LongProperty refundAmountProperty() {
        return refundAmount;
    }

    public void setRefundAmount(long refundAmount) {
        this.refundAmount.set(refundAmount);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }
}
