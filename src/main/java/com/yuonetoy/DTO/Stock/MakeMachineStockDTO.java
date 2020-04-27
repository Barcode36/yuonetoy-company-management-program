package com.yuonetoy.DTO.Stock;

import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountDTO;
import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import javafx.beans.property.*;


public class MakeMachineStockDTO implements StockDTO {
    private MachineDTO machine;
    private StringProperty stockName;

    public MakeMachineStockDTO() {
        stockName = new SimpleStringProperty(this, "stockName");
    }

    public MakeMachineStockDTO(MachineDTO machineDTO) {
        this();
        setMachine(machineDTO);
        setStockStatus();
    }
    public String getStockName() {
        return stockName.get();
    }

    public MachineDTO getMachine() {
        return machine;
    }

    public void setMachine(MachineDTO machine) {
        this.machine = machine;
    }

    public void setStockStatus() {
        stockName.set(machine.getName());
    }
}
