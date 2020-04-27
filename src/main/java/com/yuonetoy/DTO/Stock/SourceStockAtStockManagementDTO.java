package com.yuonetoy.DTO.Stock;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class SourceStockAtStockManagementDTO {
    private Long id;
    private Long sourceId;
    private Long stockId;
    private StringProperty stockType;
    private StringProperty sourceName;
    private StringProperty stockName;
    private IntegerProperty stockCount;
    private IntegerProperty salesAccountProductStockCount;
    private Integer previousSalesAccountProductStockCount;
    private Integer previousStockCount;
    private Boolean isChanged;

    public SourceStockAtStockManagementDTO() {
        sourceName = new SimpleStringProperty(this, "sourceName");
        stockType = new SimpleStringProperty(this, "stockType");
        stockName = new SimpleStringProperty(this, "product");
        stockCount = new SimpleIntegerProperty(this, "stockCount");
        salesAccountProductStockCount = new SimpleIntegerProperty(this, "salesAccountProductStockCount");
    }

    public int getSalesAccountProductStockCount() {
        return salesAccountProductStockCount.get();
    }

    public IntegerProperty salesAccountProductStockCountProperty() {
        return salesAccountProductStockCount;
    }

    public void setSalesAccountProductStockCount(int salesAccountProductStockCount) {
        this.salesAccountProductStockCount.set(salesAccountProductStockCount);
    }

    public Integer getPreviousSalesAccountProductStockCount() {
        return previousSalesAccountProductStockCount;
    }

    public void setPreviousSalesAccountProductStockCount(Integer previousSalesAccountProductStockCount) {
        this.previousSalesAccountProductStockCount = previousSalesAccountProductStockCount;
    }

    public String getSourceName() {
        return sourceName.get();
    }

    public StringProperty sourceNameProperty() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName.set(sourceName);
    }

    public Integer getPreviousStockCount() {
        return previousStockCount;
    }

    public void setPreviousStockCount(Integer previousStockCount) {
        this.previousStockCount = previousStockCount;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Boolean getChanged() {
        return isChanged;
    }

    public void setChanged(Boolean changed) {
        isChanged = changed;
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

    public int getStockCount() {
        return stockCount.get();
    }

    public IntegerProperty stockCountProperty() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount.set(stockCount);
    }
}
