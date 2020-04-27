package com.yuonetoy.DTO.StockHistory;

import com.yuonetoy.DTO.Company.CompanyDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.Product.ProductDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

public class CToCHistoryDTO implements SendStockHistory {
    private Long id;
    private ObjectProperty<CompanyDTO> sourceCompany;
    private ObjectProperty<CompanyDTO> targetCompany;
    private ObjectProperty<ProductPriceDTO> product;
    private IntegerProperty sendedCount;
    private ObjectProperty<LocalDate> date;

    public CToCHistoryDTO() {
        sourceCompany = new SimpleObjectProperty<CompanyDTO>(this, "sourceCompany");
        targetCompany = new SimpleObjectProperty<CompanyDTO>(this, "targetCompany");
        product = new SimpleObjectProperty<ProductPriceDTO>(this, "product");
        sendedCount = new SimpleIntegerProperty(this, "sendedCount");
        date = new SimpleObjectProperty<LocalDate>(this, "date");
    }

    public CToCHistoryDTO(Long id, CompanyDTO soureCompanyDTO, CompanyDTO targetCompanyDTO, ProductPriceDTO product, int sendedCount, LocalDate date) {
        this();
        this.id = id;
        setSourceCompany(soureCompanyDTO);
        setTargetCompany(targetCompanyDTO);
        setProduct(product);
        setSendedCount(sendedCount);
        setDate(date);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyDTO getSourceCompany() {
        return sourceCompany.get();
    }

    public void setSourceCompany(CompanyDTO sourceCompany) {
        this.sourceCompany.set(sourceCompany);
    }

    public CompanyDTO getTargetCompany() {
        return targetCompany.get();
    }

    public void setTargetCompany(CompanyDTO targetCompany) {
        this.targetCompany.set(targetCompany);
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
