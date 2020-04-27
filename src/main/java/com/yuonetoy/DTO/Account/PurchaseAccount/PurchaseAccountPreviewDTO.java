package com.yuonetoy.DTO.Account.PurchaseAccount;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

public class PurchaseAccountPreviewDTO {
    private Long id;
    private StringProperty name;
    private StringProperty address;
    private StringProperty ph;
    private ObjectProperty<LocalDate> entryDate;

    public PurchaseAccountPreviewDTO() {
        name = new SimpleStringProperty(this,"name");
        address = new SimpleStringProperty(this,"address");
        ph = new SimpleStringProperty(this,"ph");
        entryDate = new SimpleObjectProperty<LocalDate>(this, "entryDate");
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

    public String getPh() {
        return ph.get();
    }

    public void setPh(String ph) {
        this.ph.set(ph);
    }

    public LocalDate getEntryDate() {
        return entryDate.get();
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate.set(entryDate);
    }
}
