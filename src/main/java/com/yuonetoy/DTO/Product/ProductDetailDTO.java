package com.yuonetoy.DTO.Product;

import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountDTO;
import com.yuonetoy.Entitiy.Account.PurchaseAccount;
import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ProductDetailDTO {
    private Long id;
    private Boolean isCapsuleToy;
    private StringProperty productType;
    private StringProperty name;
    private IntegerProperty price;
    private ObjectProperty<PurchaseAccountDTO> purchaseAccount;

    public ProductDetailDTO() {
        name = new SimpleStringProperty(this, "name");
        productType = new SimpleStringProperty(this, "productType");
        price = new SimpleIntegerProperty(this, "price");
        purchaseAccount = new SimpleObjectProperty<PurchaseAccountDTO>(this, "purchaseAccount");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCapsuleToy() {
        return isCapsuleToy;
    }

    public void setCapsuleToy(Boolean capsuleToy) {
        isCapsuleToy = capsuleToy;
        setProductType(capsuleToy == true ? "캡슐 토이" : "일반 상품");
    }

    public String getProductType() {
        return productType.get();
    }

    public StringProperty productTypeProperty() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType.set(productType);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getPrice() {
        return price.get();
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public PurchaseAccountDTO getPurchaseAccount() {
        return purchaseAccount.get();
    }

    public void setPurchaseAccount(PurchaseAccountDTO purchaseAccount) {
        this.purchaseAccount.set(purchaseAccount);
    }
}
