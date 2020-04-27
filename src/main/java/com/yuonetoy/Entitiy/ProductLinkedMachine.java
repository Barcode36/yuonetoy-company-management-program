package com.yuonetoy.Entitiy;

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
@Table(name = "Product_Linked_Machine")
public class ProductLinkedMachine {
    @Id
    @Column(name = "PLM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "M_FK", referencedColumnName = "M_ID")
    private Machine machine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "P_FK", referencedColumnName = "P_ID")
    private Product product;
}
