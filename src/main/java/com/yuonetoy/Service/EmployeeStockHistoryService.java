package com.yuonetoy.Service;

import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import com.yuonetoy.DTO.Stock.EmployeeStockDTO;
import com.yuonetoy.DTO.StockHistory.EmployeeStockHistoryDTO;
import com.yuonetoy.Entitiy.BusinessJournalHistory;
import com.yuonetoy.Entitiy.Employee;
import com.yuonetoy.Entitiy.Machine;
import com.yuonetoy.Entitiy.Product;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeMachineStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeProductStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeStockHistory;
import com.yuonetoy.Repository.EmployeeRepository;
import com.yuonetoy.Repository.EmployeeStockHistoryRepository;
import com.yuonetoy.Repository.Stock.EmployeeMachineStockRepository;
import com.yuonetoy.Repository.Stock.EmployeeProductStockRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeStockHistoryService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EmployeeProductStockRepository employeeProductStockRepository;

    @Autowired
    EmployeeMachineStockRepository employeeMachineStockRepository;

    @Autowired
    EmployeeStockHistoryRepository employeeStockHistoryRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeStockService employeeStockService;

    public List<EmployeeDTO> getEmployeeList() {
        return employeeService.readEmployeeDTO();
    }

    public EmployeeStockHistory save(EmployeeStockHistory employeeStockHistory) {
        return employeeStockHistoryRepository.save(employeeStockHistory);
    }

    public void saveEmployeeStockHistory(BusinessJournalHistory businessJournalHistory) {
        Employee employee = businessJournalHistory.getBusinessJournalHistoryList().getSalesAccount().getEmployee();
        Product product = businessJournalHistory.getProduct();
        Machine machine = businessJournalHistory.getSalesAccountMachine().getMachine();

        int sendProductCount = businessJournalHistory.getSupplyCount();
        if (product != null) {
            sendProductCount = ((businessJournalHistory.getSupplyCount() + (businessJournalHistory.getMinusValue() * (-500)) + businessJournalHistory.getPlusValue() * 500)) / product.getPrice();
            sendProductCount = -sendProductCount;
        }

        int sendMachineCount = -businessJournalHistory.getMachineCount();
        LocalDate date = businessJournalHistory.getBusinessJournalHistoryList().getDate();
        EmployeeProductStock employeeProductStock = null;
        EmployeeMachineStock employeeMachineStock = null;

        if (businessJournalHistory.getType().hashCode() == "신규 입점".hashCode() ||
                businessJournalHistory.getType().hashCode() == "철수".hashCode() ||
                businessJournalHistory.getType().hashCode() == "기계 보충".hashCode() ||
                businessJournalHistory.getType().hashCode() == "재입점".hashCode()) {
            employeeMachineStock = employeeMachineStockRepository.findByEmployeeIdAndMachineId(employee.getId(), machine.getId());
            if (machine.getIsProductMachine())
                employeeProductStock = employeeProductStockRepository.findByEmployeeIdAndProductId(employee.getId(), product.getId());
        } else if (businessJournalHistory.getType().hashCode() == "기계 관리".hashCode()) {
            employeeMachineStock = employeeMachineStockRepository.findByEmployeeIdAndMachineId(employee.getId(), machine.getId());
        } else if (businessJournalHistory.getType().hashCode() == "상품 보충".hashCode()){
            if (machine.getIsProductMachine() || machine.getIsCoinChanger())
                employeeProductStock = employeeProductStockRepository.findByEmployeeIdAndProductId(employee.getId(), product.getId());
        }

        EmployeeStockHistory employeeStockHistory = new EmployeeStockHistory();
        employeeStockHistory.setIsCompanySendStock(false);
        employeeStockHistory.setBusinessJournalHistory(businessJournalHistory);
        employeeStockHistory.setEmployeeProductStock(employeeProductStock);
        employeeStockHistory.setEmployeeMachineStock(employeeMachineStock);
        employeeStockHistory.setSendMachineCount(sendMachineCount);
        employeeStockHistory.setSendProductCount(sendProductCount);
        employeeStockHistory.setDate(date);

        employeeStockHistory = employeeStockHistoryRepository.save(employeeStockHistory);

        Set<EmployeeStockHistory> employeeStockHistorySet = new HashSet<>();
        employeeStockHistorySet.add(employeeStockHistory);
        businessJournalHistory.setEmployeeStockHistory(employeeStockHistorySet);
    }

    public void updateEmployeeStockHistory(BusinessJournalHistory businessJournalHistory) {
        EmployeeStockHistory employeeStockHistory = businessJournalHistory.getEmployeeStockHistory().iterator().next();
        int sendProductCount = businessJournalHistory.getSupplyCount();
        Product product = businessJournalHistory.getProduct();
        if (product != null) {
            sendProductCount = ((businessJournalHistory.getSupplyCount() + (businessJournalHistory.getMinusValue() * (-500)) + businessJournalHistory.getPlusValue() * 500)) / product.getPrice();
            sendProductCount = -sendProductCount;
        }
        int sendMachineCount = -businessJournalHistory.getMachineCount();

        employeeStockHistory.setSendMachineCount(sendMachineCount);
        employeeStockHistory.setSendProductCount(sendProductCount);

        employeeStockHistoryRepository.save(employeeStockHistory);
    }

    @Transactional(readOnly = true)
    public List<EmployeeStockDTO> getEmployeeStock(EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.getEmployeeStockByemployeeId(employeeDTO.getId());
        List<EmployeeStockDTO> companyStockDTOList = new ArrayList<>();

        employee.getEmployeeProductStocks().forEach(employeeProductStock -> {
            ProductPriceDTO productPriceDTO = modelMapper.map(employeeProductStock.getProduct(), ProductPriceDTO.class);
            int count = employeeProductStock.getCount();

            EmployeeStockDTO employeeStockDTO = new EmployeeStockDTO(employeeDTO, true, productPriceDTO, null, count);
            companyStockDTOList.add(employeeStockDTO);
        });

        employee.getEmployeeMachineStocks().forEach(employeeMachineStock -> {
            MachineDTO machineDTO = modelMapper.map(employeeMachineStock.getMachine(), MachineDTO.class);
            int count = employeeMachineStock.getCount();

            EmployeeStockDTO employeeStockDTO = new EmployeeStockDTO(employeeDTO, false, null, machineDTO, count);
            companyStockDTOList.add(employeeStockDTO);
        });

        return companyStockDTOList;
    }

    @Transactional(readOnly = true)
    public List<EmployeeStockHistoryDTO> getEmployeeStockMoveHistory(EmployeeStockDTO employeeStockDTO, LocalDate firstDate, LocalDate lastDate) {
        EmployeeDTO employeeDTO = employeeStockDTO.getEmployee();
        ProductPriceDTO productDTO = employeeStockDTO.getProduct();
        MachineDTO machineDTO = employeeStockDTO.getMachine();

        List<EmployeeStockHistory> employeeStockHistoryList = null;

        if (employeeStockDTO.isProduct()) {
            long productId = productDTO.getId();
            employeeStockHistoryList = employeeStockHistoryRepository.findByEmployeeProductStockId(employeeDTO.getId(), productId, firstDate, lastDate);
        } else {
            long machineId = machineDTO.getId();
            employeeStockHistoryList = employeeStockHistoryRepository.findByEmployeeMachineStockId(employeeDTO.getId(), machineId, firstDate, lastDate);
        }

        List<EmployeeStockHistoryDTO> employeeStockHistoryDTOList = new ArrayList<>();

        employeeStockHistoryList.forEach(employeeStockHistory -> {
            EmployeeStockHistoryDTO employeeStockHistoryDTO = new EmployeeStockHistoryDTO();

            if (employeeStockHistory.getIsCompanySendStock()) {
                employeeStockHistoryDTO.setType("상품 출고");
                employeeStockHistoryDTO.setTargetName(employeeStockHistory.getSendStockHistory().getSourceCompany().getName());
            } else if (employeeStockHistory.getBusinessJournalHistory() != null){
                employeeStockHistoryDTO.setType(employeeStockHistory.getBusinessJournalHistory().getType());
                employeeStockHistoryDTO.setTargetName(employeeStockHistory.getBusinessJournalHistory().getSalesAccountMachine().getSalesAccount().getName());
            }else if (employeeStockHistory.getBusinessJournalHistory() == null && employeeStockHistory.getSendStockHistory() == null){
                employeeStockHistoryDTO.setType("재고 관리");
                employeeStockHistoryDTO.setTargetName("관리자");
            }

            if (employeeStockDTO.isProduct()) {
                employeeStockHistoryDTO.setEmployee(employeeStockHistory.getEmployeeProductStock().getEmployee().getName());
                employeeStockHistoryDTO.setStockName(employeeStockHistory.getEmployeeProductStock().getProduct().getName());
                employeeStockHistoryDTO.setStockCount((long) employeeStockHistory.getEmployeeProductStock().getCount());
                employeeStockHistoryDTO.setSendCount(employeeStockHistory.getSendProductCount());
                employeeStockHistoryDTO.setDate(employeeStockHistory.getDate());
            } else {
                employeeStockHistoryDTO.setEmployee(employeeStockHistory.getEmployeeMachineStock().getEmployee().getName());
                employeeStockHistoryDTO.setStockName(employeeStockHistory.getEmployeeMachineStock().getMachine().getName());
                employeeStockHistoryDTO.setStockCount((long) employeeStockHistory.getEmployeeMachineStock().getCount());
                employeeStockHistoryDTO.setSendCount(employeeStockHistory.getSendMachineCount());
                employeeStockHistoryDTO.setDate(employeeStockHistory.getDate());
            }

            employeeStockHistoryDTOList.add(employeeStockHistoryDTO);
        });


        return employeeStockHistoryDTOList;
    }

    public Long getBeforeProductStockCount(long employeeId, long productId, LocalDate firstDate) {
        return employeeStockHistoryRepository.beforeEmployeeProductStock(employeeId, productId, firstDate);
    }

    public Long getBeforeMachineStockCount(long employeeId, long machineId, LocalDate firstDate) {
        return employeeStockHistoryRepository.beforeEmployeeMachineStock(employeeId, machineId, firstDate);
    }
}
