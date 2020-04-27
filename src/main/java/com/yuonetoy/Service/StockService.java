package com.yuonetoy.Service;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.Company.CompanyDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import com.yuonetoy.DTO.SalesAccountMachineDTO;
import com.yuonetoy.DTO.Stock.*;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyMachineStock;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyProductStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeMachineStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeProductStock;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountMachineStock;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountProductStock;
import com.yuonetoy.Repository.Stock.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {
    @Autowired
    CompanyService companyService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    SalesAccountService salesAccountService;

    @Autowired
    EmployeeProductStockRepository employeeProductStockRepository;
    @Autowired
    CompanyProductStockRepository companyProductStockRepository;
    @Autowired
    SalesAccountProductStockRepository salesAccountProductStockRepository;
    @Autowired
    EmployeeMachineStockRepository employeeMachineStockRepository;
    @Autowired
    CompanyMachineStockRepository companyMachineStockRepository;
    @Autowired
    SalesAccountMachineStockRepository salesAccountMachineStockRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<CompanyDTO> getCompany() {
        return companyService.readCompany();
    }

    public List<EmployeeDTO> getEmployee() {
        return employeeService.readEmployeeDTO();
    }

    public List<SalesAccountDTO> getSalesAccount() {
        return salesAccountService.readSalesAccount();
    }

    public List<CompanyProductStockAtStockViewDTO> getCompanyProductStock() {
        List<CompanyProductStockAtStockViewDTO> companyProductStockAtStockViewDTOList = companyProductStockRepository.findAllByCompanyProductStockDTO();
        return companyProductStockAtStockViewDTOList;
    }

    public List<EmployeeProductStockAtStockViewDTO> getEmployeeProductStock() {
        List<EmployeeProductStockAtStockViewDTO> employeeProductStockAtStockViewDTOList = employeeProductStockRepository.findAllByEmployeeProductStockDTO();
        return employeeProductStockAtStockViewDTOList;
    }

    public List<SalesAccountProductStockAtStockViewDTO> getSalesAccountProductStock() {
        List<SalesAccountProductStockAtStockViewDTO> salesAccountProductStockAtStockViewDTOList = salesAccountProductStockRepository.findAllBySalesAccountProductStockDTO();
        return salesAccountProductStockAtStockViewDTOList;
    }

    public List<CompanyMachineStockAtStockViewDTO> getCompanyMachineStock() {
        List<CompanyMachineStockAtStockViewDTO> companyMachineStockAtStockViewDTOList = companyMachineStockRepository.findAllByCompanyMachineStockDTO();
        return companyMachineStockAtStockViewDTOList;
    }

    public List<EmployeeMachineStockAtStockViewDTO> getEmployeeMachineStock() {
        List<EmployeeMachineStockAtStockViewDTO> employeeMachineStockAtStockViewDTOList = employeeMachineStockRepository.findAllByEmployeeMachineStockDTO();
        return employeeMachineStockAtStockViewDTOList;
    }

    public List<SalesAccountMachineStockAtStockViewDTO> getSalesAccountMachineStock() {
        List<SalesAccountMachineStockAtStockViewDTO> salesAccountMachineStockAtStockViewDTOList = salesAccountMachineStockRepository.findAllBySalesAccountMachineStockDTO();
        return salesAccountMachineStockAtStockViewDTOList;
    }
}
