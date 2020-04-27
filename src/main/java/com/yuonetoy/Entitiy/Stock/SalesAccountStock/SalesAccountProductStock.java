package com.yuonetoy.Entitiy.Stock.SalesAccountStock;

import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.Product;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachine;
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
@Table(name = "salesAccount_ProductStock")
public class SalesAccountProductStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SAPS_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SA_M_FK", referencedColumnName = "SA_M_ID")
    private SalesAccountMachine salesAccountMachine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "P_FK", referencedColumnName = "P_ID")
    private Product product;

    @Column(name = "COUNT")
    private Integer count;
}
