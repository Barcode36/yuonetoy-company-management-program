package com.yuonetoy.Repository;

import com.yuonetoy.Entitiy.Company;
import com.yuonetoy.Entitiy.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByNameEquals(String name);
}
