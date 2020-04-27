package com.yuonetoy.Entitiy.Area;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="SalesAccount_area")
public class SalesAccountArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SA_A_ID")
    private Long id;

    @Column(name = "AREA_NM")
    private String name;

    public void update(String name){
        this.name = name;
    }
}
