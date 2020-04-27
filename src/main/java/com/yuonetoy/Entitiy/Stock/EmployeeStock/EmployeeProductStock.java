package com.yuonetoy.Entitiy.Stock.EmployeeStock;

import com.yuonetoy.Entitiy.Employee;
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
@Table(name = "employee_ProductStock")
public class EmployeeProductStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EPS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "E_FK", referencedColumnName = "E_ID")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_FK", referencedColumnName = "P_ID")
    private Product product;

    @Column(name = "COUNT")
    private Integer count;
}
