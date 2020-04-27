package com.yuonetoy.DTO.Product;

import com.yuonetoy.Entitiy.Account.PurchaseAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
