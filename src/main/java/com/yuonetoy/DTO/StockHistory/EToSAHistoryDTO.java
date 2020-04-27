package com.yuonetoy.DTO.StockHistory;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import com.yuonetoy.DTO.SalesAccountMachineDTO;
import javafx.beans.property.*;

import java.time.LocalDate;

public class EToSAHistoryDTO implements SendStockHistory {
    private Long id;
    private EmployeeDTO employee;
    private StringProperty typeName;
    private ObjectProperty<SalesAccountDTO> salesAccount;
    private ObjectProperty<ProductPriceDTO> product;
    private ObjectProperty<SalesAccountMachineDTO> salesAccountMachine;
    private IntegerProperty sendedCount;
    private ObjectProperty<LocalDate> date;

    public EToSAHistoryDTO() {
        typeName = new SimpleStringProperty(this, "typeName");
        salesAccount = new SimpleObjectProperty<SalesAccountDTO>(this, "salesAccount");
        product = new SimpleObjectProperty<ProductPriceDTO>(this, "product");
        sendedCount = new SimpleIntegerProperty(this, "sendedCount");
        salesAccountMachine = new SimpleObjectProperty<SalesAccountMachineDTO>(this, "machine");
        date = new SimpleObjectProperty<LocalDate>(this, "date");
    }

    public EToSAHistoryDTO(Long id, EmployeeDTO employeeDTO, ProductPriceDTO product, int sendedCount, SalesAccountMachineDTO machineDTO, LocalDate date) {
        this();
        this.id = id;
        setEmployee(employeeDTO);
        setProduct(product);
        setSendedCount(sendedCount);
        setSalesAccountMachine(machineDTO);
        setDate(date);

        SalesAccountDTO salesAccountDTO = new SalesAccountDTO();
        salesAccountDTO.setId(machineDTO.getSalesAccount().getId());
        salesAccountDTO.setName(machineDTO.getSalesAccount().getName());

        setSalesAccount(salesAccountDTO);
    }

    public SalesAccountMachineDTO getSalesAccountMachine() {
        return salesAccountMachine.get();
    }

    public void setSalesAccountMachine(SalesAccountMachineDTO salesAccountMachine) {
        this.salesAccountMachine.set(salesAccountMachine);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public String getTypeName() {
        return typeName.get();
    }

    public void setTypeName(String typeName) {
        this.typeName.set(typeName);
    }

    public SalesAccountDTO getSalesAccount() {
        return salesAccount.get();
    }

    public void setSalesAccount(SalesAccountDTO salesAccount) {
        this.salesAccount.set(salesAccount);
    }

    public ProductPriceDTO getProduct() {
        return product.get();
    }

    public void setProduct(ProductPriceDTO product) {
        this.product.set(product);
    }

    public int getSendedCount() {
        return sendedCount.get();
    }

    public void setSendedCount(int sendedCount) {
        this.sendedCount.set(sendedCount);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }
}
