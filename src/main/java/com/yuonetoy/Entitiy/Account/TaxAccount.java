package com.yuonetoy.Entitiy.Account;

import com.yuonetoy.Tool.LocalDatePersistenceConverter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="taxaccount")
public class TaxAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TA_ID")
    private Long id;

    @Column(name="NAME")
    private String name;

    @Column(name="representative")
    private String representative;

    @Column(name="address")
    private String address;

    @Column(name="ph")
    private String ph;

    @Convert(converter = LocalDatePersistenceConverter.class)
    @Column(name="entryDate")
    private LocalDate entryDate;

    @Column(name = "uptea")
    private String uptea;

    @Column(name = "jongmok")
    private String jongmok;

    @Column(name = "companyNum")
    private String companyNum;

    @Column(name = "manager_name")
    private String manager_name;

    @Column(name = "manager_email")
    private String manager_email;

    @Column(name = "manager_ph")
    private String manager_ph;

    public void update(String name, String representative, String address,
                       String ph, LocalDate entryDate, String uptea,
                       String jongmok, String companyNum, String managerName,
                       String managerEmail, String managerPh){
        this.name = name;
        this.representative = representative;
        this.address = address;
        this.ph = ph;
        this.entryDate = entryDate;
        this.uptea = uptea;
        this.jongmok = jongmok;
        this.companyNum = companyNum;
        this.manager_name = managerName;
        this.manager_email = managerEmail;
        this.manager_ph = managerPh;
    }
}
