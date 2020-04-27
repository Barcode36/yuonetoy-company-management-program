package com.yuonetoy.Service;

import com.yuonetoy.DTO.*;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDetailDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import com.yuonetoy.DTO.Stock.EmployeeMachineStockDTO;
import com.yuonetoy.DTO.Stock.EmployeeProductStockDTO;
import com.yuonetoy.Entitiy.*;
import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.SalesAccountRevisionHistory;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeMachineStock;
import com.yuonetoy.Repository.Account.SalesAccountRepository;
import com.yuonetoy.Repository.SalesAccountRevisionHistoryRepository;
import com.yuonetoy.Repository.Stock.EmployeeMachineStockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class MachineManagementService {
    @Autowired
    private SalesAccountMachineService salesAccountMachineService;
    @Autowired
    private ProductLinkedMachineService productLinkedMachineService;
    @Autowired
    private SalesAccountService salesAccountService;
    @Autowired
    private EmployeeMachineStockRepository employeeMachineStockRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<SalesAccountDTO> getSalesAccountList() {
        return salesAccountService.readSalesAccount();
    }

    @Transactional(readOnly = true)
    public List<EmployeeMachineStockDTO> getEmployeeMachineStockList(long employeeId) {
        List<EmployeeMachineStock> employeeMachineStockList = employeeMachineStockRepository.findByEmployeeIdOrderByMachine(employeeId);
        List<EmployeeMachineStockDTO> employeeMachineStockDTOList = new LinkedList<>();

        employeeMachineStockList.forEach(employeeMachineStock -> {
            EmployeeMachineStockDTO employeeMachineStockDTO = modelMapper.map(employeeMachineStock, EmployeeMachineStockDTO.class);
            employeeMachineStockDTO.setStockName();
            employeeMachineStockDTOList.add(employeeMachineStockDTO);
        });
        return employeeMachineStockDTOList;
    }

    public List<SalesAccountMachineDetailDTO> getSalesAccountMachineDetailDTO(Long salesAccount_id) {
        return salesAccountMachineService.getSalesAccountMachineDetailDTO(salesAccount_id);
    }

    public List<SalesAccountMachineDTO> getSalesAccountMachineDTO(Long salesAccount_id) {
        return salesAccountMachineService.getSalesAccountMachineDTO(salesAccount_id);
    }

    public List<EmployeeProductStockDTO> callProductLinkedMachine(EmployeeDTO employeeDTO, MachineDTO machineDTO) {
        return productLinkedMachineService.callProductLinkedMachine(employeeDTO, machineDTO);
    }

}
