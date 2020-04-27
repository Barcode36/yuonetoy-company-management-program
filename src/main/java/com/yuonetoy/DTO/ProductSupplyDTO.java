package com.yuonetoy.DTO;

import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import javafx.beans.property.*;

import java.time.LocalDate;

public class ProductSupplyDTO {
    private EmployeeDTO employee;
    private SalesAccountMachineDTO salesAccountMachine;
    private ProductPriceDTO product;

    private StringProperty salesAccountName;
    private StringProperty machineName;
    private StringProperty productName;
    private IntegerProperty productCount;
    private IntegerProperty plusValue;
    private IntegerProperty minusValue;
    private LocalDate localDate;

    public ProductSupplyDTO() {
        salesAccountName = new SimpleStringProperty(this, "salesAccountName");
        machineName = new SimpleStringProperty(this, "machineName");
        productName = new SimpleStringProperty(this, "productName");
        productCount = new SimpleIntegerProperty(this, "productCount");
        plusValue = new SimpleIntegerProperty(this, "plusValue");
        minusValue = new SimpleIntegerProperty(this, "minusValue");
    }

    public ProductSupplyDTO(EmployeeDTO employee, SalesAccountMachineDTO salesAccountMachine, ProductPriceDTO product,
                            int productCount, int plusValue, int minusValue, LocalDate localDate) {
        this();
        this.employee = employee;
        this.salesAccountMachine = salesAccountMachine;
        this.product = product;
        setSalesAccountName(salesAccountMachine.getSalesAccount().getName());
        setMachineName(salesAccountMachine.getMachine().getName());
        setProductName(product.getName());
        setProductCount(productCount);
        setPlusValue(plusValue);
        setMinusValue(minusValue);
        this.localDate = localDate;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public SalesAccountMachineDTO getSalesAccountMachine() {
        return salesAccountMachine;
    }

    public void setSalesAccountMachine(SalesAccountMachineDTO salesAccountMachine) {
        this.salesAccountMachine = salesAccountMachine;
        setSalesAccountName(salesAccountMachine.getSalesAccount().getName());
        setMachineName(salesAccountMachine.getMachine().getName());
    }

    public ProductPriceDTO getProduct() {
        return product;
    }

    public void setProduct(ProductPriceDTO product) {
        this.product = product;
        setProductName(product.getName());
    }

    public String getSalesAccountName() {
        return salesAccountName.get();
    }

    public void setSalesAccountName(String salesAccountName) {
        this.salesAccountName.set(salesAccountName);
    }

    public String getMachineName() {
        return machineName.get();
    }

    public void setMachineName(String machineName) {
        this.machineName.set(machineName);
    }


    public String getProductName() {
        return productName.get();
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public int getProductCount() {
        return productCount.get();
    }

    public void setProductCount(int productCount) {
        this.productCount.set(productCount);
    }

    public int getPlusValue() {
        return plusValue.get();
    }

    public void setPlusValue(int plusValue) {
        this.plusValue.set(plusValue);
    }

    public int getMinusValue() {
        return minusValue.get();
    }

    public void setMinusValue(int minusValue) {
        this.minusValue.set(minusValue);
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}