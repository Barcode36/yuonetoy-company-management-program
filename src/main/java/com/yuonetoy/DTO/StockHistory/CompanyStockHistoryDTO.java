package com.yuonetoy.DTO.StockHistory;

import java.time.LocalDate;

public class CompanyStockHistoryDTO {

    private String type;
    private String company;
    private String targetName;
    private String stockName;
    private Integer sendCount;
    private Long stockCount;
    private LocalDate date;

    private Long id;



    public String getTargetName() {
        return targetName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    public Long getStockCount() {
        return stockCount;
    }

    public void setStockCount(Long stockCount) {
        this.stockCount = stockCount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }
}
