package com.yuonetoy.Repository;

import com.yuonetoy.Entitiy.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select c " +
            "from Employee c " +
            "left outer join fetch c.employeeMachineStocks " +
            "left outer join fetch c.employeeProductStocks " +
            "where c.id = :employeeID")
    Employee getEmployeeStockByemployeeId(@Param("employeeID") Long id);

    Employee findByName(String s);
}
