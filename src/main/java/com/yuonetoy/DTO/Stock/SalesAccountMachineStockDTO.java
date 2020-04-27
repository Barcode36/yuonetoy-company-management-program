package com.yuonetoy.DTO.Stock;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.SalesAccountMachineDTO;
import javafx.beans.property.*;


public class SalesAccountMachineStockDTO {
    private Long id;
    private StringProperty salesAccountName;
    private SalesAccountDTO salesAccount;
    private ObjectProperty<SalesAccountMachineDTO> salesAccountMachine;
    private IntegerProperty count;

    public SalesAccountMachineStockDTO() {
        salesAccountName = new SimpleStringProperty(this, "salesAccountName");
        salesAccountMachine = new SimpleObjectProperty<>(this, "salesAccountMachine");
        count = new SimpleIntegerProperty(this, "count");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getSalesAccountName() {
        return salesAccountName.get();
    }

    public void setSalesAccountName(String salesAccountName) {
        this.salesAccountName.set(salesAccountName);
    }

    public SalesAccountDTO getSalesAccount() {
        return salesAccount;
    }

    public void setSalesAccount(SalesAccountDTO salesAccount) {
        this.salesAccount = salesAccount;
        setSalesAccountName(salesAccount.getName());
    }


    public SalesAccountMachineDTO getSalesAccountMachine() {
        return salesAccountMachine.get();
    }

    public void setSalesAccountMachine(SalesAccountMachineDTO salesAccountMachine) {
        this.salesAccountMachine.set(salesAccountMachine);
    }

    public int getCount() {
        return count.get();
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    @Override
    public String toString() {
        return salesAccountMachine.get().getMachine().getName() + " (남은 재고 : " + count + "개)";
    }
}
