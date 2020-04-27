package com.yuonetoy.Service;

import com.yuonetoy.DTO.Stock.*;
import com.yuonetoy.Entitiy.*;
import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachine;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyMachineStock;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyProductStock;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyStockHistory;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeMachineStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeProductStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeStockHistory;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountMachineStock;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountProductStock;
import com.yuonetoy.Repository.*;
import com.yuonetoy.Repository.Account.SalesAccountRepository;
import com.yuonetoy.Repository.Stock.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class StockManagementService {
    @Autowired
    CompanyService companyService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    SalesAccountService salesAccountService;

    @Autowired
    MachineRepository machineRepository;
    @Autowired
    ProductRepository productRepository;

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

    public List<SourceInfoAtStockManagementDTO> getCompany() {
        return companyService.readCompanyAtStockManagementView();
    }

    public List<SourceInfoAtStockManagementDTO> getEmployee() {
        return employeeService.readEmployeeDTOAtStockManagementView();
    }

    public List<SourceInfoAtStockManagementDTO> getSalesAccount(String name) {
        return salesAccountService.readSalesAccountSourceInfoStockManagementDTO(name);
    }


    public List<StockAtStockManagementDTO> getProductAndMachine() {
        List<StockAtStockManagementDTO> stockAtStockManagementDTOList = new ArrayList<>();

        List<Product> products = productRepository.findAll();
        products.forEach(product -> {
            StockAtStockManagementDTO stockAtStockManagementDTO = new StockAtStockManagementDTO();
            stockAtStockManagementDTO.setId(product.getId());
            stockAtStockManagementDTO.setStockType("상품 재고");
            stockAtStockManagementDTO.setStockName(product.getName());
            stockAtStockManagementDTOList.add(stockAtStockManagementDTO);
        });

        List<Machine> machines = machineRepository.findAll();
        machines.forEach(machine -> {
            StockAtStockManagementDTO stockAtStockManagementDTO = new StockAtStockManagementDTO();
            stockAtStockManagementDTO.setId(machine.getId());
            stockAtStockManagementDTO.setStockType("기계 재고");
            stockAtStockManagementDTO.setStockName(machine.getName());
            stockAtStockManagementDTOList.add(stockAtStockManagementDTO);
        });

        return stockAtStockManagementDTOList;
    }

    @Autowired
    CompanyRepository companyRepository;

    public List<SourceStockAtStockManagementDTO> getCompanyStock(long companyId) {
        Company company = companyRepository.getCompanyStockByCompanyId(companyId);
        List<SourceStockAtStockManagementDTO> sourceStockAtStockManagementDTOList = new ArrayList<>();

        company.getCompanyProductStocks().forEach(companyProductStock -> {
            int count = companyProductStock.getCount();

            SourceStockAtStockManagementDTO sourceStockAtStockManagementDTO = new SourceStockAtStockManagementDTO();
            sourceStockAtStockManagementDTO.setId(companyProductStock.getId());
            sourceStockAtStockManagementDTO.setSourceId(companyId);
            sourceStockAtStockManagementDTO.setStockId(companyProductStock.getProduct().getId());
            sourceStockAtStockManagementDTO.setStockType("상품 재고");
            sourceStockAtStockManagementDTO.setStockName(companyProductStock.getProduct().getName());
            sourceStockAtStockManagementDTO.setStockCount(count);
            sourceStockAtStockManagementDTO.setPreviousStockCount(count);
            sourceStockAtStockManagementDTO.setChanged(false);
            sourceStockAtStockManagementDTOList.add(sourceStockAtStockManagementDTO);
        });

        company.getCompanyMachineStocks().forEach(companyMachineStock -> {
            int count = companyMachineStock.getCount();

            SourceStockAtStockManagementDTO sourceStockAtStockManagementDTO = new SourceStockAtStockManagementDTO();
            sourceStockAtStockManagementDTO.setId(companyMachineStock.getId());
            sourceStockAtStockManagementDTO.setSourceId(companyId);
            sourceStockAtStockManagementDTO.setStockId(companyMachineStock.getMachine().getId());
            sourceStockAtStockManagementDTO.setStockType("기계 재고");
            sourceStockAtStockManagementDTO.setStockName(companyMachineStock.getMachine().getName());
            sourceStockAtStockManagementDTO.setStockCount(count);
            sourceStockAtStockManagementDTO.setPreviousStockCount(count);
            sourceStockAtStockManagementDTO.setChanged(false);
            sourceStockAtStockManagementDTOList.add(sourceStockAtStockManagementDTO);
        });

        sourceStockAtStockManagementDTOList.sort(new Comparator<SourceStockAtStockManagementDTO>() {
            @Override
            public int compare(SourceStockAtStockManagementDTO o1, SourceStockAtStockManagementDTO o2) {
                return o1.getStockName().compareTo(o2.getStockName());
            }
        });

        return sourceStockAtStockManagementDTOList;
    }

    @Autowired
    EmployeeRepository employeeRepository;

    public List<SourceStockAtStockManagementDTO> getEmployeeStock(long employeeId) {
        Employee employee = employeeRepository.getEmployeeStockByemployeeId(employeeId);
        List<SourceStockAtStockManagementDTO> sourceStockAtStockManagementDTOList = new ArrayList<>();

        employee.getEmployeeProductStocks().forEach(employeeProductStock -> {
            int count = employeeProductStock.getCount();

            SourceStockAtStockManagementDTO sourceStockAtStockManagementDTO = new SourceStockAtStockManagementDTO();
            sourceStockAtStockManagementDTO.setId(employeeProductStock.getId());
            sourceStockAtStockManagementDTO.setSourceId(employeeId);
            sourceStockAtStockManagementDTO.setStockId(employeeProductStock.getProduct().getId());
            sourceStockAtStockManagementDTO.setStockType("상품 재고");
            sourceStockAtStockManagementDTO.setStockName(employeeProductStock.getProduct().getName());
            sourceStockAtStockManagementDTO.setStockCount(count);
            sourceStockAtStockManagementDTO.setPreviousStockCount(count);
            sourceStockAtStockManagementDTO.setChanged(false);
            sourceStockAtStockManagementDTOList.add(sourceStockAtStockManagementDTO);
        });

        employee.getEmployeeMachineStocks().forEach(employeeMachineStock -> {
            int count = employeeMachineStock.getCount();

            SourceStockAtStockManagementDTO sourceStockAtStockManagementDTO = new SourceStockAtStockManagementDTO();
            sourceStockAtStockManagementDTO.setId(employeeMachineStock.getId());
            sourceStockAtStockManagementDTO.setSourceId(employeeId);
            sourceStockAtStockManagementDTO.setStockId(employeeMachineStock.getMachine().getId());
            sourceStockAtStockManagementDTO.setStockType("기계 재고");
            sourceStockAtStockManagementDTO.setStockName(employeeMachineStock.getMachine().getName());
            sourceStockAtStockManagementDTO.setStockCount(count);
            sourceStockAtStockManagementDTO.setPreviousStockCount(count);
            sourceStockAtStockManagementDTO.setChanged(false);
            sourceStockAtStockManagementDTOList.add(sourceStockAtStockManagementDTO);
        });

        sourceStockAtStockManagementDTOList.sort(new Comparator<SourceStockAtStockManagementDTO>() {
            @Override
            public int compare(SourceStockAtStockManagementDTO o1, SourceStockAtStockManagementDTO o2) {
                return o1.getStockName().compareTo(o2.getStockName());
            }
        });

        return sourceStockAtStockManagementDTOList;
    }

    @Autowired
    SalesAccountMachineRepository salesAccountMachineRepository;

    public List<SourceStockAtStockManagementDTO> getSalesAccountStock(long salesAccountId) {
        List<SourceStockAtStockManagementDTO> sourceStockAtStockManagementDTOList = new ArrayList<>();

        Set<SalesAccountMachine> salesAccountMachineSet = salesAccountMachineRepository.findBySalesAccountId(salesAccountId).getSalesAccountMachines();
        StringBuilder stringBuilder = new StringBuilder();

        salesAccountMachineSet.forEach(salesAccountMachine -> {

            SalesAccountMachineStock salesAccountMachineStock = salesAccountMachine.getSalesAccountMachineStock();

            SourceStockAtStockManagementDTO sourceStockAtStockManagementDTO = new SourceStockAtStockManagementDTO();
            sourceStockAtStockManagementDTO.setId(salesAccountMachineStock.getId());
            sourceStockAtStockManagementDTO.setSourceId(salesAccountId);
            sourceStockAtStockManagementDTO.setStockId(salesAccountMachine.getMachine().getId());
            sourceStockAtStockManagementDTO.setStockType("거래처 재고");

            if (salesAccountMachine.getMachine().getIsProductMachine() || salesAccountMachine.getMachine().getIsCoinChanger()) {
                SalesAccountProductStock salesAccountProductStock = salesAccountMachine.getSalesAccountProductStock();
                stringBuilder.delete(0, stringBuilder.length());
                stringBuilder.append(salesAccountMachine.getMachine().getName())
                        .append(" : ")
                        .append(salesAccountProductStock.getProduct().getName());

                sourceStockAtStockManagementDTO.setSalesAccountProductStockCount(salesAccountProductStock.getCount());
                sourceStockAtStockManagementDTO.setPreviousSalesAccountProductStockCount(salesAccountProductStock.getCount());
            }else{
                stringBuilder.delete(0, stringBuilder.length());
                stringBuilder.append(salesAccountMachine.getMachine().getName());
            }

            sourceStockAtStockManagementDTO.setStockName(stringBuilder.toString());
            sourceStockAtStockManagementDTO.setStockCount(salesAccountMachineStock.getCount());
            sourceStockAtStockManagementDTO.setPreviousStockCount(salesAccountMachineStock.getCount());
            sourceStockAtStockManagementDTO.setChanged(false);


            sourceStockAtStockManagementDTOList.add(sourceStockAtStockManagementDTO);
        });

        sourceStockAtStockManagementDTOList.sort(new Comparator<SourceStockAtStockManagementDTO>() {
            @Override
            public int compare(SourceStockAtStockManagementDTO o1, SourceStockAtStockManagementDTO o2) {
                return o1.getStockName().compareTo(o2.getStockName());
            }
        });

        return sourceStockAtStockManagementDTOList;
    }

    private void saveCompanyStock(SourceStockAtStockManagementDTO sourceStockAtStockManagementDTO, LocalDate date) {
        int supplyCount = sourceStockAtStockManagementDTO.getStockCount() - sourceStockAtStockManagementDTO.getPreviousStockCount();

        if (sourceStockAtStockManagementDTO.getChanged()) {
            if (sourceStockAtStockManagementDTO.getStockType().hashCode() == "상품 재고".hashCode()) {
                CompanyProductStock companyProductStock;
                if (sourceStockAtStockManagementDTO.getId() == null) {
                    companyProductStock = new CompanyProductStock();

                    Company company = new Company();
                    company.setId(sourceStockAtStockManagementDTO.getSourceId());
                    Product product = new Product();
                    product.setId(sourceStockAtStockManagementDTO.getStockId());

                    companyProductStock.setCompany(company);
                    companyProductStock.setProduct(product);
                    companyProductStock.setCount(sourceStockAtStockManagementDTO.getStockCount());
                } else {
                    companyProductStock = companyProductStockRepository.findById(sourceStockAtStockManagementDTO.getId()).get();
                    companyProductStock.setCount(sourceStockAtStockManagementDTO.getStockCount());
                }

                companyProductStockRepository.save(companyProductStock);


                saveCompanyProductStockHistory(companyProductStock, supplyCount, date);
            } else {
                CompanyMachineStock companyMachineStock;
                if (sourceStockAtStockManagementDTO.getId() == null) {
                    companyMachineStock = new CompanyMachineStock();

                    Company company = new Company();
                    company.setId(sourceStockAtStockManagementDTO.getSourceId());
                    Machine machine = new Machine();
                    machine.setId(sourceStockAtStockManagementDTO.getStockId());

                    companyMachineStock.setCompany(company);
                    companyMachineStock.setMachine(machine);
                    companyMachineStock.setCount(sourceStockAtStockManagementDTO.getStockCount());
                } else {
                    companyMachineStock = companyMachineStockRepository.findById(sourceStockAtStockManagementDTO.getId()).get();
                    companyMachineStock.setCount(sourceStockAtStockManagementDTO.getStockCount());
                }

                companyMachineStockRepository.save(companyMachineStock);
                saveCompanyMachineStockHistory(companyMachineStock, supplyCount, date);
            }
        }
    }

    private void saveEmployeeStock(SourceStockAtStockManagementDTO sourceStockAtStockManagementDTO, LocalDate date) {
        int supplyCount = sourceStockAtStockManagementDTO.getStockCount() - sourceStockAtStockManagementDTO.getPreviousStockCount();

        if (sourceStockAtStockManagementDTO.getStockType().hashCode() == "상품 재고".hashCode()) {
            EmployeeProductStock employeeProductStock;
            if (sourceStockAtStockManagementDTO.getId() == null) {
                employeeProductStock = new EmployeeProductStock();

                Employee employee = new Employee();
                employee.setId(sourceStockAtStockManagementDTO.getSourceId());
                Product product = new Product();
                product.setId(sourceStockAtStockManagementDTO.getStockId());

                employeeProductStock.setEmployee(employee);
                employeeProductStock.setProduct(product);
                employeeProductStock.setCount(sourceStockAtStockManagementDTO.getStockCount());
            } else {
                employeeProductStock = employeeProductStockRepository.findById(sourceStockAtStockManagementDTO.getId()).get();
                employeeProductStock.setCount(sourceStockAtStockManagementDTO.getStockCount());
            }

            employeeProductStockRepository.save(employeeProductStock);
            saveEmployeeProductStockHistory(employeeProductStock, supplyCount, date);
        } else {
            EmployeeMachineStock employeeMachineStock;
            if (sourceStockAtStockManagementDTO.getId() == null) {
                employeeMachineStock = new EmployeeMachineStock();

                Employee employee = new Employee();
                employee.setId(sourceStockAtStockManagementDTO.getSourceId());
                Machine machine = new Machine();
                machine.setId(sourceStockAtStockManagementDTO.getStockId());

                employeeMachineStock.setEmployee(employee);
                employeeMachineStock.setMachine(machine);
                employeeMachineStock.setCount(sourceStockAtStockManagementDTO.getStockCount());
            } else {
                employeeMachineStock = employeeMachineStockRepository.findById(sourceStockAtStockManagementDTO.getId()).get();
                employeeMachineStock.setCount(sourceStockAtStockManagementDTO.getStockCount());
            }

            employeeMachineStockRepository.save(employeeMachineStock);
            saveEmployeeMachineStockHistory(employeeMachineStock, supplyCount, date);
        }
    }

    @Autowired
    private ProductLinkedMachineRepository productLinkedMachineRepository;

    @Autowired
    private SalesAccountRepository salesAccountRepository;

    private SalesAccountMachine saveSalesAccountStock(SourceStockAtStockManagementDTO sourceStockAtStockManagementDTO) {
        if (sourceStockAtStockManagementDTO.getId() == null) {
            SalesAccountMachine salesAccountMachine = new SalesAccountMachine();
            Machine machine = machineRepository.findByName(sourceStockAtStockManagementDTO.getStockName());
            salesAccountMachine.setMachine(machine);
            salesAccountMachine.setInitialQuantity(0);
            salesAccountMachine.setMinusValue(0);
            salesAccountMachine.setPlusValue(0);
            salesAccountMachine.setFees(0.0);

            SalesAccount salesAccount = salesAccountRepository.findBySalesAccountId(sourceStockAtStockManagementDTO.getSourceId());
            salesAccountMachine.setSalesAccount(salesAccount);

            salesAccountMachine = salesAccountMachineRepository.save(salesAccountMachine);

            SalesAccountMachineStock salesAccountMachineStock = new SalesAccountMachineStock();
            salesAccountMachineStock.setSalesAccountMachine(salesAccountMachine);
            salesAccountMachineStock.setCount(sourceStockAtStockManagementDTO.getStockCount());
            salesAccountMachineStockRepository.save(salesAccountMachineStock);

            if (machine.getIsProductMachine() || machine.getIsCoinChanger()) {
                SalesAccountProductStock salesAccountProductStock = new SalesAccountProductStock();
                salesAccountProductStock.setCount(sourceStockAtStockManagementDTO.getSalesAccountProductStockCount());

                Product product = productLinkedMachineRepository.findByMachineIdRetuonProduct(machine.getId());
                salesAccountProductStock.setProduct(product);
                salesAccountProductStock.setSalesAccountMachine(salesAccountMachine);
                salesAccountProductStockRepository.save(salesAccountProductStock);

                salesAccountMachine.setSalesAccountProductStock(salesAccountProductStock);
            }

            salesAccountMachine.setSalesAccountMachineStock(salesAccountMachineStock);
            salesAccountMachine = salesAccountMachineRepository.save(salesAccountMachine);

            return salesAccountMachine;
        } else {
            SalesAccountMachineStock salesAccountMachineStock = salesAccountMachineStockRepository.findById(sourceStockAtStockManagementDTO.getId()).get();
            salesAccountMachineStock.setCount(sourceStockAtStockManagementDTO.getStockCount());
            salesAccountMachineStockRepository.save(salesAccountMachineStock);

            Machine machine = salesAccountMachineStock.getSalesAccountMachine().getMachine();

            if (machine.getIsProductMachine() || machine.getIsCoinChanger()) {
                SalesAccountProductStock salesAccountProductStock = salesAccountMachineStock.getSalesAccountMachine().getSalesAccountProductStock();
                salesAccountProductStock.setCount(sourceStockAtStockManagementDTO.getSalesAccountProductStockCount());
                salesAccountProductStockRepository.save(salesAccountProductStock);
            }
            return salesAccountMachineStock.getSalesAccountMachine();
        }
    }

    @Autowired
    CompanyStockHistoryRepository companyStockHistoryRepository;

    @Autowired
    EmployeeStockHistoryRepository employeeStockHistoryRepository;

    private void saveCompanyProductStockHistory(CompanyProductStock companyProductStock, int supplyCount, LocalDate date) {
        CompanyStockHistory companyStockHistory = new CompanyStockHistory();
        companyStockHistory.setSendProductCount(supplyCount);
        companyStockHistory.setCompanyProductStock(companyProductStock);
        companyStockHistory.setDate(date);

        companyStockHistoryRepository.save(companyStockHistory);
    }

    private void saveCompanyMachineStockHistory(CompanyMachineStock companyMachineStock, int supplyCount, LocalDate date) {
        CompanyStockHistory companyStockHistory = new CompanyStockHistory();
        companyStockHistory.setSendMachineCount(supplyCount);
        companyStockHistory.setCompanyMachineStock(companyMachineStock);
        companyStockHistory.setDate(date);
        companyStockHistoryRepository.save(companyStockHistory);
    }


    private void saveEmployeeProductStockHistory(EmployeeProductStock employeeProductStock, int supplyCount, LocalDate date) {
        EmployeeStockHistory employeeStockHistory = new EmployeeStockHistory();
        employeeStockHistory.setSendProductCount(supplyCount);
        employeeStockHistory.setEmployeeProductStock(employeeProductStock);
        employeeStockHistory.setDate(date);
        employeeStockHistory.setIsCompanySendStock(false);

        employeeStockHistoryRepository.save(employeeStockHistory);
    }

    private void saveEmployeeMachineStockHistory(EmployeeMachineStock employeeMachineStock, int supplyCount, LocalDate date) {
        EmployeeStockHistory employeeStockHistory = new EmployeeStockHistory();
        employeeStockHistory.setSendMachineCount(supplyCount);
        employeeStockHistory.setEmployeeMachineStock(employeeMachineStock);
        employeeStockHistory.setDate(date);
        employeeStockHistory.setIsCompanySendStock(false);

        employeeStockHistoryRepository.save(employeeStockHistory);
    }

    private BusinessJournalHistory saveSalesAccountStockHistory(BusinessJournalHistoryList businessJournalHistoryList, SalesAccountMachine salesAccountMachine, int supplyCount, int machineCount) {
        BusinessJournalHistory businessJournalHistory = new BusinessJournalHistory();
        if (salesAccountMachine.getMachine().getIsProductMachine() || salesAccountMachine.getMachine().getIsCoinChanger()) {
            businessJournalHistory.setSupplyCount(supplyCount);

            SalesAccountProductStock salesAccountProductStock = salesAccountMachine.getSalesAccountProductStock();
            businessJournalHistory.setProduct(salesAccountProductStock.getProduct());
        }

        businessJournalHistory.setMachineCount(machineCount);
        businessJournalHistory.setSalesAccountMachine(salesAccountMachine);
        businessJournalHistory.setBusinessJournalHistoryList(businessJournalHistoryList);
        businessJournalHistory.setType("재고 관리");

        return businessJournalHistoryRepository.save(businessJournalHistory);
    }

    @Autowired
    private BusinessJournalHistoryRepository businessJournalHistoryRepository;
    @Autowired
    private BusinessJournalHistoryListRepository businessJournalHistoryListRepository;

    @Transactional
    public void saveStock(int type, List<SourceStockAtStockManagementDTO> stockAtStockManagementDTOList, LocalDate date) {
        BusinessJournalHistoryList businessJournalHistoryList = null;
        List<BusinessJournalHistory> businessJournalHistories = null;

        if (type == 2 || type == 3) {
            businessJournalHistories = new ArrayList<>();
            businessJournalHistoryList = new BusinessJournalHistoryList();
            businessJournalHistoryList.setType("재고 관리");
            businessJournalHistoryList.setDate(date);
        }

        for (SourceStockAtStockManagementDTO sourceStockAtStockManagementDTO : stockAtStockManagementDTOList) {
            if (!sourceStockAtStockManagementDTO.getChanged())
                continue;

            switch (type) {
                case 0:
                    saveCompanyStock(sourceStockAtStockManagementDTO, date);
                    break;
                case 1:
                    saveEmployeeStock(sourceStockAtStockManagementDTO, date);
                    break;
                case 2:
                    SalesAccountMachine salesAccountMachine = saveSalesAccountStock(sourceStockAtStockManagementDTO);
                    businessJournalHistoryList.setSalesAccount(salesAccountMachine.getSalesAccount());
                    businessJournalHistoryList = businessJournalHistoryListRepository.save(businessJournalHistoryList);

                    int machineCount = sourceStockAtStockManagementDTO.getStockCount() - sourceStockAtStockManagementDTO.getPreviousStockCount();

                    boolean isProductMachineOrCoinChanger = salesAccountMachine.getMachine().getIsProductMachine() || salesAccountMachine.getMachine().getIsCoinChanger();

                    int supplyCount = isProductMachineOrCoinChanger ? sourceStockAtStockManagementDTO.getSalesAccountProductStockCount() - sourceStockAtStockManagementDTO.getPreviousSalesAccountProductStockCount() : 0;
                    BusinessJournalHistory businessJournalHistory = saveSalesAccountStockHistory(businessJournalHistoryList, salesAccountMachine, supplyCount, machineCount);
                    businessJournalHistories.add(businessJournalHistory);
                    break;
            }
        }
    }
}
