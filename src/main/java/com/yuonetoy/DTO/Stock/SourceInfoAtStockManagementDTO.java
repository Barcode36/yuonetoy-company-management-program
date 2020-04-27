package com.yuonetoy.DTO.Stock;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class SourceInfoAtStockManagementDTO {
    private Long id;
    private StringProperty name;
    private StringProperty address;

    public SourceInfoAtStockManagementDTO() {
        name = new SimpleStringProperty(this, "name");
        address = new SimpleStringProperty(this, "address");
}

    public SourceInfoAtStockManagementDTO(Long id, String name, String address) {
        this();
        this.id = id;
        setName(name);
        setAddress(address);
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

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }
}
