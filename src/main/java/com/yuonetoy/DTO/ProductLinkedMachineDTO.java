package com.yuonetoy.DTO;

import com.yuonetoy.DTO.Product.ProductDTO;
import com.yuonetoy.Entitiy.Machine;
import com.yuonetoy.Entitiy.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class ProductLinkedMachineDTO {
    private Long id;
    private MachineDTO machine;
    private ProductDTO product;
}
