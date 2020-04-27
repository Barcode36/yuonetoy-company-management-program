package com.yuonetoy.DTO.StockHistory;

import com.yuonetoy.DTO.BusinessJournalHistoryDTO;
import com.yuonetoy.DTO.Stock.EmployeeMachineStockDTO;
import com.yuonetoy.DTO.Stock.EmployeeProductStockDTO;
import com.yuonetoy.Entitiy.BusinessJournalHistory;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeMachineStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeProductStock;
import com.yuonetoy.Entitiy.StockHistory.SendStockHistory;
import com.yuonetoy.Tool.LocalDatePersistenceConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

public class EmployeeStockHistoryDTO {

    private String type;
    private String employee;
    private String targetName;
    private String stockName;
    private Integer sendCount;
    private Long stockCount;
    private LocalDate date;

    private Long id;

    public EmployeeStockHistoryDTO() {
    }

    public String getTargetName() {
        return targetName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
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
