package com.yuonetoy.DTO.Stock;

import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import javafx.beans.property.*;


public class EmployeeProductStockDTO {
    private Long id;
    private ObjectProperty<ProductPriceDTO> product;
    private String stockName;
    private IntegerProperty count;


    public EmployeeProductStockDTO() {

        product = new SimpleObjectProperty<>(this, "product");
        count = new SimpleIntegerProperty(this, "count");
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setStockName() {
        stockName =  getProduct().getName() + " (남은 재고 : " + count.get() + "개)";
    }

    @Override
    public String toString() {
        return stockName;
    }
}
