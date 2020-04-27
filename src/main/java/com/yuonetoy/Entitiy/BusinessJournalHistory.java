package com.yuonetoy.Entitiy;

import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachine;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachineSales;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeStockHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "BusinessJournalHistory")
public class BusinessJournalHistory {

    public BusinessJournalHistory() {
        fees = 0.0;
        initialQuantity = 0;
        machineCount = 0;
        minusValue = 0;
        plusValue = 0;
        refundValue = (long) 0;
        salesValue = (long) 0;
        supplyCount = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BJH_ID")
    private Long id;
    // OneToOne. this is fake ManyToOne. because LazyLoding
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BJHL_FK", referencedColumnName = "BJHL_ID")
    private BusinessJournalHistoryList businessJournalHistoryList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SA_M_FK", referencedColumnName = "SA_M_ID")
    private SalesAccountMachine salesAccountMachine;

    @Column(name = "type")
    private String type;

    @Column(name = "machineCount")
    private Integer machineCount;

    @Column(name = "initialQuantity")
    private Integer initialQuantity;

    @Column(name = "fees")
    private Double fees;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_FK", referencedColumnName = "P_ID")
    private Product product;

    @Column(name = "supplyCount")
    private Integer supplyCount;

    @Column(name = "plusValue")
    private Integer plusValue;

    @Column(name = "minusValue")
    private Integer minusValue;

    @Column(name = "salesValue")
    private Long salesValue;

    @Column(name = "refundValue")
    private Long refundValue;
    // OneToOne. this is fake ManyToOne. because LazyLoding
    @OneToMany(mappedBy = "businessJournalHistory", fetch = FetchType.LAZY)
    private Set<EmployeeStockHistory> employeeStockHistory;

    @OneToMany(mappedBy = "businessJournalHistory", fetch = FetchType.LAZY)
    private Set<SalesAccountMachineSales> salesAccountMachineSales;
}
