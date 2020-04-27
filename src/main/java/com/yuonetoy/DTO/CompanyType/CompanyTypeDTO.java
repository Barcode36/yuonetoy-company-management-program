package com.yuonetoy.DTO.CompanyType;

import com.yuonetoy.Entitiy.CompanyType.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyTypeDTO {
    private Long id;
    private String name;

    private List<SecondCompanyTypeDTO> secondCompanyTypeDTO;

    @Override
    public String toString() {
        return name;
    }
}
