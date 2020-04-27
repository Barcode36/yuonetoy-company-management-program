package com.yuonetoy.Service;

import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountDTO;
import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountDetailDTO;
import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountPreviewDTO;
import com.yuonetoy.Entitiy.Account.PurchaseAccount;
import com.yuonetoy.Repository.Account.PurchaseAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseAccountService {
    @Autowired
    private PurchaseAccountRepository purchaseAccountRepository;
    @Autowired
    private ModelMapper modelMapper;
    
    public PurchaseAccountPreviewDTO addPurchaseAccount(String name, String representative, String address,
                                              String ph, LocalDate entryDate, String uptea,
                                              String jongmok, String companyNum){

        PurchaseAccountDetailDTO purchaseAccountDetailDTO = new PurchaseAccountDetailDTO(null, name, representative, address, ph, entryDate, uptea, jongmok, companyNum);

        PurchaseAccount purchaseAccount = modelMapper.map(purchaseAccountDetailDTO, PurchaseAccount.class);
        purchaseAccountRepository.save(purchaseAccount);

        PurchaseAccountPreviewDTO purchaseAccountPreviewDTO = modelMapper.map(purchaseAccount, PurchaseAccountPreviewDTO.class);

        return purchaseAccountPreviewDTO;
    }

    public List<PurchaseAccountDTO> readPurchaseAccount(){
        List<PurchaseAccount> purchaseAccountList = purchaseAccountRepository.findAll();
        List<PurchaseAccountDTO> purchaseAccountDTOList = new ArrayList<PurchaseAccountDTO>();

        purchaseAccountList.forEach(purchaseAccount -> {
            PurchaseAccountDTO purchaseAccountDTO = modelMapper.map(purchaseAccount, PurchaseAccountDTO.class);
            purchaseAccountDTOList.add(purchaseAccountDTO);
        });
        return purchaseAccountDTOList;
    }

    public PurchaseAccountPreviewDTO updatePurchaseAccount(PurchaseAccountDetailDTO purchaseAccountDetailDTO, String name, String representative, String address,
                                                 String ph, LocalDate entryDate, String uptea,
                                                 String jongmok, String companyNum){

        PurchaseAccount purchaseAccount = purchaseAccountRepository.findById(purchaseAccountDetailDTO.getId()).get();

        purchaseAccount.update(name, representative, address, ph, entryDate, uptea, jongmok, companyNum);
        purchaseAccountRepository.save(purchaseAccount);

        PurchaseAccountPreviewDTO purchaseAccountPreviewDTO = modelMapper.map(purchaseAccount, PurchaseAccountPreviewDTO.class);
        return purchaseAccountPreviewDTO;
    }

    public PurchaseAccountDetailDTO findPurchaseAccountDetailDTO(PurchaseAccountPreviewDTO purchaseAccountPreviewDTO){
        PurchaseAccount purchaseAccount = purchaseAccountRepository.findById(purchaseAccountPreviewDTO.getId()).get();
        PurchaseAccountDetailDTO purchaseAccountDetailDTO = modelMapper.map(purchaseAccount, PurchaseAccountDetailDTO.class);

        return purchaseAccountDetailDTO;
    }
}
