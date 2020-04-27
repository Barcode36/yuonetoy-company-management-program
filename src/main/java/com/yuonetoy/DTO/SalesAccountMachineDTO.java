package com.yuonetoy.DTO;


import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountPreviewDTO;
import com.yuonetoy.Entitiy.Machine;
import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SalesAccountMachineDTO {
    private Long id;
    private SalesAccountPreviewDTO salesAccount;
    private MachineDTO machine;

    @Override
    public String toString() {
        return machine.getName();
    }
}
