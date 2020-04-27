package com.yuonetoy.Entitiy;

import com.yuonetoy.Entitiy.Account.PurchaseAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "P_ID")
    private Long id;

    @Type(type = "yes_no")
    @Column(name = "isCapsuleToy")
    private Boolean isCapsuleToy;

    @Column(name = "PRODUCT_NM")
    private String name;

    @Column(name = "PRICE")
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PA_FK", referencedColumnName = "PA_ID")
    private PurchaseAccount purchaseAccount;

    public Boolean getCapsuleToy() {
        return isCapsuleToy;
    }

    public void setCapsuleToy(Boolean capsuleToy) {
        isCapsuleToy = capsuleToy;
    }

}
