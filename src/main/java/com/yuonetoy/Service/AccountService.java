package com.yuonetoy.Service;

import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountPreviewDTO;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountPreviewDTO;
import com.yuonetoy.DTO.Account.TaxAccount.TaxAccountPreviewDTO;
import com.yuonetoy.DTO.Area.SalesAccountAreaDTO;
import com.yuonetoy.DTO.CompanyType.CompanyTypeDTO;
import com.yuonetoy.DTO.CompanyType.SecondCompanyTypeDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.Entitiy.Account.PurchaseAccount;
import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.Account.TaxAccount;
import com.yuonetoy.Entitiy.Machine;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachine;
import com.yuonetoy.Model.TotalCoinChangerInfo;
import com.yuonetoy.Model.TotalMachineInfo;
import com.yuonetoy.Repository.Account.PurchaseAccountRepository;
import com.yuonetoy.Repository.Account.SalesAccountRepository;
import com.yuonetoy.Repository.Account.TaxAccountRepository;
import com.yuonetoy.Repository.SalesAccountMachineRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private SalesAccountRepository salesAccountRepository;
    @Autowired
    private PurchaseAccountRepository purchaseAccountRepository;
    @Autowired
    private TaxAccountRepository taxAccountRepository;
    @Autowired
    private SalesAccountMachineRepository salesAccountMachineRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<SalesAccountPreviewDTO> readSalesAccount() {
        return salesAccountRepository.findAllSalesAccountPreviewDTO();
    }

    public List<PurchaseAccountPreviewDTO> readPurchaseAccount() {
        List<PurchaseAccount> purchaseAccounts = purchaseAccountRepository.findAll();
        List<PurchaseAccountPreviewDTO> purchaseAccountPreviewDTOList = new ArrayList<PurchaseAccountPreviewDTO>();

        purchaseAccounts.forEach(purchaseAccount -> {
            PurchaseAccountPreviewDTO purchaseAccountPreviewDTO = modelMapper.map(purchaseAccount, PurchaseAccountPreviewDTO.class);
            purchaseAccountPreviewDTOList.add(purchaseAccountPreviewDTO);
        });

        return purchaseAccountPreviewDTOList;
    }

    public List<TaxAccountPreviewDTO> readTaxAccount() {
        List<TaxAccount> taxAccounts = taxAccountRepository.findAll();
        List<TaxAccountPreviewDTO> taxAccountPreviewDTOList = new ArrayList<TaxAccountPreviewDTO>();

        taxAccounts.forEach(taxAccount -> {
            TaxAccountPreviewDTO taxAccountPreviewDTO = modelMapper.map(taxAccount, TaxAccountPreviewDTO.class);
            taxAccountPreviewDTOList.add(taxAccountPreviewDTO);
        });

        return taxAccountPreviewDTOList;
    }


    public List<TotalMachineInfo> getTotalMachine(){
        List<TotalMachineInfo> totalMachineInfos = salesAccountMachineRepository.getTotalMachineInfo();
        return totalMachineInfos;
    }

    public List<TotalMachineInfo> getTotalMachine(List<Long> salesAccountId){
        List<TotalMachineInfo> totalMachineInfos = salesAccountMachineRepository.getTotalMachineInfo(salesAccountId);
        return totalMachineInfos;
    }

    public List<TotalCoinChangerInfo> getTotalCoinChanger(List<Long> salesAccountId){
        List<TotalCoinChangerInfo> totalCoinChangerInfos = salesAccountMachineRepository.getTotalCoinChangerInfo(salesAccountId);
        return totalCoinChangerInfos;
    }

    public List<TotalCoinChangerInfo> getTotalCoinChanger(){
        List<TotalCoinChangerInfo> totalCoinChangerInfos = salesAccountMachineRepository.getTotalCoinChangerInfo();
        return totalCoinChangerInfos;
    }
}
