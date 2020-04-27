package com.yuonetoy.Service;

import com.yuonetoy.DTO.Company.CompanyDTO;
import com.yuonetoy.DTO.Company.CompanyDetailDTO;
import com.yuonetoy.DTO.Stock.SourceInfoAtStockManagementDTO;
import com.yuonetoy.Entitiy.Company;
import com.yuonetoy.Repository.CompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CompanyDetailDTO addCompany(String name, String address){
        Company company = new Company();
        company.setName(name);
        company.setAddress(address);
        companyRepository.save(company);

        CompanyDetailDTO companyDetailDTO = modelMapper.map(company, CompanyDetailDTO.class);
        companyDetailDTO.setName(name);
        companyDetailDTO.setAddress(address);
        return companyDetailDTO;
    }

    public void deleteCompany(CompanyDetailDTO companyDetailDTO){
        Company company = companyRepository.findById(companyDetailDTO.getId()).get();
        companyRepository.delete(company);
    }

    public List<CompanyDetailDTO> readCompanyDetail(){
        List<Company> companies = companyRepository.findAll();
        List<CompanyDetailDTO> companyDetailDTOList = new ArrayList<CompanyDetailDTO>();

        companies.forEach(company -> {
            CompanyDetailDTO companyDetailDTO = modelMapper.map(company, CompanyDetailDTO.class);
            companyDetailDTOList.add(companyDetailDTO);
        });

        return companyDetailDTOList;
    }

    public List<CompanyDTO> readCompany(){
        List<Company> companies = companyRepository.findAll();
        List<CompanyDTO> companyDTOList = new ArrayList<CompanyDTO>();

        companies.forEach(company -> {
            CompanyDTO companyDTO = modelMapper.map(company, CompanyDTO.class);
            companyDTOList.add(companyDTO);
        });

        return companyDTOList;
    }

    public List<SourceInfoAtStockManagementDTO> readCompanyAtStockManagementView(){
        List<Company> companies = companyRepository.findAll();
        List<SourceInfoAtStockManagementDTO> sourceInfoAtStockManagementDTOList = new ArrayList<SourceInfoAtStockManagementDTO>();

        companies.forEach(company -> {
            SourceInfoAtStockManagementDTO sourceInfoAtStockManagementDTO = new SourceInfoAtStockManagementDTO();
            sourceInfoAtStockManagementDTO.setId(company.getId());
            sourceInfoAtStockManagementDTO.setName(company.getName());
            sourceInfoAtStockManagementDTO.setAddress(company.getAddress());
            sourceInfoAtStockManagementDTOList.add(sourceInfoAtStockManagementDTO);
        });

        return sourceInfoAtStockManagementDTOList;
    }
}
