package com.yuonetoy.DTO.Account.TaxAccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaxAccountDTO {
    private Long id;
    private String name;
    private String address;

    @Override
    public String toString() {
        return name;
    }
}
