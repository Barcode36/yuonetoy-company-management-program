package com.yuonetoy.Repository.Area;

import com.yuonetoy.Entitiy.Area.SalesAccountArea;
import com.yuonetoy.Entitiy.CompanyType.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesAccountAreaRepository extends JpaRepository<SalesAccountArea, Long> {

    SalesAccountArea findByName(String name);
}
