package com.yuonetoy.Service;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDetailDTO;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountPreviewDTO;
import com.yuonetoy.DTO.Account.TaxAccount.TaxAccountDTO;
import com.yuonetoy.DTO.Area.SalesAccountAreaDTO;
import com.yuonetoy.DTO.CompanyType.CompanyTypeDTO;
import com.yuonetoy.DTO.CompanyType.SecondCompanyTypeDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.SalesAccountMachineDetailDTO;
import com.yuonetoy.DTO.Stock.SourceInfoAtStockManagementDTO;
import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.Account.TaxAccount;
import com.yuonetoy.Entitiy.BusinessJournalHistory;
import com.yuonetoy.Entitiy.Employee;
import com.yuonetoy.Entitiy.Product;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachine;
import com.yuonetoy.Repository.Account.SalesAccountRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SalesAccountService {

    @Autowired
    private SalesAccountRepository salesAccountRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<SalesAccountDTO> readSalesAccount(Long employee_id){
        List<SalesAccount> salesAccountList = salesAccountRepository.findAllByEmployeeId(employee_id);
        List<SalesAccountDTO> salesAccountDTOList = new ArrayList<SalesAccountDTO>();

        salesAccountList.forEach(salesAccount -> {
            SalesAccountDTO salesAccountDTO = modelMapper.map(salesAccount, SalesAccountDTO.class);
            salesAccountDTOList.add(salesAccountDTO);
        });
        return salesAccountDTOList;
    }

    @Transactional(readOnly = true)
    public List<SalesAccountDTO> readSalesAccount(){
        List<SalesAccountDTO> salesAccountList = salesAccountRepository.findAllSalesAccountDTO();
        return salesAccountList;
    }

    public List<SourceInfoAtStockManagementDTO> readSalesAccountSourceInfoStockManagementDTO(String name){
        List<SourceInfoAtStockManagementDTO> salesAccountList = salesAccountRepository.findAllSourceInfoStockManagementDTO(name);
        return salesAccountList;
    }

    @Transactional(readOnly = true)
    public SalesAccountDetailDTO findSalesAccountDetailDTO(SalesAccountPreviewDTO salesAccountPreviewDTO) {
        SalesAccount salesAccount = salesAccountRepository.findBySalesAccountId(salesAccountPreviewDTO.getId());
        SalesAccountDetailDTO salesAccountDetailDTO = modelMapper.map(salesAccount, SalesAccountDetailDTO.class);

        Employee employee = salesAccount.getEmployee();

        LinkedList<SalesAccountMachine> salesAccountMachines = new LinkedList<>(salesAccount.getSalesAccountMachines());

        for (int i = 0; i < salesAccountMachines.size(); i++) {
            SalesAccountMachineDetailDTO salesAccountMachineDetailDTO = salesAccountDetailDTO.getSalesAccountMachines().get(i);
            if (salesAccountMachineDetailDTO.getMachine().getIsProductMachine()) {
                Product product = salesAccountMachines.get(i).getSalesAccountProductStock().getProduct();
                salesAccountMachineDetailDTO.setProduct(product);
            }
            salesAccountMachineDetailDTO.setEmployee(employee);
        }

        return salesAccountDetailDTO;
    }

    public void updateIsUsing(SalesAccount salesAccount, boolean isUsing){
        salesAccount.setIsUsing(isUsing);
        salesAccountRepository.save(salesAccount);
    }
}
