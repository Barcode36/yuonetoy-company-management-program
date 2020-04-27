package com.yuonetoy.DTO.Stock;

import com.yuonetoy.DTO.Company.CompanyDTO;
import com.yuonetoy.DTO.MachineDTO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;


public class CompanyMachineStockDTO {
    private Long id;
    private ObjectProperty<CompanyDTO> company;
    private ObjectProperty<MachineDTO> machine;
    private IntegerProperty count;

    public CompanyMachineStockDTO() {
        company = new SimpleObjectProperty<>(this, "company");
        machine = new SimpleObjectProperty<>(this, "machine");
        count = new SimpleIntegerProperty(this, "count");
    }

    public CompanyMachineStockDTO(Long id, CompanyDTO company, MachineDTO machine, int count) {
        this();
        this.id = id;
        setCompany(company);
        setMachine(machine);
        setCount(count);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyDTO getCompany() {
        return company.get();
    }

    public void setCompany(CompanyDTO company) {
        this.company.set(company);
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

    @Override
    public String toString() {
        return machine.getName() + " (남은 재고 : " + count + "개)";
    }
}
