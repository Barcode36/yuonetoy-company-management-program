package com.yuonetoy.Service;


import com.yuonetoy.DTO.CompanyType.CompanyTypeDTO;
import com.yuonetoy.DTO.CompanyType.SecondCompanyTypeDTO;
import com.yuonetoy.Entitiy.Company;
import com.yuonetoy.Entitiy.CompanyType.CompanyType;


import com.yuonetoy.Entitiy.CompanyType.SecondCompanyType;
import com.yuonetoy.Repository.CompanyType.CompanyTypeRepository;
import com.yuonetoy.Repository.CompanyType.SecondCompanyTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyTypeService {
    @Autowired
    private CompanyTypeRepository companyTypeRepository;
    @Autowired
    private SecondCompanyTypeRepository secondCompanyTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CompanyTypeDTO addCompanyType(String name) {
        CompanyType companyType = new CompanyType();
        companyType.setName(name);
        companyTypeRepository.save(companyType);

        CompanyTypeDTO companyTypeDTO = modelMapper.map(companyType, CompanyTypeDTO.class);
        return companyTypeDTO;
    }

    public SecondCompanyTypeDTO addSecondCompanyType(String name, CompanyTypeDTO companyTypeDTO) {
        CompanyType companyType = modelMapper.map(companyTypeDTO, CompanyType.class);

        SecondCompanyType secondCompanyType = new SecondCompanyType();
        secondCompanyType.setName(name);
        secondCompanyType.setCompanyType(companyType);
        secondCompanyTypeRepository.save(secondCompanyType);

        SecondCompanyTypeDTO secondCompanyTypeDTO = modelMapper.map(secondCompanyType, SecondCompanyTypeDTO.class);

        return secondCompanyTypeDTO;
    }

    public CompanyType findCompanyType(CompanyTypeDTO companyTypeDTO){
        return companyTypeRepository.findById(companyTypeDTO.getId()).get();
    }

    public void deleteCompanyType(CompanyTypeDTO companyTypeDTO) {
        CompanyType companyType = companyTypeRepository.findById(companyTypeDTO.getId()).get();
        companyTypeRepository.delete(companyType);
    }

    public void deleteSecondCompanyType(SecondCompanyTypeDTO secondCompanyTypeDTO) {
        SecondCompanyType secondCompanyType = secondCompanyTypeRepository.findById(secondCompanyTypeDTO.getId()).get();
        secondCompanyTypeRepository.delete(secondCompanyType);
    }

    public List<CompanyTypeDTO> readCompanyType() {
        List<CompanyType> companyTypes = companyTypeRepository.findAll();
        List<CompanyTypeDTO> companyTypeDTOList = new ArrayList<CompanyTypeDTO>();

        companyTypes.forEach(companyType -> {
            CompanyTypeDTO companyTypeDTO = modelMapper.map(companyType, CompanyTypeDTO.class);
            companyTypeDTOList.add(companyTypeDTO);
        });

        return companyTypeDTOList;
    }

    @Transactional(readOnly = true)
    public List<SecondCompanyTypeDTO> readSecondCompanyType(CompanyTypeDTO companyTypeDTO) {
        CompanyType companyType = findCompanyType(companyTypeDTO);
        List<SecondCompanyType> secondCompanyTypeList = secondCompanyTypeRepository.findByCompanyType(companyType);
        List<SecondCompanyTypeDTO> secondCompanyTypeDTOList = new ArrayList<SecondCompanyTypeDTO>();

        secondCompanyTypeList.forEach(secondCompanyType -> {
            SecondCompanyTypeDTO secondCompanyTypeDTO = modelMapper.map(secondCompanyType, SecondCompanyTypeDTO.class);
            secondCompanyTypeDTOList.add(secondCompanyTypeDTO);
        });

        return secondCompanyTypeDTOList;
    }

    @Transactional(readOnly = true)
    public List<SecondCompanyTypeDTO> readSecondCompanyType( ) {
        List<SecondCompanyType> secondCompanyTypeList = secondCompanyTypeRepository.findAll();
        List<SecondCompanyTypeDTO> secondCompanyTypeDTOList = new ArrayList<SecondCompanyTypeDTO>();

        secondCompanyTypeList.forEach(secondCompanyType -> {
            SecondCompanyTypeDTO secondCompanyTypeDTO = modelMapper.map(secondCompanyType, SecondCompanyTypeDTO.class);
            secondCompanyTypeDTOList.add(secondCompanyTypeDTO);
        });

        return secondCompanyTypeDTOList;
    }


    public CompanyTypeDTO updateCompanyType(CompanyTypeDTO companyTypeDTO, String name) {
        CompanyType companyType = companyTypeRepository.findById(companyTypeDTO.getId()).get();
        companyType.update(name);
        companyTypeRepository.save(companyType);

        companyTypeDTO = modelMapper.map(companyType, CompanyTypeDTO.class);
        return companyTypeDTO;
    }

    public SecondCompanyTypeDTO updateSecondCompanyType(SecondCompanyTypeDTO secondCompanyTypeDTO, String name) {
        SecondCompanyType secondCompanyType = secondCompanyTypeRepository.findById(secondCompanyTypeDTO.getId()).get();
        secondCompanyType.update(name);
        secondCompanyTypeRepository.save(secondCompanyType);

        secondCompanyTypeDTO = modelMapper.map(secondCompanyType, SecondCompanyTypeDTO.class);
        return secondCompanyTypeDTO;
    }

    public CompanyType toCompanyType(CompanyTypeDTO companyTypeDTO){
        return companyTypeRepository.findById(companyTypeDTO.getId()).get();
    }

    public SecondCompanyType toSecondCompanyType(SecondCompanyTypeDTO secondCompanyTypeDTO){
        return secondCompanyTypeRepository.findById(secondCompanyTypeDTO.getId()).get();
    }
}
