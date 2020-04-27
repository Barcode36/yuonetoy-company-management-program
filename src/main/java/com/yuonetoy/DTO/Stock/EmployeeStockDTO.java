package com.yuonetoy.DTO.Stock;

import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import javafx.beans.property.*;


public class EmployeeStockDTO implements StockDTO{
    private Long id;

    private ObjectProperty<EmployeeDTO> employee;
    private boolean isProduct;
    private ProductPriceDTO product;
    private MachineDTO machine;

    private StringProperty stockType;
    private StringProperty stockName;
    private IntegerProperty price;
    private IntegerProperty count;

    public EmployeeStockDTO() {
        employee = new SimpleObjectProperty<>(this, "employee");
        stockType = new SimpleStringProperty(this, "stockType");
        stockName = new SimpleStringProperty(this, "stockName");
        price = new SimpleIntegerProperty(this, "price");
        count = new SimpleIntegerProperty(this, "count");
    }

    public EmployeeStockDTO(EmployeeDTO employee, boolean isProduct, ProductPriceDTO product, MachineDTO machine, int count) {
        this();

        setEmployee(employee);
        setProduct(isProduct);
        if (isProduct)
            setProduct(product);
        else
            setMachine(machine);
        setCount(count);
    }

    public String getStockType() {
        return stockType.get();
    }

    public void setStockType(String stockType) {
        this.stockType.set(stockType);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeDTO getEmployee() {
        return employee.get();
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee.set(employee);
    }

    public boolean isProduct() {
        return isProduct;
    }

    public void setProduct(boolean product) {
        isProduct = product;
        if (product)
            setStockType("상품");
        else
            setStockType("기계");
    }

    public ProductPriceDTO getProduct() {
        return product;
    }

    public void setProduct(ProductPriceDTO product) {
        this.product = product;
        setStockName(product.getName());
        setPrice(product.getPrice());
    }

    public MachineDTO getMachine() {
        return machine;
    }

    public void setMachine(MachineDTO machine) {
        this.machine = machine;
        setStockName(machine.getName());
        setPrice(0);
    }

    public String getStockName() {
        return stockName.get();
    }

    public void setStockName(String stockName) {
        this.stockName.set(stockName);
    }

    public int getPrice() {
        return price.get();
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public int getCount() {
        return count.get();
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    @Override
    public String toString() {
        return stockName.get();
    }
}
