package com.yuonetoy.DTO.Account.SalesAccount;

import com.yuonetoy.DTO.Area.SalesAccountAreaDTO;
import com.yuonetoy.DTO.CompanyType.CompanyTypeDTO;
import com.yuonetoy.DTO.CompanyType.SecondCompanyTypeDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.SalesAccountMachineDetailDTO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class SalesAccountPreviewDTO {
    private Long id;
    private StringProperty state;
    private StringProperty name;
    private StringProperty address;
    private StringProperty ph;
    private StringProperty salesAccountAreaName;
    private StringProperty companyTypeName;
    private StringProperty secondCompanyTypeName;
    private StringProperty employeeName;
    private ObjectProperty<LocalDate> entryDate;

    public SalesAccountPreviewDTO(long id, String name, String address, String ph, String salesAccountAreaName, String companyTypeName, String secondCompanyTypeName, String employeeName, LocalDate entryDate, Boolean isUsing) {
        this();
        setId(id);
        setName(name);
        setAddress(address);
        setPh(ph);
        setSalesAccountAreaName(salesAccountAreaName);
        setCompanyTypeName(companyTypeName);
        setSecondCompanyTypeName(secondCompanyTypeName);
        setEmployeeName(employeeName);
        setEntryDate(entryDate);

        if (isUsing)
            setState("입점");
        else
            setState("철수");
    }

    public SalesAccountPreviewDTO() {
        state = new SimpleStringProperty(this, "state");
        name = new SimpleStringProperty(this, "name");
        address = new SimpleStringProperty(this, "address");
        ph = new SimpleStringProperty(this, "ph");
        entryDate = new SimpleObjectProperty<LocalDate>(this, "entryDate");
        salesAccountAreaName = new SimpleStringProperty(this, "salesAccountAreaName");
        companyTypeName = new SimpleStringProperty(this, "companyTypeName");
        secondCompanyTypeName = new SimpleStringProperty(this, "secondCompanyTypeName");
        employeeName = new SimpleStringProperty(this, "employeeName");
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

    public String getPh() {
        return ph.get();
    }

    public void setPh(String ph) {
        this.ph.set(ph);
    }

    public LocalDate getEntryDate() {
        return entryDate.get();
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate.set(entryDate);
    }

    public String getSalesAccountAreaName() {
        return salesAccountAreaName.get();
    }

    public StringProperty salesAccountAreaNameProperty() {
        return salesAccountAreaName;
    }

    public void setSalesAccountAreaName(String salesAccountAreaName) {
        this.salesAccountAreaName.set(salesAccountAreaName);
    }

    public String getCompanyTypeName() {
        return companyTypeName.get();
    }

    public StringProperty companyTypeNameProperty() {
        return companyTypeName;
    }

    public void setCompanyTypeName(String companyTypeName) {
        this.companyTypeName.set(companyTypeName);
    }

    public String getSecondCompanyTypeName() {
        return secondCompanyTypeName.get();
    }

    public StringProperty secondCompanyTypeNameProperty() {
        return secondCompanyTypeName;
    }

    public void setSecondCompanyTypeName(String secondCompanyTypeName) {
        this.secondCompanyTypeName.set(secondCompanyTypeName);
    }

    public String getEmployeeName() {
        return employeeName.get();
    }

    public StringProperty employeeNameProperty() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName.set(employeeName);
    }

    @Override
    public String toString() {
        return name.get();
    }
}
