package com.yuonetoy.DTO.StockHistory;

import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountDTO;
import com.yuonetoy.DTO.Company.CompanyDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.Product.ProductDTO;
import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


public class SendStockHistoryDTO {
    private long id;

    private StringProperty type;
    private StringProperty sourceName;
    private StringProperty targetName;
    private StringProperty stockName;
    private LongProperty sendCount;
    private LongProperty stockCount;
    private boolean isProduct;
    private ObjectProperty<LocalDate> date;

    public boolean isProduct() {
        return isProduct;
    }

    public void setIsProduct(boolean product) {
        isProduct = product;
    }

    public SendStockHistoryDTO() {
        type = new SimpleStringProperty(this, "type");
        sourceName = new SimpleStringProperty(this, "sourceName");
        targetName = new SimpleStringProperty(this, "targetName");
        stockName = new SimpleStringProperty(this, "stockName");
        sendCount = new SimpleLongProperty(this, "sendCount");
        stockCount = new SimpleLongProperty(this, "stockCount");
        date = new SimpleObjectProperty<LocalDate>(this, "date");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStockCount() {
        return stockCount.get();
    }

    public LongProperty stockCountProperty() {
        return stockCount;
    }

    public void setStockCount(long stockCount) {
        this.stockCount.set(stockCount);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
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

    public String getTargetName() {
        return targetName.get();
    }

    public StringProperty targetNameProperty() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName.set(targetName);
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

    public long getSendCount() {
        return sendCount.get();
    }

    public LongProperty sendCountProperty() {
        return sendCount;
    }

    public void setSendCount(long sendCount) {
        this.sendCount.set(sendCount);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }
}