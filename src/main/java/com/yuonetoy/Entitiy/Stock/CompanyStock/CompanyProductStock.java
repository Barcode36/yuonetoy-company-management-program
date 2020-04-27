package com.yuonetoy.Entitiy.Stock.CompanyStock;

import com.yuonetoy.Entitiy.Company;
import com.yuonetoy.Entitiy.Product;
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
@Table(name = "company_ProductStock")
public class CompanyProductStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CPS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_FK", referencedColumnName = "C_ID")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_FK", referencedColumnName = "P_ID")
    private Product product;

    @Column(name = "COUNT")
    private Integer count;
}
