package com.citlalicue.messaging.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citlalicue.messaging.model.EmployeeAddressInfo;

@Repository
public interface EmployeeAddressRepository extends JpaRepository<EmployeeAddressInfo, Long> {

    Optional<EmployeeAddressInfo> findByEmployeeId(Long employeeId);
}