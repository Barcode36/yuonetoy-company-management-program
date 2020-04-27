package com.yuonetoy.DTO.Stock;

import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountDTO;
import com.yuonetoy.DTO.Company.CompanyDTO;
import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import javafx.beans.property.*;


public class PurchaseAccountStockDTO implements StockDTO {
    private ObjectProperty<PurchaseAccountDTO> purchaseAccount;
    private ProductPriceDTO product;
    private StringProperty stockName;
    private IntegerProperty price;

    public PurchaseAccountStockDTO() {
        purchaseAccount = new SimpleObjectProperty<>(this, "purchaseAccount");
        stockName = new SimpleStringProperty(this, "stockName");
        price = new SimpleIntegerProperty(this, "price");
    }

    public PurchaseAccountStockDTO(PurchaseAccountDTO purchaseAccountDTO, ProductPriceDTO product) {
        this();
        setPurchaseAccount(purchaseAccountDTO);
        setProduct(product);
        setStockStatus();
    }

    public PurchaseAccountDTO getPurchaseAccount() {
        return purchaseAccount.get();
    }

    public void setPurchaseAccount(PurchaseAccountDTO purchaseAccount) {
        this.purchaseAccount.set(purchaseAccount);
    }

    public ProductPriceDTO getProduct() {
        return product;
    }

    public int getPrice() {
        return price.get();
    }

    public void setProduct(ProductPriceDTO product) {
        this.product = product;
    }

    public String getStockName() {
        return stockName.get();
    }

    public void setStockStatus() {
        stockName.set(product.getName());
        price.set(product.getPrice());
    }
}
