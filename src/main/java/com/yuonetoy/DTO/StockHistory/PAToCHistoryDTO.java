package com.yuonetoy.DTO.StockHistory;

import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountDTO;
import com.yuonetoy.DTO.Company.CompanyDTO;
import com.yuonetoy.DTO.Product.ProductDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

public class PAToCHistoryDTO implements SendStockHistory {
    private Long id;
    private ObjectProperty<PurchaseAccountDTO> purchaseAccount;
    private ObjectProperty<CompanyDTO> targetCompany;
    private ObjectProperty<ProductPriceDTO> product;
    private IntegerProperty sendedCount;
    private ObjectProperty<LocalDate> date;

    public PAToCHistoryDTO() {
        purchaseAccount = new SimpleObjectProperty<PurchaseAccountDTO>(this, "purchaseAccount");
        targetCompany = new SimpleObjectProperty<CompanyDTO>(this, "targetCompany");
        product = new SimpleObjectProperty<ProductPriceDTO>(this, "product");
        sendedCount = new SimpleIntegerProperty(this, "sendedCount");
        date = new SimpleObjectProperty<LocalDate>(this, "date");
    }

    public PAToCHistoryDTO(Long id, PurchaseAccountDTO purchaseAccount, CompanyDTO targetCompany, ProductPriceDTO product, int sendedCount, LocalDate date) {
        this();
        this.id = id;
        setPurchaseAccount(purchaseAccount);
        setTargetCompany(targetCompany);
        setProduct(product);
        setSendedCount(sendedCount);
        setDate(date);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PurchaseAccountDTO getPurchaseAccount() {
        return purchaseAccount.get();
    }

    public void setPurchaseAccount(PurchaseAccountDTO purchaseAccount) {
        this.purchaseAccount.set(purchaseAccount);
    }

    public CompanyDTO getTargetCompany() {
        return targetCompany.get();
    }

    public void setTargetCompany(CompanyDTO targetCompany) {
        this.targetCompany.set(targetCompany);
    }

    public ProductPriceDTO getProduct() {
        return product.get();
    }

    public void setProduct(ProductPriceDTO productPriceDTO) {
        this.product.set(productPriceDTO);
    }

    public int getSendedCount() {
        return sendedCount.get();
    }

    public void setSendedCount(int sendedCount) {
        this.sendedCount.set(sendedCount);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }
}
