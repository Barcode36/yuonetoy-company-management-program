package com.yuonetoy.DTO;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountPreviewDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import com.yuonetoy.Entitiy.Employee;
import com.yuonetoy.Entitiy.Product;
import javafx.beans.property.*;


import java.time.LocalDate;

public class SalesAccountMachineDetailDTO {
    private Long id;
    private SalesAccountPreviewDTO salesAccount;
    private ObjectProperty<MachineDTO> machine;
    private IntegerProperty machineCount;
    private DoubleProperty fees;
    private IntegerProperty initialQuantity;
    private IntegerProperty plusValue;
    private IntegerProperty minusValue;
    private EmployeeDTO employee;
    private ProductPriceDTO product;
    private Integer productCount;
    private LocalDate date;

    public SalesAccountMachineDetailDTO(){
        machine = new SimpleObjectProperty<MachineDTO>(this, "machine");
        machineCount = new SimpleIntegerProperty(this, "machineCount");
        fees = new SimpleDoubleProperty(this, "fees");
        initialQuantity = new SimpleIntegerProperty(this, "initialQuantity");
        plusValue = new SimpleIntegerProperty(this, "plusValue");
        minusValue = new SimpleIntegerProperty(this, "minusValue");
    }

    public SalesAccountMachineDetailDTO(SalesAccountMachineDetailDTO salesAccountMachineDetailDTO) {
        this();
        this.id = salesAccountMachineDetailDTO.getId();
        this.salesAccount = salesAccountMachineDetailDTO.getSalesAccount();
        setMachine(salesAccountMachineDetailDTO.getMachine());
        setMachineCount(salesAccountMachineDetailDTO.getMachineCount());
        setFees(salesAccountMachineDetailDTO.getFees());
        setInitialQuantity(salesAccountMachineDetailDTO.getInitialQuantity());
        setPlusValue(salesAccountMachineDetailDTO.getPlusValue());
        setMinusValue(salesAccountMachineDetailDTO.getMinusValue());
        this.date = salesAccountMachineDetailDTO.getDate();
        setEmployee(salesAccountMachineDetailDTO.getEmployee());
        setProduct(salesAccountMachineDetailDTO.getProduct());
    }

    public SalesAccountMachineDetailDTO(Long id, SalesAccountPreviewDTO salesAccountPreviewDTO, MachineDTO machineDTO, int machineCount,
                                        double fees, int initialQuantity, int plusValue, int minusValue, LocalDate date,
                                        ProductPriceDTO product, EmployeeDTO employee) {
        this();
        this.id = id;
        this.salesAccount = salesAccountPreviewDTO;
        setMachine(machineDTO);
        setMachineCount(machineCount);
        setFees(fees);
        setInitialQuantity(initialQuantity);
        setPlusValue(plusValue);
        setMinusValue(minusValue);
        this.date = date;
        this.product = product;
        this.employee = employee;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public void setEmployee(Employee employee) {
        if (employee == null)
            return;

        this.employee = new EmployeeDTO();
        this.employee.setId(employee.getId());
        this.employee.setName(employee.getName());
    }

    public ProductPriceDTO getProduct() {
        return product;
    }

    public void setProduct(ProductPriceDTO product) {
        this.product = product;
    }

    public void setProduct(Product product) {
        if (product == null)
            return;

        this.product = new ProductPriceDTO();
        this.product.setId(product.getId());
        this.product.setName(product.getName());
        this.product.setPrice(product.getPrice());
        this.product.setCapsuleToy(product.getCapsuleToy());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SalesAccountPreviewDTO getSalesAccount() {
        return salesAccount;
    }

    public void setSalesAccount(SalesAccountPreviewDTO salesAccount) {
        this.salesAccount = salesAccount;
    }

    public MachineDTO getMachine() {
        return machine.get();
    }

    public void setMachine(MachineDTO machine) {
        this.machine.set(machine);
    }

    public int getMachineCount() {
        return machineCount.get();
    }

    public void setMachineCount(int machineCount) {
        this.machineCount.set(machineCount);
    }

    public double getFees() {
        return fees.get();
    }

    public void setFees(double fees) {
        this.fees.set(fees);
    }

    public int getInitialQuantity() {
        return initialQuantity.get();
    }

    public void setInitialQuantity(int initialQuantity) {
        this.initialQuantity.set(initialQuantity);
    }

    public int getPlusValue() {
        return plusValue.get();
    }

    public void setPlusValue(int plusValue) {
        this.plusValue.set(plusValue);
    }

    public int getMinusValue() {
        return minusValue.get();
    }

    public void setMinusValue(int minusValue) {
        this.minusValue.set(minusValue);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    @Override
    public String toString() {
        return machine.get().getName();
    }
}
