package com.yuonetoy.DTO;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountPreviewDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import com.yuonetoy.Entitiy.Employee;
import com.yuonetoy.Entitiy.Product;
import javafx.beans.property.*;

import java.time.LocalDate;

public class SalesAccountMachineAtBusinessJournalDTO {
    private StringProperty machine;
    private IntegerProperty machineCount;
    private IntegerProperty initialQuantity;
    private DoubleProperty fees;
    private IntegerProperty plusValue;
    private IntegerProperty minusValue;
    private StringProperty product;
    private IntegerProperty productCount;

    public SalesAccountMachineAtBusinessJournalDTO(){
        machine = new SimpleStringProperty(this, "machine");
        machineCount = new SimpleIntegerProperty(this, "machineCount");
        fees = new SimpleDoubleProperty(this, "fees");
        initialQuantity = new SimpleIntegerProperty(this, "initialQuantity");
        plusValue = new SimpleIntegerProperty(this, "plusValue");
        minusValue = new SimpleIntegerProperty(this, "minusValue");
        product = new SimpleStringProperty(this, "product");
        productCount = new SimpleIntegerProperty(this, "productCount");
    }

    public SalesAccountMachineAtBusinessJournalDTO(SalesAccountMachineDetailDTO salesAccountMachineDetailDTO) {
        this();
        setMachine(salesAccountMachineDetailDTO.getMachine().getName());
        setMachineCount(salesAccountMachineDetailDTO.getMachineCount());
        setFees(salesAccountMachineDetailDTO.getFees());
        setInitialQuantity(salesAccountMachineDetailDTO.getInitialQuantity());
        setPlusValue(salesAccountMachineDetailDTO.getPlusValue());
        setMinusValue(salesAccountMachineDetailDTO.getMinusValue());
        if (salesAccountMachineDetailDTO.getMachine().getIsProductMachine() || salesAccountMachineDetailDTO.getMachine().getIsCoinChanger()) {
            setProduct(salesAccountMachineDetailDTO.getProduct().getName());
            setProductCount(salesAccountMachineDetailDTO.getProductCount());
        }
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

    public int getMachineCount() {
        return machineCount.get();
    }

    public IntegerProperty machineCountProperty() {
        return machineCount;
    }

    public void setMachineCount(int machineCount) {
        this.machineCount.set(machineCount);
    }

    public int getInitialQuantity() {
        return initialQuantity.get();
    }

    public IntegerProperty initialQuantityProperty() {
        return initialQuantity;
    }

    public void setInitialQuantity(int initialQuantity) {
        this.initialQuantity.set(initialQuantity);
    }

    public double getFees() {
        return fees.get();
    }

    public DoubleProperty feesProperty() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees.set(fees);
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

    public String getProduct() {
        return product.get();
    }

    public StringProperty productProperty() {
        return product;
    }

    public void setProduct(String product) {
        this.product.set(product);
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
}
