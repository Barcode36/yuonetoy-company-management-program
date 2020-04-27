package com.yuonetoy.Service;


import com.yuonetoy.DTO.Area.EmployeeAreaDTO;
import com.yuonetoy.Entitiy.Area.EmployeeArea;

import com.yuonetoy.Repository.Area.EmployeeAreaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeAreaService {
    @Autowired
    private EmployeeAreaRepository employeeAreaRepository;
    @Autowired
    private ModelMapper modelMapper;

    public EmployeeAreaDTO addEmployeeArea(String name) {
        EmployeeArea employeeArea = new EmployeeArea();
        employeeArea.setName(name);
        employeeAreaRepository.save(employeeArea);

        EmployeeAreaDTO employeeAreaDTO = modelMapper.map(employeeArea, EmployeeAreaDTO.class);

        return employeeAreaDTO;
    }

    public void deleteEmployeeArea(EmployeeAreaDTO employeeAreaDTO) {
        EmployeeArea employeeArea = findEmployeeArea(employeeAreaDTO);
        employeeAreaRepository.delete(employeeArea);
    }

    public List<EmployeeAreaDTO> readEmployeeArea() {
        List<EmployeeArea> employeeAreas = employeeAreaRepository.findAll();

        List<EmployeeAreaDTO> employeeAreaDTOList = new ArrayList<EmployeeAreaDTO>();

        employeeAreas.forEach(employeeArea -> {
            EmployeeAreaDTO employeeAreaDTO = modelMapper.map(employeeArea, EmployeeAreaDTO.class);
            employeeAreaDTOList.add(employeeAreaDTO);
        });

        return employeeAreaDTOList;
    }

    public EmployeeArea findEmployeeArea(EmployeeAreaDTO employeeAreaDTO) {
        return employeeAreaRepository.findById(employeeAreaDTO.getId()).get();
    }

    public EmployeeAreaDTO update(EmployeeAreaDTO employeeAreaDTO, String name) {
        EmployeeArea employeeArea = findEmployeeArea(employeeAreaDTO);
        employeeAreaDTO.setName(name);
        employeeArea.update(name);
        employeeAreaRepository.save(employeeArea);

        return employeeAreaDTO;
    }

    public EmployeeAreaDTO toEmployeeAreaDTO(EmployeeArea employeeArea){
        return modelMapper.map(employeeArea, EmployeeAreaDTO.class);
    }

    public EmployeeArea toEmployeeArea(EmployeeAreaDTO employeeAreaDTO){
        return employeeAreaRepository.findById(employeeAreaDTO.getId()).get();
    }

}
