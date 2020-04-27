package com.yuonetoy.Entitiy;

import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.Area.EmployeeArea;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeMachineStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeProductStock;
import com.yuonetoy.Tool.LocalDatePersistenceConverter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="employee")
public class Employee  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "E_ID")
    private Long id;

    @Column(name = "EMPLOYEE_NM")
    private String name;

    @Column(name = "PH")
    private String ph;

    @Column(name = "ADDRESS")
    private String address;

    @Convert(converter = LocalDatePersistenceConverter.class)
    @Column(name = "ENTRY_DATE")
    private LocalDate entryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "E_A_FK",
            referencedColumnName = "E_A_ID")
    private EmployeeArea area;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private Set<EmployeeProductStock> employeeProductStocks;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private Set<EmployeeMachineStock> employeeMachineStocks;

    public void update(String name, String ph, String address, LocalDate entryDate, EmployeeArea employeeArea){
        this.name = name;
        this.ph = ph;
        this.address = address;
        this.entryDate = entryDate;
        this.area = employeeArea;
    }
}
