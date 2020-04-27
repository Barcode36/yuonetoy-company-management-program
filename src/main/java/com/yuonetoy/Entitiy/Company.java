
package com.yuonetoy.Entitiy;

import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyMachineStock;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyProductStock;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C_ID")
    private Long id;

    @Column(name="COMPANY_NM")
    private String name;

    @Column(name="ADDRESS")
    private String address;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<CompanyProductStock> companyProductStocks;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<CompanyMachineStock> companyMachineStocks;
}
