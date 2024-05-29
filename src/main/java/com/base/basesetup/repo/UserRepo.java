package com.base.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.base.basesetup.entity.UserVO;

public interface UserRepo extends JpaRepository<UserVO, Long> {

	boolean existsByUserNameOrEmail(String userName, String email);

	UserVO findByUserName(String userName);

	@Query(value = "select u from UserVO u where u.id =?1")
	UserVO getUserById(Long userId);

	UserVO findByUserNameAndUserId(String userName, Long userId);

	boolean existsByUserName(String code);

	@Query(nativeQuery = true,value = "SELECT COUNT(CASE WHEN type = 'customer' THEN 1 END) AS total_customer,COUNT(CASE WHEN type = 'employee' THEN 1 END) AS total_employee FROM users;")
	UserVO findEmployeeAndCustomerCount(Long userId);

}