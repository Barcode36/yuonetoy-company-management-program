package com.yuonetoy.Entitiy.CompanyType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="CompanyType")
public class CompanyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CT_ID")
    private Long id;

    @Column(name = "TYPE_NM")
    private String name;

    @OneToMany(mappedBy = "companyType", fetch = FetchType.LAZY)
    private List<SecondCompanyType> secondCompanyType;

    public void update(String name){
        this.name = name;
    }
}
