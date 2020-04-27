package com.yuonetoy.DTO.Area;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeAreaDTO {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
