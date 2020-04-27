package com.yuonetoy.Service;

import com.yuonetoy.DTO.BusinessJournalHistoryDTO;
import com.yuonetoy.DTO.SalesAccountMachineDTO;
import com.yuonetoy.DTO.SalesAccountMachineDetailDTO;
import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.BusinessJournalHistory;
import com.yuonetoy.Entitiy.Machine;
import com.yuonetoy.Entitiy.Product;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachine;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachineSales;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountMachineStock;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountProductStock;
import com.yuonetoy.Repository.Account.SalesAccountRepository;
import com.yuonetoy.Repository.BusinessJournalHistoryRepository;
import com.yuonetoy.Repository.SalesAccountMachineRepository;
import com.yuonetoy.Repository.SalesAccountMachineSalesRepository;
import com.yuonetoy.Repository.Stock.SalesAccountMachineStockRepository;
import com.yuonetoy.Repository.Stock.SalesAccountProductStockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
public class SalesAccountMachineSalesService {
    @Autowired
    private BusinessJournalHistoryRepository businessJournalHistoryRepository;
    @Autowired
    private SalesAccountMachineSalesRepository salesAccountMachineSalesRepository;

    public void saveSalesInfo(BusinessJournalHistory businessJournalHistory) {
        //매출 기록이 없을때
        long salesAccountMachineId = businessJournalHistory.getSalesAccountMachine().getId();
        LocalDate date = businessJournalHistory.getBusinessJournalHistoryList().getDate();
        long salesValue = businessJournalHistory.getSalesValue();
        long refundValue = businessJournalHistory.getRefundValue();
        Double fees = businessJournalHistoryRepository.getFees(salesAccountMachineId, date);
        fees = (fees == null) ? 0 : fees;

        SalesAccountMachineSales salesAccountMachineSales;

        salesAccountMachineSales = new SalesAccountMachineSales();
        salesAccountMachineSales.setSalesAccountMachine(businessJournalHistory.getSalesAccountMachine());
        salesAccountMachineSales.setBusinessJournalHistory(businessJournalHistory);
        salesAccountMachineSales.setDate(date);
        salesAccountMachineSales.setDepositAmount((long) 0);
        salesAccountMachineSales.setConfirmationOfPayment(false);
        salesAccountMachineSales.setSales(salesValue);
        salesAccountMachineSales.setRefundAmount(refundValue);
        salesAccountMachineSales.setBalance((long) (salesValue * (1 - (fees / 100))));

        salesAccountMachineSalesRepository.save(salesAccountMachineSales);
    }

    public void updateSalesInfo(BusinessJournalHistory businessJournalHistory) {
        //매출 기록이 없을때
        long salesAccountMachineId = businessJournalHistory.getSalesAccountMachine().getId();
        LocalDate date = businessJournalHistory.getBusinessJournalHistoryList().getDate();
        long salesValue = businessJournalHistory.getSalesValue();
        long refundValue = businessJournalHistory.getRefundValue();
        Double fees = businessJournalHistoryRepository.getFees(salesAccountMachineId, date);
        fees = (fees == null) ? 0 : fees;

        SalesAccountMachineSales salesAccountMachineSales;

        // 무조건 하나
        salesAccountMachineSales = businessJournalHistory.getSalesAccountMachineSales().iterator().next();
        salesAccountMachineSales.setDate(date);
        salesAccountMachineSales.setSales(salesValue);
        salesAccountMachineSales.setRefundAmount(refundValue);
        salesAccountMachineSales.setBalance((long) (salesValue * (1 - (fees / 100))));

        salesAccountMachineSalesRepository.save(salesAccountMachineSales);
    }
}
