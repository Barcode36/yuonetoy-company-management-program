package com.yuonetoy.DTO;

import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountDTO;
import com.yuonetoy.DTO.Company.CompanyDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import javafx.beans.property.*;

import java.time.LocalDate;

public class SendStockDTO {
    public SendStockDTO() {
        type = new SimpleStringProperty(this, "type");
        sourceName = new SimpleStringProperty(this, "sourceName");
        targetName = new SimpleStringProperty(this, "targetName");
        stockName = new SimpleStringProperty(this, "stockName");
        stockPrice = new SimpleIntegerProperty(this, "stockPrice");
        sendCount = new SimpleIntegerProperty(this, "sendCount");
        date = new SimpleObjectProperty<LocalDate>(this, "date");
    }

    private PurchaseAccountDTO sourcePurchaseAccount;
    private CompanyDTO sourceCompany;
    private CompanyDTO targetCompany;
    private EmployeeDTO targetEmployee;
    private boolean isProduct;
    private ProductPriceDTO product;
    private MachineDTO machine;
    private ObjectProperty<LocalDate> date;

    private StringProperty type;
    private StringProperty sourceName;
    private StringProperty targetName;
    private StringProperty stockName;
    private IntegerProperty stockPrice;
    private IntegerProperty sendCount;

    public void initStatus() {
        if (type.get().hashCode() == "상품 입고".hashCode()) {
            sourceName.set(sourcePurchaseAccount.getName());
            targetName.set(targetCompany.getName());
        } else if (type.get().hashCode() == "상품 출고".hashCode()) {
            sourceName.set(sourceCompany.getName());
            targetName.set(targetEmployee.getName());
        } else if (type.get().hashCode() == "본지 이동".hashCode()) {
            sourceName.set(sourceCompany.getName());
            targetName.set(targetCompany.getName());
        }

        if (isProduct) {
            if (type.get().hashCode() == "상품 출고".hashCode() && product.getCapsuleToy())
                setStockName(new StringBuilder().append("캡슐 토이 (").append(product.getName()).append(")").toString());
            else
                setStockName(product.getName());

            setStockPrice(product.getPrice());
        } else {
            setStockName(machine.getName());
            setStockPrice(0);
        }
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getSourceName() {
        return sourceName.get();
    }

    public StringProperty sourceNameProperty() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName.set(sourceName);
    }

    public String getTargetName() {
        return targetName.get();
    }

    public StringProperty targetNameProperty() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName.set(targetName);
    }

    public PurchaseAccountDTO getSourcePurchaseAccount() {
        return sourcePurchaseAccount;
    }

    public void setSourcePurchaseAccount(PurchaseAccountDTO sourcePurchaseAccount) {
        this.sourcePurchaseAccount = sourcePurchaseAccount;
    }

    public CompanyDTO getSourceCompany() {
        return sourceCompany;
    }

    public void setSourceCompany(CompanyDTO sourceCompany) {
        this.sourceCompany = sourceCompany;
    }

    public CompanyDTO getTargetCompany() {
        return targetCompany;
    }

    public void setTargetCompany(CompanyDTO targetCompany) {
        this.targetCompany = targetCompany;
    }

    public EmployeeDTO getTargetEmployee() {
        return targetEmployee;
    }

    public void setTargetEmployee(EmployeeDTO targetEmployee) {
        this.targetEmployee = targetEmployee;
    }

    public boolean isProduct() {
        return isProduct;
    }

    public void setIsProduct(boolean product) {
        isProduct = product;
    }

    public ProductPriceDTO getProduct() {
        return product;
    }

    public void setProduct(ProductPriceDTO product) {
        this.product = product;
    }

    public MachineDTO getMachine() {
        return machine;
    }

    public void setMachine(MachineDTO machine) {
        this.machine = machine;
    }

    public String getStockName() {
        return stockName.get();
    }

    public StringProperty stockNameProperty() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName.set(stockName);
    }

    public int getStockPrice() {
        return stockPrice.get();
    }

    public IntegerProperty stockPriceProperty() {
        return stockPrice;
    }

    public void setStockPrice(int stockPrice) {
        this.stockPrice.set(stockPrice);
    }

    public int getSendCount() {
        return sendCount.get();
    }

    public IntegerProperty sendCountProperty() {
        return sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount.set(sendCount);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }
}