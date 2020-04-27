package com.yuonetoy.Service;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDetailDTO;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountPreviewDTO;
import com.yuonetoy.DTO.Account.TaxAccount.TaxAccountDTO;
import com.yuonetoy.DTO.Area.SalesAccountAreaDTO;
import com.yuonetoy.DTO.CompanyType.CompanyTypeDTO;
import com.yuonetoy.DTO.CompanyType.SecondCompanyTypeDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.SalesAccountMachineDetailDTO;
import com.yuonetoy.DTO.Stock.EmployeeMachineStockDTO;
import com.yuonetoy.DTO.Stock.EmployeeProductStockDTO;
import com.yuonetoy.Entitiy.*;
import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.Account.TaxAccount;
import com.yuonetoy.Entitiy.Area.SalesAccountArea;
import com.yuonetoy.Entitiy.CompanyType.CompanyType;
import com.yuonetoy.Entitiy.CompanyType.SecondCompanyType;
import com.yuonetoy.Repository.Account.SalesAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AddSalesAccountService {
    @Autowired
    protected SalesAccountRepository salesAccountRepository;

    @Autowired
    protected TaxAccountService taxAccountService;
    @Autowired
    protected EmployeeService employeeService;
    @Autowired
    protected CompanyTypeService companyTypeService;
    @Autowired
    protected SalesAccountAreaService salesAccountAreaService;

    @Autowired
    private MachineManagementService machineManagementService;

    @Autowired
    protected ModelMapper modelMapper;

    public List<TaxAccountDTO> getTaxAccount(String searchText) {
        return taxAccountService.findTaxAccount(searchText);
    }

    public List<EmployeeDTO> getEmployeeList() {
        return employeeService.readEmployeeDTO();
    }

    public List<SalesAccountAreaDTO> getSalesAccountAreaList() {
        return salesAccountAreaService.readSalesAccountArea();
    }

    public List<CompanyTypeDTO> getCompanyTypeList() {
        return companyTypeService.readCompanyType();
    }

    public List<SecondCompanyTypeDTO> getSecondCompanyTypeList(CompanyTypeDTO companyTypeDTO) {
        return companyTypeService.readSecondCompanyType(companyTypeDTO);
    }

    public List<EmployeeMachineStockDTO> getMachineList(long employeeid) {
        return machineManagementService.getEmployeeMachineStockList(employeeid);
    }

    public List<EmployeeProductStockDTO> callProductLinkedMachine(EmployeeDTO employeeDTO, MachineDTO machineDTO) {
        return machineManagementService.callProductLinkedMachine(employeeDTO, machineDTO);
    }

    @Transactional
    public SalesAccount addSalesAccount(SalesAccountDetailDTO salesAccountDetailDTO) {

        SalesAccountArea salesAccountArea = modelMapper.map(salesAccountDetailDTO.getSalesAccountArea(), SalesAccountArea.class);
        CompanyType companyType = modelMapper.map(salesAccountDetailDTO.getCompanyType(), CompanyType.class);
        SecondCompanyType secondCompanyType = salesAccountDetailDTO.getSecondCompanyType() == null ? null : modelMapper.map(salesAccountDetailDTO.getSecondCompanyType(), SecondCompanyType.class);
        Employee employee = modelMapper.map(salesAccountDetailDTO.getEmployee(), Employee.class);
        TaxAccount taxAccount = modelMapper.map(salesAccountDetailDTO.getTaxaccount(), TaxAccount.class);

        SalesAccount salesAccount = modelMapper.map(salesAccountDetailDTO, SalesAccount.class);
        salesAccount.setIsUsing(true);
//        salesAccount.setName(name);
//        salesAccount.setRepresentative(representative);
//        salesAccount.setAddress(address);
//        salesAccount.setPh(ph);
//        salesAccount.setEntryDate(entryDate);
//        salesAccount.setTaxBillType(taxBillType);
//        salesAccount.setDepositType(depositType);
//        salesAccount.setIsYuoneToy(isYuoneToy);
//        salesAccount.setSalesAccountArea(salesAccountArea);
//        salesAccount.setCompanyType(companyType);
//        salesAccount.setSecondCompanyType(secondCompanyType);
//        salesAccount.setEmployee(employee);
//        salesAccount.setTaxaccount(taxAccount);
        salesAccount = salesAccountRepository.save(salesAccount);
        return salesAccount;
    }

    public SalesAccountPreviewDTO updateSalesAccount(SalesAccountDetailDTO salesAccountDetailDTO, String name, String representative, String address,
                                                     String ph, LocalDate entryDate, int taxBillType,
                                                     int depositType, Boolean isYuoneToy, SalesAccountAreaDTO salesAccountAreaDTO,
                                                     CompanyTypeDTO companyTypeDTO, SecondCompanyTypeDTO secondCompanyTypeDTO, EmployeeDTO employeeDTO, TaxAccountDTO taxAccountDTO) {

        SalesAccount salesAccount = salesAccountRepository.findById(salesAccountDetailDTO.getId()).get();

        SalesAccountArea salesAccountArea = salesAccountAreaService.toSalesAccountArea(salesAccountAreaDTO);
        CompanyType companyType = companyTypeService.toCompanyType(companyTypeDTO);
        SecondCompanyType secondCompanyType = companyTypeService.toSecondCompanyType(secondCompanyTypeDTO);
        Employee employee = employeeService.toEmployee(employeeDTO);
        TaxAccount taxAccount = taxAccountService.toTaxAccount(taxAccountDTO);

        salesAccount.update(name, representative, address, ph, entryDate,
                taxBillType, depositType, isYuoneToy, salesAccountArea, companyType, secondCompanyType, employee, taxAccount);
        salesAccountRepository.save(salesAccount);

        SalesAccountPreviewDTO salesAccountPreviewDTO = modelMapper.map(salesAccount, SalesAccountPreviewDTO.class);
        return salesAccountPreviewDTO;
    }

}
