package com.base.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.basesetup.entity.EmployeeVO;

public interface EmployeeRepo extends JpaRepository<EmployeeVO, Long> {

	boolean existsByCode(String code);

	boolean existsByEmail(String email);

}
