package com.yuonetoy.Entitiy.Account;

import com.yuonetoy.Entitiy.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="purchaseaccount")
public class PurchaseAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pa_id")
    private Long id;

    @Column(name="NAME")
    private String name;

    @Column(name="representative")
    private String representative;

    @Column(name="address")
    private String address;

    @Column(name="ph")
    private String ph;

    @Column(name="entryDate")
    private LocalDate entryDate;

    @Column(name = "uptea")
    private String uptea;

    @Column(name = "jongmok")
    private String jongmok;

    @Column(name = "companyNum")
    private String companyNum;

    @OneToMany(mappedBy = "purchaseAccount", fetch = FetchType.LAZY)
    private List<Product> product;

    public void update(String name, String representative, String address,
                       String ph, LocalDate entryDate, String uptea,
                       String jongmok, String companyNum){
        this.name = name;
        this.representative = representative;
        this.address = address;
        this.ph = ph;
        this.entryDate = entryDate;
        this.uptea = uptea;
        this.jongmok = jongmok;
        this.companyNum = companyNum;
    }
}
