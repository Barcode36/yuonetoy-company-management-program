package com.yuonetoy.DTO.Account.TaxAccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaxAccountDetailDTO {
    private Long id;
    private String name;
    private String representative;
    private String address;
    private String ph;
    private LocalDate entryDate;
    private String uptea;
    private String jongmok;
    private String companyNum;
    private String manager_name;
    private String manager_email;
    private String manager_ph;
}
