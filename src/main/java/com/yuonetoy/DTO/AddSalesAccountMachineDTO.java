package com.yuonetoy.DTO;


import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountPreviewDTO;
import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.Machine;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountMachineStock;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountProductStock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddSalesAccountMachineDTO {
    private SalesAccount salesAccount;
    private Machine machine;
    private Integer machineCount;
    private Integer initialQuantity;
    private Double fees;
    private Integer plusValue;
    private Integer minusValue;
}
