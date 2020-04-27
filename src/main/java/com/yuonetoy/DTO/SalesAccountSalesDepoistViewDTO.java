package com.yuonetoy.DTO;

import com.yuonetoy.Entitiy.BusinessJournalHistory;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachine;
import com.yuonetoy.Model.DepositType;
import com.yuonetoy.Model.TaxBillType;
import com.yuonetoy.Tool.LocalDatePersistenceConverter;
import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

public class SalesAccountSalesDepoistViewDTO {
    private Long id;
    private StringProperty confirmationOfPayment;
    private ObjectProperty<LocalDate> date;
    private StringProperty taxType;
    private StringProperty depositType;
    private StringProperty companyType;
    private StringProperty secondCompanyType;
    private StringProperty employee;
    private StringProperty taxAccount;
    private StringProperty salesAccount;
    private LongProperty sales;
    private LongProperty feesAmount;
    private LongProperty balance;
    private LongProperty depositAmount;
    private ObjectProperty<LocalDate> depositDate;
    private LongProperty remainingAmount;

    public SalesAccountSalesDepoistViewDTO(){
        confirmationOfPayment = new SimpleStringProperty(this, "confirmationOfPayment");
        date = new SimpleObjectProperty<LocalDate>(this, "date");
        taxType = new SimpleStringProperty(this, "taxType");
        depositType = new SimpleStringProperty(this, "depositType");
        companyType = new SimpleStringProperty(this, "companyType");
        secondCompanyType = new SimpleStringProperty(this, "secondCompanyType");
        employee = new SimpleStringProperty(this, "employee");
        taxAccount = new SimpleStringProperty(this, "taxAccount");
        salesAccount = new SimpleStringProperty(this, "salesAccount");
        sales = new SimpleLongProperty(this, "sales");
        feesAmount = new SimpleLongProperty(this, "feesAmount");
        balance = new SimpleLongProperty(this, "balance");
        depositAmount = new SimpleLongProperty(this, "depositAmount");
        depositDate = new SimpleObjectProperty<LocalDate>(this, "depositDate");
        remainingAmount = new SimpleLongProperty(this, "remainingAmount");
    }

    public SalesAccountSalesDepoistViewDTO(Long id, Boolean confirmationOfPayment, LocalDate date,
                                           Integer taxType, Integer depositType, String companyType,
                                           String secondCompanyType, String employee, String taxAccount,
                                           String salesAccount, Long sales, Long balance,
                                           Long depositAmount, LocalDate depositDate) {
        this();
        this.id = id;
        setConfirmationOfPayment(confirmationOfPayment ? "입금 완료" : null);
        setDate(date);
        setTaxType(new TaxBillType(taxType).getName());
        setDepositType(new DepositType(depositType).getName());
        setCompanyType(companyType);
        setSecondCompanyType(secondCompanyType);
        setEmployee(employee);
        setTaxAccount(taxAccount);
        setSalesAccount(salesAccount);
        setSales(sales);
        setFeesAmount(sales - balance);
        setBalance(balance);
        setDepositAmount(depositAmount);
        setDepositDate(depositDate);
        setRemainingAmount(balance - depositAmount);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfirmationOfPayment() {
        return confirmationOfPayment.get();
    }

    public StringProperty confirmationOfPaymentProperty() {
        return confirmationOfPayment;
    }

    public void setConfirmationOfPayment(String confirmationOfPayment) {
        this.confirmationOfPayment.set(confirmationOfPayment);
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

    public String getTaxType() {
        return taxType.get();
    }

    public StringProperty taxTypeProperty() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType.set(taxType);
    }

    public String getDepositType() {
        return depositType.get();
    }

    public StringProperty depositTypeProperty() {
        return depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType.set(depositType);
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

    public String getTaxAccount() {
        return taxAccount.get();
    }

    public StringProperty taxAccountProperty() {
        return taxAccount;
    }

    public void setTaxAccount(String taxAccount) {
        this.taxAccount.set(taxAccount);
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

    public long getSales() {
        return sales.get();
    }

    public LongProperty salesProperty() {
        return sales;
    }

    public void setSales(long sales) {
        this.sales.set(sales);
    }

    public long getFeesAmount() {
        return feesAmount.get();
    }

    public LongProperty feesAmountProperty() {
        return feesAmount;
    }

    public void setFeesAmount(long feesAmount) {
        this.feesAmount.set(feesAmount);
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

    public long getDepositAmount() {
        return depositAmount.get();
    }

    public LongProperty depositAmountProperty() {
        return depositAmount;
    }

    public void setDepositAmount(long depositAmount) {
        this.depositAmount.set(depositAmount);
    }

    public LocalDate getDepositDate() {
        return depositDate.get();
    }

    public ObjectProperty<LocalDate> depositDateProperty() {
        return depositDate;
    }

    public void setDepositDate(LocalDate depositDate) {
        this.depositDate.set(depositDate);
    }

    public long getRemainingAmount() {
        return remainingAmount.get();
    }

    public LongProperty remainingAmountProperty() {
        return remainingAmount;
    }

    public void setRemainingAmount(long remainingAmount) {
        this.remainingAmount.set(remainingAmount);
    }
}
