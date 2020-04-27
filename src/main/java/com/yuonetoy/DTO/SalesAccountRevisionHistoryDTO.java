package com.yuonetoy.DTO;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDetailDTO;
import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.Machine;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class SalesAccountRevisionHistoryDTO implements Serializable {
    private Long id;
    private ObjectProperty<LocalDate> date;
    private StringProperty type;
    private StringProperty contents;

    public SalesAccountRevisionHistoryDTO() {
        type = new SimpleStringProperty(this,"type");
        contents = new SimpleStringProperty(this,"contents");
        date = new SimpleObjectProperty<LocalDate>(this,"date");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getContents() {
        return contents.get();
    }

    public void setContents(String contents) {
        this.contents.set(contents);
    }
}