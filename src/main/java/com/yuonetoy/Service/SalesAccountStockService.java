package com.yuonetoy.Service;

import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeMachineStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeProductStock;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountMachineStock;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountProductStock;
import com.yuonetoy.Repository.EmployeeRepository;
import com.yuonetoy.Repository.EmployeeStockHistoryRepository;
import com.yuonetoy.Repository.Stock.EmployeeMachineStockRepository;
import com.yuonetoy.Repository.Stock.EmployeeProductStockRepository;
import com.yuonetoy.Repository.Stock.SalesAccountMachineStockRepository;
import com.yuonetoy.Repository.Stock.SalesAccountProductStockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SalesAccountStockService {
    @Autowired
    private SalesAccountMachineStockRepository salesAccountMachineStockRepository;
    @Autowired
    private SalesAccountProductStockRepository salesAccountProductStockRepository;

    public void updateSalesAccountProductStock(long salesAccountMachineId) {
        SalesAccountProductStock salesAccountProductStock = salesAccountProductStockRepository.findBySalesAccountMachineId(salesAccountMachineId);
        if (salesAccountProductStock == null)
            return;
        Long sumProductCount = salesAccountProductStockRepository.getTotalProductStock(salesAccountMachineId);

        if (sumProductCount == null)
            sumProductCount = (long) 0;

        salesAccountProductStock.setCount(sumProductCount.intValue());
        salesAccountProductStockRepository.save(salesAccountProductStock);
    }

    public void updateSalesAccountMachineStock(long salesAccountMachineId) {
        SalesAccountMachineStock salesAccountMachineStock = salesAccountMachineStockRepository.findBySalesAccountMachineId(salesAccountMachineId);
        if (salesAccountMachineStock == null)
            return;
        Long sumMachineCount = salesAccountMachineStockRepository.getTotalMachineStock(salesAccountMachineId);
        if (sumMachineCount == null)
            sumMachineCount = (long) 0;

        salesAccountMachineStock.setCount(sumMachineCount.intValue());
        salesAccountMachineStockRepository.save(salesAccountMachineStock);
    }
}
