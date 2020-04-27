package com.yuonetoy.DTO.StockHistory;

import com.yuonetoy.DTO.Product.ProductPriceDTO;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
public class SalesAccountStockHistoryDTO {
    private String type;
    private String employeeName;
    private String machineName;
    private String productName;
    private Integer supplyCount;
    private Long salesValue;
    private Long stockValue;
    private LocalDate date;

    public SalesAccountStockHistoryDTO(String type, String employeeName, String machineName, String productName, Integer supplyCount, Long salesValue, LocalDate date) {
        this.type = type;
        this.employeeName = employeeName;
        this.machineName = machineName;
        this.productName = productName;
        this.supplyCount = supplyCount;
        this.salesValue = salesValue;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getSupplyCount() {
        return supplyCount;
    }

    public void setSupplyCount(Integer supplyCount) {
        this.supplyCount = supplyCount;
    }

    public Long getSalesValue() {
        return salesValue;
    }

    public void setSalesValue(Long salesValue) {
        this.salesValue = salesValue;
    }

    public Long getStockValue() {
        return stockValue;
    }

    public void setStockValue(Long stockValue) {
        this.stockValue = stockValue;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
