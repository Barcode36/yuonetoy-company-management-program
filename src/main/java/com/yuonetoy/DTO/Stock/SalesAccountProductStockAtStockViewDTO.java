package com.yuonetoy.DTO.Stock;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import com.yuonetoy.DTO.SalesAccountMachineDTO;
import javafx.beans.property.*;


public class SalesAccountProductStockAtStockViewDTO {
    private Long id;
    private StringProperty salesAccountName;
    private StringProperty salesAccountMachine;
    private StringProperty product;
    private IntegerProperty count;

    public SalesAccountProductStockAtStockViewDTO() {
        salesAccountName = new SimpleStringProperty(this, "salesAccountName");
        salesAccountMachine = new SimpleStringProperty(this, "salesAccountMachine");
        product = new SimpleStringProperty(this, "product");
        count = new SimpleIntegerProperty(this, "count");
    }

    public SalesAccountProductStockAtStockViewDTO(Long id, String salesAccountName, String salesAccountMachine, String product, int count) {
        this();
        setId(id);
        setSalesAccountName(salesAccountName);
        setSalesAccountMachine(salesAccountMachine);
        setProduct(product);
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

    public String getProduct() {
        return product.get();
    }

    public StringProperty productProperty() {
        return product;
    }

    public void setProduct(String product) {
        this.product.set(product);
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
