package com.yuonetoy.DTO.Stock;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class StockAtStockManagementDTO {
    private Long id;
    private StringProperty stockType;
    private StringProperty stockName;

    public StockAtStockManagementDTO() {
        stockType = new SimpleStringProperty(this, "stockType");
        stockName = new SimpleStringProperty(this, "stockName");
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockType() {
        return stockType.get();
    }

    public StringProperty stockTypeProperty() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType.set(stockType);
    }

    public String getStockName() {
        return stockName.get();
    }

    public StringProperty stockNameProperty() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName.set(stockName);
    }
}
