package com.yuonetoy.DTO.Account.PurchaseAccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseAccountDetailDTO {
    private Long id;
    private String name;
    private String representative;
    private String address;
    private String ph;
    private LocalDate entryDate;
    private String uptea;
    private String jongmok;
    private String companyNum;
}
