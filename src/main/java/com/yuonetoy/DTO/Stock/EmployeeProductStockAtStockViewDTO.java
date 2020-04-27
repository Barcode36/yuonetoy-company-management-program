package com.yuonetoy.DTO.Stock;

import com.yuonetoy.DTO.Product.ProductPriceDTO;
import javafx.beans.property.*;


public class EmployeeProductStockAtStockViewDTO {
    private Long id;
    private StringProperty employee;
    private StringProperty product;
    private IntegerProperty count;


    public EmployeeProductStockAtStockViewDTO() {
        employee = new SimpleStringProperty(this, "employee");
        product = new SimpleStringProperty(this, "product");
        count = new SimpleIntegerProperty(this, "count");
    }

    public EmployeeProductStockAtStockViewDTO(long id, String employee, String product, int count){
        this();
        setId(id);
        setEmployee(employee);
        setProduct(product);
        setCount(count);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployee() {
        return employee.get();
    }

    public StringProperty employeeProperty() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee.set(employee);
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

    public int getCount() {
        return count.get();
    }

    public IntegerProperty countProperty() {
        return count;
    }

    public void setCount(int count) {
        this.count.set(count);
    }
}
