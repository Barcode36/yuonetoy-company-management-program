package com.yuonetoy.DTO.Account.PurchaseAccount;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

public class PurchaseAccountDTO {
    private Long id;
    private StringProperty name;
    private StringProperty address;

    public PurchaseAccountDTO() {
        name = new SimpleStringProperty(this, "name");
        address = new SimpleStringProperty(this, "address");
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

    @Override
    public String toString() {
        return getName();
    }
}
