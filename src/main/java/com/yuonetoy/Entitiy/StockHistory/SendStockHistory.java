package com.yuonetoy.Entitiy.StockHistory;

import com.yuonetoy.Entitiy.Account.PurchaseAccount;
import com.yuonetoy.Entitiy.Company;
import com.yuonetoy.Entitiy.Employee;
import com.yuonetoy.Entitiy.Machine;
import com.yuonetoy.Entitiy.Product;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyStockHistory;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeStockHistory;
import com.yuonetoy.Tool.LocalDatePersistenceConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SendStockHistory")
public class SendStockHistory  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SSH_ID")
    private Long id;

    @Type(type = "yes_no")
    @Column(name = "isPAtoC")
    private Boolean isPAtoC;

    @Type(type = "yes_no")
    @Column(name = "isCtoC")
    private Boolean isCtoC;

    @Type(type = "yes_no")
    @Column(name = "isCtoE")
    private Boolean isCtoE;

    @Type(type = "yes_no")
    @Column(name = "isMtoC")
    private Boolean isMtoC;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sourcePA_FK", referencedColumnName = "PA_ID")
    private PurchaseAccount sourcePurchaseAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sourceC_FK", referencedColumnName = "C_ID")
    private Company sourceCompany;

//    @ManyToOne
//    @JoinColumn(name = "sourceE_FK", referencedColumnName = "E_ID")
//    private Employee sourceEmployee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetC_FK", referencedColumnName = "C_ID")
    private Company targetCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetE_FK", referencedColumnName = "E_ID")
    private Employee targetEmployee;

    @Type(type = "yes_no")
    @Column(name = "isProductStock")
    private Boolean isProductStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_FK", referencedColumnName = "P_ID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "M_FK", referencedColumnName = "M_ID")
    private Machine machine;

    @Column(name = "sendCount")
    private Integer sendCount;
    // OneToOne. this is fake ManyToOne. because LazyLoding
    @OneToMany(mappedBy = "sendStockHistory", fetch = FetchType.LAZY)
    private Set<EmployeeStockHistory> employeeStockHistory;
    // OneToOne. this is fake ManyToOne. because LazyLoding
    @OneToMany(mappedBy = "sendStockHistory", fetch = FetchType.LAZY)
    private List<CompanyStockHistory> companyStockHistory;

    @Convert(converter = LocalDatePersistenceConverter.class)
    @Column(name = "DATE")
    private LocalDate date;
}
