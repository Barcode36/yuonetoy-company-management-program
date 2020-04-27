package com.yuonetoy.Entitiy.Stock.CompanyStock;

import com.yuonetoy.Entitiy.Company;
import com.yuonetoy.Entitiy.Machine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company_MachineStock")
public class CompanyMachineStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CMS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_FK", referencedColumnName = "C_ID")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "M_FK", referencedColumnName = "M_ID")
    private Machine machine;

    @Column(name = "COUNT")
    private Integer count;
}
