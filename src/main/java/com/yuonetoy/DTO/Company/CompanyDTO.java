package com.yuonetoy.DTO.Company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
