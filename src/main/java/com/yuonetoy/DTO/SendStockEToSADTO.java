package com.yuonetoy.DTO;

import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import javafx.beans.property.*;

import java.time.LocalDate;

public class SendStockEToSADTO {
    public SendStockEToSADTO (EmployeeDTO employee, SalesAccountMachineDetailDTO salesAccountMachine, boolean isProduct,
                              ProductPriceDTO product, MachineDTO machine, int stockSendCount, LocalDate date) {
        this();
        setEmployee(employee);
        setSalesAccountMachine(salesAccountMachine);
        this.isProduct = isProduct;
        setProduct(product);
        setMachine(machine);

        if (isProduct && product != null) {
            setStockName(product.getName());
            setStockPrice(product.getPrice());
        }else if (!isProduct && machine != null){
            setStockName(machine.getName());
            setStockPrice(0);
        }

        setSendedCount(stockSendCount);
        setDate(date);
    }

    public SendStockEToSADTO() {
        employee = new SimpleObjectProperty<EmployeeDTO>(this,"employee");
        salesAccountMachine = new SimpleObjectProperty<SalesAccountMachineDetailDTO>(this,"salesAccountMachine");
        stockName = new SimpleStringProperty(this,"stockName");
        stockPrice = new SimpleIntegerProperty(this,"stockPrice");
        sendedCount = new SimpleIntegerProperty(this,"sendedCount");
        date = new SimpleObjectProperty<LocalDate>(this,"date");
    }

    private ObjectProperty<EmployeeDTO> employee;
    private ObjectProperty<SalesAccountMachineDetailDTO> salesAccountMachine;
    private boolean isProduct;
    private ProductPriceDTO product;
    private MachineDTO machine;
    private StringProperty stockName;
    private IntegerProperty stockPrice;
    private IntegerProperty sendedCount;
    private ObjectProperty<LocalDate> date;

    public boolean isProduct() {
        return isProduct;
    }

    public void setProduct(boolean product) {
        isProduct = product;
    }

    public MachineDTO getMachine() {
        return machine;
    }

    public void setMachine(MachineDTO machine) {
        this.machine = machine;
    }

    public EmployeeDTO getEmployee() {
        return employee.get();
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee.set(employee);
    }

    public SalesAccountMachineDetailDTO getSalesAccountMachine() {
        return salesAccountMachine.get();
    }

    public void setSalesAccountMachine(SalesAccountMachineDetailDTO salesAccountMachine) {
        this.salesAccountMachine.set(salesAccountMachine);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public String getStockName() {
        return stockName.get();
    }

    public void setStockName(String stockName) {
        this.stockName.set(stockName);
    }

    public int getStockPrice() {
        return stockPrice.get();
    }

    public void setStockPrice(int stockPrice) {
        this.stockPrice.set(stockPrice);
    }

    public int getSendedCount() {
        return sendedCount.get();
    }

    public void setSendedCount(int sendedCount) {
        this.sendedCount.set(sendedCount);
    }

    public ProductPriceDTO getProduct() {
        return product;
    }

    public void setProduct(ProductPriceDTO product) {
        this.product = product;
    }
}