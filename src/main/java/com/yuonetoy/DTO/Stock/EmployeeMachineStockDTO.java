package com.yuonetoy.DTO.Stock;

import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.MachineDTO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;


public class EmployeeMachineStockDTO {
    private Long id;
    private ObjectProperty<MachineDTO> machine;
    private IntegerProperty count;
    private String stockName;

    public EmployeeMachineStockDTO() {
        machine = new SimpleObjectProperty<>(this, "machine");
        count = new SimpleIntegerProperty(this, "count");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MachineDTO getMachine() {
        return machine.get();
    }

    public void setMachine(MachineDTO machine) {
        this.machine.set(machine);
    }

    public int getCount() {
        return count.get();
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    public void setStockName() {
        stockName =  getMachine().getName() + " (남은 재고 : " + count.get() + "개)";
    }

    @Override
    public String toString() {
        return stockName;
    }
}
