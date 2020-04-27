package com.yuonetoy.Service;

import com.yuonetoy.DTO.Account.TaxAccount.TaxAccountDTO;
import com.yuonetoy.DTO.Account.TaxAccount.TaxAccountDetailDTO;
import com.yuonetoy.DTO.Account.TaxAccount.TaxAccountPreviewDTO;
import com.yuonetoy.DTO.Area.EmployeeAreaDTO;
import com.yuonetoy.Entitiy.Account.TaxAccount;
import com.yuonetoy.Entitiy.Area.EmployeeArea;
import com.yuonetoy.Repository.Account.TaxAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class TaxAccountService {
    @Autowired
    private TaxAccountRepository taxAccountRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<TaxAccountDTO> findTaxAccount(String searchText) {
        List<TaxAccount> taxAccounts = taxAccountRepository.findByNameContaining(searchText);
        List<TaxAccountDTO> taxAccountDTOList = new LinkedList<>();

        taxAccounts.forEach(taxAccount -> {
            TaxAccountDTO taxAccountDTO = modelMapper.map(taxAccount, TaxAccountDTO.class);
            taxAccountDTOList.add(taxAccountDTO);
        });

        return taxAccountDTOList;
    }

    public TaxAccountPreviewDTO addTaxAccount(String name, String representative, String address,
                                             String ph, LocalDate entryDate, String uptea,
                                             String jongmok, String companyNum, String managerName,
                                             String managerEmail, String managerPh  ){

        TaxAccountDetailDTO taxAccountDetailDTO = new TaxAccountDetailDTO(null, name, representative, address, ph, entryDate, uptea, jongmok, companyNum, managerName, managerEmail, managerPh);

        TaxAccount taxAccount = modelMapper.map(taxAccountDetailDTO, TaxAccount.class);
        taxAccountRepository.save(taxAccount);

        TaxAccountPreviewDTO taxAccountPreviewDTO = modelMapper.map(taxAccount, TaxAccountPreviewDTO.class);

        return taxAccountPreviewDTO;
    }

    public TaxAccountPreviewDTO updateTaxAccount(TaxAccountDetailDTO taxAccountDetailDTO, String name, String representative, String address,
                                             String ph, LocalDate entryDate, String uptea,
                                             String jongmok, String companyNum, String managerName,
                                             String managerEmail, String managerPh  ){

        TaxAccount taxAccount = taxAccountRepository.findById(taxAccountDetailDTO.getId()).get();

        taxAccount.update(name, representative, address, ph, entryDate, uptea, jongmok, companyNum, managerName, managerEmail, managerPh);
        taxAccountRepository.save(taxAccount);

        TaxAccountPreviewDTO taxAccountPreviewDTO = modelMapper.map(taxAccount, TaxAccountPreviewDTO.class);
        return taxAccountPreviewDTO;
    }

    public TaxAccountDetailDTO findTaxAccountDetailDTO(TaxAccountPreviewDTO taxAccountPreviewDTO){
        TaxAccount taxAccount = taxAccountRepository.findById(taxAccountPreviewDTO.getId()).get();
        TaxAccountDetailDTO taxAccountDetailDTO = modelMapper.map(taxAccount, TaxAccountDetailDTO.class);

        return taxAccountDetailDTO;
    }


    public TaxAccount toTaxAccount(TaxAccountDTO taxAccountDTO){
        return taxAccountRepository.findById(taxAccountDTO.getId()).get();
    }
}
