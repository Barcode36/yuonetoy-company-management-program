package com.yuonetoy.DTO;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessJournalHistoryListDTO {
    private long id;
    private String type;
    private String salesAccountName;
    private String employeeName;
    private LocalDate date;
}
