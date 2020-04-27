package com.yuonetoy.DTO;

import com.yuonetoy.Entitiy.Machine;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class MachineDTO {
    private Long id;
    private String name;
    private Boolean isCoinChanger;
    private Boolean isProductMachine;

    private StringProperty machineName;
    private StringProperty machineType;
    private StringProperty product;

    public void initStatus(){
        setMachineName(name);

        if (isCoinChanger) {
            setMachineType("동전 교환기");
        } else {
            if (isProductMachine)
                setMachineType("상품 교환기");
            else
                setMachineType("무상품 교환기");
        }
    }

    public MachineDTO() {
        machineName = new SimpleStringProperty(this,"machineName");
        machineType = new SimpleStringProperty(this,"machineType");
        product = new SimpleStringProperty(this,"product");
    }

    public MachineDTO(Long id, String name, Boolean isCoinChanger, Boolean isProductMachine, String product) {
        this();
        this.id = id;
        this.isCoinChanger = isCoinChanger;
        this.isProductMachine = isProductMachine;
        this.name = name;

        setMachineName(name);

        if (isCoinChanger) {
            setMachineType("동전 교환기");
        } else {
            if (isProductMachine)
                setMachineType("상품 교환기");
            else
                setMachineType("무상품 교환기");
        }

        setProduct(product);
    }

    public String getMachineName() {
        return machineName.get();
    }

    public StringProperty machineNameProperty() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName.set(machineName);
    }

    public String getMachineType() {
        return machineType.get();
    }

    public StringProperty machineTypeProperty() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType.set(machineType);
    }

    public String getProduct() {
        return product.get();
    }

    public StringProperty productProperty() {
        return product;
    }

    public void setProduct(String product) {
        this.product.set(product);
    }

    @Override
    public String toString() {
        return name;
    }
}
