package com.yuonetoy.Entitiy.SalesAccountMachine;

import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.Machine;
import com.yuonetoy.Entitiy.Product;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountMachineStock;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountProductStock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SalesAccount_Machine")
public class SalesAccountMachine implements PersistentAttributeInterceptable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SA_M_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SA_FK",
            referencedColumnName = "SA_ID")
    private SalesAccount salesAccount;

    @Column(name = "FEES")
    private Double fees;

    @Column(name = "INITIAL_QUANTITY")
    private Integer initialQuantity;

    @Column(name ="plusValue")
    private Integer plusValue;

    @Column(name ="minusValue")
    private Integer minusValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "M_FK" , referencedColumnName = "M_ID")
    private Machine machine;

    @OneToOne(mappedBy = "salesAccountMachine", fetch = FetchType.LAZY)
    private SalesAccountMachineStock salesAccountMachineStock;

    @OneToOne(mappedBy = "salesAccountMachine", fetch = FetchType.LAZY)
    private SalesAccountProductStock salesAccountProductStock;

    @OneToMany(mappedBy = "salesAccountMachine", fetch = FetchType.LAZY)
    private Set<SalesAccountMachineSales> salesAccountMachineSales;

    @Transient
    private PersistentAttributeInterceptor interceptor;

    @Override
    public PersistentAttributeInterceptor $$_hibernate_getInterceptor() {
        return interceptor;
    }

    @Override
    public void $$_hibernate_setInterceptor(PersistentAttributeInterceptor persistentAttributeInterceptor) {
        interceptor = persistentAttributeInterceptor;
    }
}
