package com.yuonetoy.Service;

import com.yuonetoy.DTO.Area.SalesAccountAreaDTO;
import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.Area.SalesAccountArea;
import com.yuonetoy.Repository.Area.SalesAccountAreaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesAccountAreaService {
    @Autowired
    private SalesAccountAreaRepository salesAccountAreaRepository;
    @Autowired
    private ModelMapper modelMapper;

    public SalesAccountAreaDTO addSalesAccountArea(String name) {
        SalesAccountArea salesAccountArea = new SalesAccountArea();
        salesAccountArea.setName(name);
        salesAccountAreaRepository.save(salesAccountArea);

        SalesAccountAreaDTO salesAccountAreaDTO = modelMapper.map(salesAccountArea, SalesAccountAreaDTO.class);

        return salesAccountAreaDTO;
    }

    public void deleteSalesAccountArea(SalesAccountAreaDTO salesAccountAreaDTO) {
        SalesAccountArea SalesAccountArea = findSalesAccountArea(salesAccountAreaDTO);
        salesAccountAreaRepository.delete(SalesAccountArea);
    }

    public List<SalesAccountAreaDTO> readSalesAccountArea() {
        List<SalesAccountArea> SalesAccountAreas = salesAccountAreaRepository.findAll();
        List<SalesAccountAreaDTO> salesAccountAreaDTOList = new ArrayList<>();

        SalesAccountAreas.forEach(salesAccountArea -> {
            SalesAccountAreaDTO salesAccountAreaDTO = modelMapper.map(salesAccountArea, SalesAccountAreaDTO.class);
            salesAccountAreaDTOList.add(salesAccountAreaDTO);
        });

        return salesAccountAreaDTOList;
    }

    public SalesAccountArea findSalesAccountArea(SalesAccountAreaDTO salesAccountAreaDTO) {
        return salesAccountAreaRepository.findById(salesAccountAreaDTO.getId()).get();
    }

    public SalesAccountAreaDTO update(SalesAccountAreaDTO salesAccountAreaDTO, String name) {
        SalesAccountArea salesAccountArea = findSalesAccountArea(salesAccountAreaDTO);
        salesAccountAreaDTO.setName(name);
        salesAccountArea.update(name);
        salesAccountAreaRepository.save(salesAccountArea);

        return salesAccountAreaDTO;
    }

    public SalesAccountAreaDTO toSalesAccountAreaDTO(SalesAccountArea salesAccountArea){
        return modelMapper.map(salesAccountArea, SalesAccountAreaDTO.class);
    }

    public SalesAccountArea toSalesAccountArea(SalesAccountAreaDTO salesAccountAreaDTO){
        return salesAccountAreaRepository.findById(salesAccountAreaDTO.getId()).get();
    }
}
