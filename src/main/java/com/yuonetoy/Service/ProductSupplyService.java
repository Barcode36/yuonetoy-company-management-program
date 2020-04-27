package com.yuonetoy.Service;

import com.yuonetoy.DTO.*;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import com.yuonetoy.DTO.Stock.EmployeeProductStockDTO;
import com.yuonetoy.Entitiy.BusinessJournalHistoryList;
import com.yuonetoy.Entitiy.Product;
import com.yuonetoy.Entitiy.ProductLinkedMachine;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachine;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeProductStock;
import com.yuonetoy.Repository.ProductLinkedMachineRepository;
import com.yuonetoy.Repository.SalesAccountMachineRepository;
import com.yuonetoy.Repository.Stock.EmployeeProductStockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class ProductSupplyService {
    @Autowired
    private SalesAccountMachineRepository salesAccountMachineRepository;
    @Autowired
    private ProductLinkedMachineService productLinkedMachineService;
    @Autowired
    private SalesAccountMachineService salesAccountMachineService;

    @Autowired
    BusinessJournalHistoryService businessJournalHistoryService;

    @Autowired
    private ModelMapper modelMapper;

    public List<SalesAccountMachineDTO> getSalesAccountMachineDTO(Long salesAccount_id) {
        return salesAccountMachineService.getSalesAccountMachineDTO(salesAccount_id);
    }

    public List<EmployeeProductStockDTO> callProductLinkedMachine(EmployeeDTO employeeDTO, MachineDTO machineDTO) {
        return productLinkedMachineService.callProductLinkedMachine(employeeDTO, machineDTO);
    }

    public void saveSalesAccountProductStock(ProductSupplyDTO productSupplyDTO, BusinessJournalHistoryList businessJournalHistoryList) {
        EmployeeDTO employeeDTO = productSupplyDTO.getEmployee();
        ProductPriceDTO productPriceDTO = productSupplyDTO.getProduct();
        SalesAccountMachineDetailDTO salesAccountMachineDetailDTO = modelMapper.map(productSupplyDTO.getSalesAccountMachine(), SalesAccountMachineDetailDTO.class);
        int sendCount = productSupplyDTO.getProductCount();
        LocalDate date = productSupplyDTO.getLocalDate();
        int plusValue = productSupplyDTO.getPlusValue();
        int minusValue = productSupplyDTO.getMinusValue();

        //영업일지 기록
      //  businessJournalHistoryService.saveProductSupplyHistory(businessJournalHistoryList, salesAccountMachineDetailDTO.getId(), sendCount, plusValue, minusValue);

        long salesAccountId = salesAccountMachineDetailDTO.getSalesAccount().getId();
        long machineId = salesAccountMachineDetailDTO.getMachine().getId();

        SalesAccountMachine salesAccountMachines = salesAccountMachineRepository.findBySalesAccountIdAndMachineId(salesAccountId, machineId);
        salesAccountMachines.setPlusValue(salesAccountMachines.getPlusValue() + plusValue);
        salesAccountMachines.setPlusValue(salesAccountMachines.getMinusValue() + minusValue);
        salesAccountMachineRepository.save(salesAccountMachines);
    }

}
