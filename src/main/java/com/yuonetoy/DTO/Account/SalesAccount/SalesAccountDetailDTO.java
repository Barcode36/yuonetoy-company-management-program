package com.yuonetoy.DTO.Account.SalesAccount;

import com.yuonetoy.DTO.Account.TaxAccount.TaxAccountDTO;
import com.yuonetoy.DTO.Area.SalesAccountAreaDTO;
import com.yuonetoy.DTO.CompanyType.CompanyTypeDTO;
import com.yuonetoy.DTO.CompanyType.SecondCompanyTypeDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.SalesAccountMachineDetailDTO;
import com.yuonetoy.DTO.SalesAccountRevisionHistoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesAccountDetailDTO {
    private Long id;
    private String name;
    private String representative;
    private String address;
    private String ph;
    private LocalDate entryDate;
    private LocalDate withdrawalDate;
    private int taxBillType;
    private int depositType;
    private Boolean isUsing;
    private Boolean isYuoneToy;
    private SalesAccountAreaDTO salesAccountArea;
    private CompanyTypeDTO companyType;
    private SecondCompanyTypeDTO secondCompanyType;
    private EmployeeDTO employee;
    private TaxAccountDTO taxaccount;
    private List<SalesAccountRevisionHistoryDTO> salesAccountRevisionHistories;
    private List<SalesAccountMachineDetailDTO> salesAccountMachines;
}