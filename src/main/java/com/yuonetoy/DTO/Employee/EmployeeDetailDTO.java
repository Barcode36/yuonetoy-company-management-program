package com.yuonetoy.DTO.Employee;

import com.yuonetoy.DTO.Area.EmployeeAreaDTO;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class EmployeeDetailDTO {
    private Long id;
    private String name;
    private String ph;
    private String address;
    private LocalDate entryDate;
    private EmployeeAreaDTO area;
}
