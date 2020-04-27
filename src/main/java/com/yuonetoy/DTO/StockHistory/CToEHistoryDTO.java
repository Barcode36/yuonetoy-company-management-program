package com.yuonetoy.DTO.StockHistory;

import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountDTO;
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

public class CToEHistoryDTO implements SendStockHistory {
    private Long id;
    private ObjectProperty<CompanyDTO> company;
    private ObjectProperty<EmployeeDTO> employee;
    private ObjectProperty<ProductPriceDTO> product;
    private IntegerProperty sendedCount;
    private ObjectProperty<LocalDate> date;

    public CToEHistoryDTO() {
        company = new SimpleObjectProperty<CompanyDTO>(this, "company");
        employee = new SimpleObjectProperty<EmployeeDTO>(this, "employee");
        product = new SimpleObjectProperty<ProductPriceDTO>(this, "product");
        sendedCount = new SimpleIntegerProperty(this, "sendedCount");
        date = new SimpleObjectProperty<LocalDate>(this, "date");
    }

    public CToEHistoryDTO(Long id, CompanyDTO companyDTO, EmployeeDTO employeeDTO, ProductPriceDTO product, int sendedCount, LocalDate date) {
        this();
        this.id = id;
        setCompany(companyDTO);
        setEmployee(employeeDTO);
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

    public CompanyDTO getCompany() {
        return company.get();
    }

    public void setCompany(CompanyDTO company) {
        this.company.set(company);
    }

    public EmployeeDTO getEmployee() {
        return employee.get();
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee.set(employee);
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
