
package com.yuonetoy.Service;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.Area.SalesAccountAreaDTO;
import com.yuonetoy.DTO.CompanyType.CompanyTypeDTO;
import com.yuonetoy.DTO.CompanyType.SecondCompanyTypeDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.SalesAccountMonthSalesDTO;
import com.yuonetoy.DTO.SalesAccountTotalSalesDTO;
import com.yuonetoy.Repository.SalesAccountMachineSalesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Service
public class SalesManagementService {
    @Autowired
    SalesAccountMachineSalesRepository salesAccountMachineSalesRepository;
    @Autowired
    ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<SalesAccountTotalSalesDTO> getSalesAccountSales(boolean isTotalSales, LocalDate startDate, LocalDate lastDate) {
        List<SalesAccountTotalSalesDTO> salesAccountTotalSalesDTOList;

        if (isTotalSales) {
            salesAccountTotalSalesDTOList = salesAccountMachineSalesRepository.findAllSalesAccountTotalSales();
        } else {
            salesAccountTotalSalesDTOList = salesAccountMachineSalesRepository.findAllSalesAccountMachineSales(startDate, lastDate);
        }

        return salesAccountTotalSalesDTOList;
    }

    @Transactional(readOnly = true)
    public List<SalesAccountMonthSalesDTO> getSalesAccountSales(LocalDate startDate, LocalDate lastDate) {
        Hashtable<Integer, SalesAccountMonthSalesDTO> salesAccountMonthSalesDTOHashtable = new Hashtable<>();

        List<SalesAccountTotalSalesDTO> salesAccountTotalSalesDTOS = salesAccountMachineSalesRepository.findAllSalesAccountMonthTotalSales(startDate, lastDate);
        salesAccountTotalSalesDTOS.forEach(salesAccountTotalSalesDTO -> {
            int sa_hash = salesAccountTotalSalesDTO.getSalesAccount().hashCode();
            int month = salesAccountTotalSalesDTO.getDate().getMonthValue() - 1;
            long sales = salesAccountTotalSalesDTO.getSales();

            if (salesAccountMonthSalesDTOHashtable.containsKey(sa_hash))
                salesAccountMonthSalesDTOHashtable.get(sa_hash).setSales(month, salesAccountMonthSalesDTOHashtable.get(sa_hash).getSales(month) + sales);
            else {
                SalesAccountMonthSalesDTO salesAccountMonthSalesDTO = modelMapper.map(salesAccountTotalSalesDTO, SalesAccountMonthSalesDTO.class);
                salesAccountMonthSalesDTO.setSales(month, salesAccountTotalSalesDTO.getBalance());
                salesAccountMonthSalesDTOHashtable.put(sa_hash, salesAccountMonthSalesDTO);
            }
        });

        List<SalesAccountMonthSalesDTO> salesAccountMonthSalesDTOList = new ArrayList<>();
        salesAccountMonthSalesDTOList.addAll(salesAccountMonthSalesDTOHashtable.values());
        return salesAccountMonthSalesDTOList;
    }
}
