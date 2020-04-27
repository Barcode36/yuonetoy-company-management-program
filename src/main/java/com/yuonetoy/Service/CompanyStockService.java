package com.yuonetoy.Service;

import com.yuonetoy.DTO.Company.CompanyDTO;
import com.yuonetoy.DTO.Stock.CompanyStockDTO;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyMachineStock;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyProductStock;
import com.yuonetoy.Repository.CompanyStockHistoryRepository;
import com.yuonetoy.Repository.Stock.CompanyMachineStockRepository;
import com.yuonetoy.Repository.Stock.CompanyProductStockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyStockService {
    @Autowired
    private CompanyStockHistoryRepository companyStockHistoryRepository;
    @Autowired
    private CompanyProductStockRepository companyProductStockRepository;
    @Autowired
    private CompanyMachineStockRepository companyMachineStockRepository;

    public void updateCompanyProductStock(long companyProductStockId) {
        CompanyProductStock companyProductStock = companyProductStockRepository.findById(companyProductStockId).get();
        Long sumProductCount = companyStockHistoryRepository.getCompanyProductStock(companyProductStock.getId());

        if (sumProductCount == null )
            sumProductCount = (long) 0;


        companyProductStock.setCount(sumProductCount.intValue());
        companyProductStockRepository.save(companyProductStock);
    }

    public void updateCompanyMachineStock(long companyMachineStockId) {
        CompanyMachineStock companyMachineStock = companyMachineStockRepository.findById(companyMachineStockId).get();
        Long sumMachineCount = companyStockHistoryRepository.getCompanyMachineStock(companyMachineStock.getId());

        if (sumMachineCount == null )
            sumMachineCount = (long) 0;

        companyMachineStock.setCount(sumMachineCount.intValue());
        companyMachineStockRepository.save(companyMachineStock);
    }
}
