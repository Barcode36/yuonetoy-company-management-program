package com.yuonetoy.Entitiy.Account;


import com.yuonetoy.Entitiy.Area.SalesAccountArea;
import com.yuonetoy.Entitiy.CompanyType.CompanyType;
import com.yuonetoy.Entitiy.CompanyType.SecondCompanyType;
import com.yuonetoy.Entitiy.Employee;

import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachine;
import com.yuonetoy.Entitiy.SalesAccountRevisionHistory;
import com.yuonetoy.Tool.LocalDatePersistenceConverter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="salesaccount")
public class SalesAccount {
    @Override
    public boolean equals(Object obj) {
        SalesAccount salesAccount = (SalesAccount) obj;
        return (id == salesAccount.id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SA_ID")
    private Long id;

    @Type(type = "yes_no")
    @Column(name = "isUsing")
    private Boolean isUsing;

    @Column(name="NAME")
    private String name;

    @Column(name="REPRESENTATIVE")
    private String representative;

    @Column(name="ADDRESS")
    private String address;

    @Column(name="PH")
    private String ph;

    @Convert(converter = LocalDatePersistenceConverter.class)
    @Column(name="ENTRY_DATE")
    private LocalDate entryDate;

    @Convert(converter = LocalDatePersistenceConverter.class)
    @Column(name="withdrawal_DATE")
    private LocalDate withdrawalDate;

    @Column(name = "taxBill_Type")
    private int taxBillType;

    @Column(name = "deposit_Type")
    private int depositType;

    @Type(type = "yes_no")
    @Column(name = "isYuoneToy")
    private Boolean isYuoneToy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SA_A_FK",
            referencedColumnName = "SA_A_ID")
    private SalesAccountArea salesAccountArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CT_FK",
            referencedColumnName = "CT_ID")
    private CompanyType companyType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCT_FK",
            referencedColumnName = "SCT_ID")
    private SecondCompanyType secondCompanyType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "E_FK",
            referencedColumnName = "E_ID")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TA_FK",
            referencedColumnName = "TA_ID")
    private TaxAccount taxaccount;

    @OneToMany(mappedBy = "salesAccount",fetch = FetchType.LAZY)
    private Set<SalesAccountMachine> salesAccountMachines;

    @OneToMany(mappedBy = "salesAccount",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<SalesAccountRevisionHistory> salesAccountRevisionHistories;

    public void update(String name, String representative, String address,
                       String ph, LocalDate entryDate, int taxBillType,
                       int depositType, Boolean isYuoneToy, SalesAccountArea salesAccountArea,
                       CompanyType companyType, SecondCompanyType secondCompanyType, Employee employee, TaxAccount taxaccount){
        this.name = name;
        this.representative = representative;
        this.address = address;
        this.ph = ph;
        this.entryDate = entryDate;
        this.taxBillType = taxBillType;
        this.depositType = depositType;
        this.isYuoneToy = isYuoneToy;
        this.salesAccountArea = salesAccountArea;
        this.companyType = companyType;
        this.secondCompanyType = secondCompanyType;
        this.employee = employee;
        this.taxaccount = taxaccount;
    }
}