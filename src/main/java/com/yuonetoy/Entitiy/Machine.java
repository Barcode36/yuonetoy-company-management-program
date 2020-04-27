package com.yuonetoy.Entitiy;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="machine")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "M_id")
    private Long id;

    @Type(type = "yes_no")
    @Column(name = "isCoinChanger")
    private Boolean isCoinChanger;

    @Type(type = "yes_no")
    @Column(name = "isProductMachine")
    private Boolean isProductMachine;

    @Column(name = "MACHINE_NM")
    private String name;

    public void update(String name){
        this.name = name;
    }
}
