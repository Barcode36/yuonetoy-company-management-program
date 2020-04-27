package com.yuonetoy.Entitiy.Area;

import com.yuonetoy.DTO.Area.EmployeeAreaDTO;
import com.yuonetoy.Tool.CommonBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="employee_area")
public class EmployeeArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "E_A_ID")
    private Long id;

    @Column(name = "AREA_NM")
    private String name;

    public void update(String name){
        this.name = name;
    }
}
