package com.yuonetoy.DTO.CompanyType;

import com.yuonetoy.Entitiy.CompanyType.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class SecondCompanyTypeDTO {
    private Long id;
    private String name;
    private CompanyTypeDTO companyType;

    public SecondCompanyTypeDTO() {
    }

    public SecondCompanyTypeDTO(Long id, String name, CompanyTypeDTO companyType) {
        this.id = id;
        this.name = name;
        this.companyType = companyType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompanyTypeDTO getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyTypeDTO companyType) {
        this.companyType = companyType;
    }

    @Override
    public String toString() {
        return name;
    }
}