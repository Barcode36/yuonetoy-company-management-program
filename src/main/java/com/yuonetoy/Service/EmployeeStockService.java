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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeStockService {
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

    public void updateEmployeeProductStock(long employeeProductStockId) {
        EmployeeProductStock employeeProductStock = employeeProductStockRepository.findById(employeeProductStockId).get();

        Long sumProductCount = employeeStockHistoryRepository.getEmployeeProductStock(employeeProductStock.getId());
        if (sumProductCount == null )
            sumProductCount = (long) 0;

        employeeProductStock.setCount(sumProductCount.intValue());
        employeeProductStockRepository.save(employeeProductStock);
    }

    public void updateEmployeeMachineStock(long employeeMachineStockId) {
        EmployeeMachineStock employeeMachineStock = employeeMachineStockRepository.findById(employeeMachineStockId).get();

        Long sumMachineCount = employeeStockHistoryRepository.getEmployeeMachineStock(employeeMachineStock.getId());

        if (sumMachineCount == null )
        sumMachineCount = (long) 0;

        employeeMachineStock.setCount(sumMachineCount.intValue());
        employeeMachineStockRepository.save(employeeMachineStock);
    }
}
