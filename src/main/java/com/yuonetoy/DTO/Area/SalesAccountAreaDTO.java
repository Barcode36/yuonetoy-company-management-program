package com.yuonetoy.DTO.Area;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesAccountAreaDTO {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
