package com.yuonetoy.Entitiy.Stock.EmployeeStock;

import com.yuonetoy.Entitiy.BusinessJournalHistory;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeMachineStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeProductStock;
import com.yuonetoy.Entitiy.StockHistory.SendStockHistory;
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

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "employee_StockHistory")
public class EmployeeStockHistory {

    public EmployeeStockHistory(){
        sendMachineCount = 0;
        sendProductCount = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ESH_ID")
    private Long id;

    @Type(type = "yes_no")
    private Boolean isCompanySendStock;
    // OneToOne. this is fake ManyToOne. because LazyLoding
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SSH_FK", referencedColumnName = "SSH_ID")
    private SendStockHistory sendStockHistory;
    // OneToOne. this is fake ManyToOne. because LazyLoding
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BJH_FK", referencedColumnName = "BJH_ID")
    private BusinessJournalHistory businessJournalHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMS_FK", referencedColumnName = "EMS_ID")
    private EmployeeMachineStock employeeMachineStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EPS_FK", referencedColumnName = "EPS_ID")
    private EmployeeProductStock employeeProductStock;

    @Column(name = "sendProductCount")
    private Integer sendProductCount;

    @Column(name = "sendMachineCount")
    private Integer sendMachineCount;

    @Convert(converter = LocalDatePersistenceConverter.class)
    @Column(name = "DATE")
    private LocalDate date;
}
