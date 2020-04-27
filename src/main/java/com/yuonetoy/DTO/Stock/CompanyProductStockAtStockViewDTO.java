package com.yuonetoy.DTO.Stock;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class CompanyProductStockAtStockViewDTO {
    private Long id;
    private StringProperty company;
    private StringProperty product;
    private IntegerProperty count;

    public CompanyProductStockAtStockViewDTO() {
        company = new SimpleStringProperty(this, "company");
        product = new SimpleStringProperty(this, "product");
        count = new SimpleIntegerProperty(this, "count");
    }

    public CompanyProductStockAtStockViewDTO(Long id, String company, String product, Integer count) {
        this();
        this.id = id;
        setCompany(company);
        setProduct(product);
        setCount(count);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company.get();
    }

    public StringProperty companyProperty() {
        return company;
    }

    public void setCompany(String company) {
        this.company.set(company);
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

    public void setCount(int count) {
        this.count.set(count);
    }

    @Override
    public String toString() {
        return product.getName() + " (남은 재고 : " + count + "개)";
    }
}
