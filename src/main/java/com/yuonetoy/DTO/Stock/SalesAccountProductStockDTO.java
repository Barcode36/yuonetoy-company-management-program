package com.yuonetoy.DTO.Stock;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import com.yuonetoy.DTO.SalesAccountMachineDTO;
import javafx.beans.property.*;


public class SalesAccountProductStockDTO {
    private Long id;
    private StringProperty salesAccountName;
    private SalesAccountDTO salesAccount;
    private ObjectProperty<SalesAccountMachineDTO> salesAccountMachine;
    private ObjectProperty<ProductPriceDTO> product;
    private DoubleProperty count;

    public SalesAccountProductStockDTO() {
        salesAccountName = new SimpleStringProperty(this, "salesAccountName");
        salesAccountMachine = new SimpleObjectProperty<>(this, "salesAccountMachine");
        product = new SimpleObjectProperty<>(this, "product");
        count = new SimpleDoubleProperty(this, "count");
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

    public void setSalesAccountName(String salesAccountName) {
        this.salesAccountName.set(salesAccountName);
    }

    public SalesAccountDTO getSalesAccount() {
        return salesAccount;
    }

    public void setSalesAccount(SalesAccountDTO salesAccount) {
        this.salesAccount = salesAccount;
        setSalesAccountName(salesAccount.getName());
    }

    public SalesAccountMachineDTO getSalesAccountMachine() {
        return salesAccountMachine.get();
    }

    public void setSalesAccountMachine(SalesAccountMachineDTO salesAccountMachine) {
        this.salesAccountMachine.set(salesAccountMachine);
    }

    public ProductPriceDTO getProduct() {
        return product.get();
    }

    public void setProduct(ProductPriceDTO product) {
        this.product.set(product);
    }

    public double getCount() {
        return count.get();
    }

    public DoubleProperty countProperty() {
        return count;
    }

    public void setCount(double count) {
        this.count.set(count);
    }

    @Override
    public String toString() {
        return product.getName() + " (남은 재고 : " + count + "개)";
    }
}
