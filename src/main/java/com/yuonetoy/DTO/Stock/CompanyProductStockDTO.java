package com.yuonetoy.DTO.Stock;

import com.yuonetoy.DTO.Company.CompanyDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;


public class CompanyProductStockDTO {
    private Long id;
    private ObjectProperty<CompanyDTO> company;
    private ObjectProperty<ProductPriceDTO> product;
    private IntegerProperty count;

    public CompanyProductStockDTO() {
        company = new SimpleObjectProperty<>(this, "company");
        product = new SimpleObjectProperty<>(this, "product");
        count = new SimpleIntegerProperty(this, "count");
    }

    public CompanyProductStockDTO(Long id, CompanyDTO company, ProductPriceDTO product, int count) {
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

    public CompanyDTO getCompany() {
        return company.get();
    }

    public void setCompany(CompanyDTO company) {
        this.company.set(company);
    }

    public ProductPriceDTO getProduct() {
        return product.get();
    }

    public void setProduct(ProductPriceDTO product) {
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
