package com.yuonetoy.Entitiy.CompanyType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="SecondCompanyType")
public class SecondCompanyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCT_ID")
    private Long id;

    @Column(name = "TYPE_NM")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CT_FK",
            referencedColumnName = "CT_ID")
    private CompanyType companyType;

    public void update(String name){
        this.name = name;
    }
}