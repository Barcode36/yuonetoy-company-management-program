package com.yuonetoy.Entitiy.Stock.CompanyStock;

import com.yuonetoy.Entitiy.BusinessJournalHistory;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyMachineStock;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyProductStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeMachineStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeProductStock;
import com.yuonetoy.Entitiy.StockHistory.SendStockHistory;
import com.yuonetoy.Tool.LocalDatePersistenceConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "company_StockHistory")
public class CompanyStockHistory{
    public CompanyStockHistory(){
        sendMachineCount = 0;
        sendProductCount = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CSH_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SSH_FK", referencedColumnName = "SSH_ID")
    private SendStockHistory sendStockHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CMS_FK", referencedColumnName = "CMS_ID")
    private CompanyMachineStock companyMachineStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CPS_FK", referencedColumnName = "CPS_ID")
    private CompanyProductStock companyProductStock;

    @Column(name = "sendProductCount")
    private Integer sendProductCount;

    @Column(name = "sendMachineCount")
    private Integer sendMachineCount;

    @Convert(converter = LocalDatePersistenceConverter.class)
    @Column(name = "DATE")
    private LocalDate date;
}
