package com.yuonetoy.DTO.Company;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CompanyDetailDTO {
    private Long id;
    private StringProperty name;
    private StringProperty address;

    public CompanyDetailDTO() {
        name = new SimpleStringProperty("name");
        address = new SimpleStringProperty("address");
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
}
