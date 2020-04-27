package com.yuonetoy.Service;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDetailDTO;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountPreviewDTO;
import com.yuonetoy.DTO.SalesAccountMachineDetailDTO;
import com.yuonetoy.DTO.SalesAccountRevisionHistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenSalesAccountService {
    @Autowired
    MachineManagementService machineManagementService;

    @Autowired
    SalesAccountService salesAccountService;

    public SalesAccountDetailDTO findSalesAccountDetailDTO(SalesAccountPreviewDTO salesAccountPreviewDTO) {
        return salesAccountService.findSalesAccountDetailDTO(salesAccountPreviewDTO);
    }
}
