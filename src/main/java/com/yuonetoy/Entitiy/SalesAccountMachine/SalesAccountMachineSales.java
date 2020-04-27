package com.yuonetoy.Entitiy.SalesAccountMachine;

import com.yuonetoy.Entitiy.BusinessJournalHistory;
import com.yuonetoy.Tool.LocalDatePersistenceConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SalesAccount_MachineSales")
public class SalesAccountMachineSales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SA_MS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SA_M_FK", referencedColumnName = "SA_M_ID")
    private SalesAccountMachine salesAccountMachine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BJH_FK", referencedColumnName = "BJH_ID")
    private BusinessJournalHistory businessJournalHistory;

    @Column(name ="sales")
    private Long sales;

    @Column(name ="balance")
    private Long balance;

    @Column(name ="refundAmount")
    private Long refundAmount;

    @Column(name ="DepositAmount")
    private Long depositAmount;

    @Type(type = "yes_no")
    @Column(name = "ConfirmationOfPayment")
    private Boolean confirmationOfPayment;

    @Convert(converter = LocalDatePersistenceConverter.class)
    @Column(name="date")
    private LocalDate date;

    @Convert(converter = LocalDatePersistenceConverter.class)
    @Column(name="DepositDate")
    private LocalDate depositDate;
}
