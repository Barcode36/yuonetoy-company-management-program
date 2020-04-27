package com.yuonetoy.DTO;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import com.yuonetoy.Entitiy.BusinessJournalHistory;
import com.yuonetoy.Entitiy.BusinessJournalHistoryList;
import com.yuonetoy.Entitiy.Product;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@NoArgsConstructor
public class BusinessJournalHistoryDTO {
    private String machineName;
    private String productName;
    private String EmployeeName;

    private String type;

    private SalesAccountDTO salesAccount;
    private ProductPriceDTO product;
    private SalesAccountMachineDetailDTO salesAccountMachine;
    private long id;
    private Integer machineCount;
    private Integer initialQuantity;
    private Double fees;
    private Integer supplyCount;
    private Integer plusValue;
    private Integer minusValue;
    private Long salesValue;
    private Long refundValue;
    private String secondType;
    private LocalDate date;

    public BusinessJournalHistoryDTO(long id, String secondType, String machineName, String productName,
                                     int machineCount, int initialQuantity, double fees,
                                     int supplyCount, int plusValue, int minusValue,
                                     long salesValue, long refundValue){

        setId(id);
        setSecondType(secondType);
        this.machineName = machineName;
        this.productName = productName;
        setMachineCount(machineCount);
        setInitialQuantity(initialQuantity);
        setFees(fees);
        setSupplyCount(supplyCount);
        setPlusValue(plusValue);
        setMinusValue(minusValue);
        setSalesValue(salesValue);
        setRefundValue(refundValue);
    }

    public String getSecondType() {
        return secondType;
    }

    public void setSecondType(String secondType) {
        this.secondType = secondType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMachineName() {
        return machineName;
    }

    public String getProductName() {
        return productName;
    }

    public SalesAccountMachineDetailDTO getSalesAccountMachine() {
        return salesAccountMachine;
    }

    public void setSalesAccountMachine(SalesAccountMachineDetailDTO salesAccountMachine) {
        this.salesAccountMachine = salesAccountMachine;
    }

    public Integer getMachineCount() {
        return machineCount;
    }

    public void setMachineCount(Integer machineCount) {
        this.machineCount = machineCount;
    }

    public Integer getInitialQuantity() {
        return initialQuantity;
    }

    public void setInitialQuantity(Integer initialQuantity) {
        this.initialQuantity = initialQuantity;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }

    public ProductPriceDTO getProduct() {
        return product;
    }

    public void setProduct(ProductPriceDTO product) {
        this.product = product;
        if (product != null)
            productName = product.getName();
    }

    public Integer getSupplyCount() {
        return supplyCount;
    }

    public void setSupplyCount(Integer supplyCount) {
        this.supplyCount = supplyCount;
    }

    public Integer getPlusValue() {
        return plusValue;
    }

    public void setPlusValue(Integer plusValue) {
        this.plusValue = plusValue;
    }

    public Integer getMinusValue() {
        return minusValue;
    }

    public void setMinusValue(Integer minusValue) {
        this.minusValue = minusValue;
    }

    public Long getSalesValue() {
        return salesValue;
    }

    public void setSalesValue(Long salesValue) {
        this.salesValue = salesValue;
    }

    public Long getRefundValue() {
        return refundValue;
    }

    public void setRefundValue(Long refundValue) {
        this.refundValue = refundValue;
    }

    public SalesAccountDTO getSalesAccount() {
        return salesAccount;
    }

    public void setSalesAccount(SalesAccountDTO salesAccount) {
        this.salesAccount = salesAccount;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setNames() {
        machineName = salesAccountMachine.getMachine().getName();

        if (salesAccountMachine.getMachine().getIsProductMachine())
            productName = product.getName();
    }
}
