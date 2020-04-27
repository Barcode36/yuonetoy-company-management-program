package com.yuonetoy.Service;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDetailDTO;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountPreviewDTO;
import com.yuonetoy.DTO.Account.TaxAccount.TaxAccountDTO;
import com.yuonetoy.DTO.Area.SalesAccountAreaDTO;
import com.yuonetoy.DTO.CompanyType.CompanyTypeDTO;
import com.yuonetoy.DTO.CompanyType.SecondCompanyTypeDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.Account.TaxAccount;
import com.yuonetoy.Entitiy.Area.SalesAccountArea;
import com.yuonetoy.Entitiy.CompanyType.CompanyType;
import com.yuonetoy.Entitiy.CompanyType.SecondCompanyType;
import com.yuonetoy.Entitiy.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ModifySalesAccountService extends AddSalesAccountService{
    @Autowired
    SalesAccountService salesAccountService;

    public SalesAccountDetailDTO findSalesAccountDetailDTO(SalesAccountPreviewDTO salesAccountPreviewDTO) {
        return salesAccountService.findSalesAccountDetailDTO(salesAccountPreviewDTO);
    }

    public SalesAccountPreviewDTO updateSalesAccount(SalesAccountDetailDTO salesAccountDetailDTO, String name, String representative, String address,
                                                     String ph, LocalDate entryDate, int taxBillType,
                                                     int depositType, Boolean isYuoneToy, SalesAccountAreaDTO salesAccountAreaDTO,
                                                     CompanyTypeDTO companyTypeDTO, SecondCompanyTypeDTO secondCompanyTypeDTO, EmployeeDTO employeeDTO, TaxAccountDTO taxAccountDTO) {


        SalesAccount salesAccount = salesAccountRepository.findById(salesAccountDetailDTO.getId()).get();

        SalesAccountArea salesAccountArea = modelMapper.map(salesAccountAreaDTO, SalesAccountArea.class);
        CompanyType companyType = modelMapper.map(companyTypeDTO, CompanyType.class);

        SecondCompanyType secondCompanyType = null;
        if (secondCompanyTypeDTO != null) {
            secondCompanyType = modelMapper.map(secondCompanyTypeDTO, SecondCompanyType.class);
        }

        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        TaxAccount taxAccount = modelMapper.map(taxAccountDTO, TaxAccount.class);

        salesAccount.update(name, representative, address, ph, entryDate,
                taxBillType, depositType, isYuoneToy, salesAccountArea, companyType, secondCompanyType, employee, taxAccount);
        salesAccountRepository.save(salesAccount);

        SalesAccountPreviewDTO salesAccountPreviewDTO = modelMapper.map(salesAccount, SalesAccountPreviewDTO.class);
        return salesAccountPreviewDTO;
    }
}
