package com.yuonetoy.DTO.Product;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;

public class ProductPriceDTO {
    private Long id;
    private StringProperty name;
    private IntegerProperty price;
    private Boolean isCapsuleToy;

    public ProductPriceDTO() {
        name = new SimpleStringProperty(this, "name");
        price = new SimpleIntegerProperty(this, "price");
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

    public int getPrice() {
        return price.get();
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public Boolean getCapsuleToy() {
        return isCapsuleToy;
    }

    public void setCapsuleToy(Boolean capsuleToy) {
        isCapsuleToy = capsuleToy;
    }

    @Override
    public String toString() {
        return getName();
    }
}
