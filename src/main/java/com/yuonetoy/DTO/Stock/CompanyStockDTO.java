package com.yuonetoy.DTO.Stock;

import com.yuonetoy.DTO.Company.CompanyDTO;
import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import javafx.beans.property.*;


public class CompanyStockDTO implements StockDTO{
    private ObjectProperty<CompanyDTO> company;
    private ProductPriceDTO product;
    private MachineDTO machine;

    private boolean isProduct;
    private StringProperty stockType;
    private StringProperty stockName;
    private IntegerProperty price;
    private IntegerProperty count;

    public CompanyStockDTO() {
        company = new SimpleObjectProperty<>(this, "company");
        stockType = new SimpleStringProperty(this, "stockType");
        stockName = new SimpleStringProperty(this, "stockName");
        price = new SimpleIntegerProperty(this, "price");
        count = new SimpleIntegerProperty(this, "count");
    }

    public CompanyStockDTO(CompanyDTO company, boolean isProduct, ProductPriceDTO product, MachineDTO machine, int count) {
        this();
        setCompany(company);
        this.isProduct = isProduct;
        if (isProduct)
            setProduct(product);
        else
            setMachine(machine);
        setCount(count);

        setStockStatus();
    }

    public String getStockType() {
        return stockType.get();
    }

    public void setStockType(String stockType) {
        this.stockType.set(stockType);
    }

    public CompanyDTO getCompany() {
        return company.get();
    }

    public void setCompany(CompanyDTO company) {
        this.company.set(company);
    }

    public ProductPriceDTO getProduct() {
        return product;
    }

    public void setProduct(ProductPriceDTO product) {
        this.product = product;
        isProduct = true;
    }

    public MachineDTO getMachine() {
        return machine;
    }

    public boolean isProduct() {
        return isProduct;
    }

    public void setMachine(MachineDTO machine) {
        this.machine = machine;
        isProduct = false;
    }

    public String getStockName() {
        return stockName.get();
    }

    public void setStockStatus() {
        if (isProduct) {
            stockName.set(product.getName());
            setPrice(product.getPrice());
            setStockType("상품");
        }else {
            stockName.set(machine.getName());
            setPrice(0);
            setStockType("기계");
        }
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
