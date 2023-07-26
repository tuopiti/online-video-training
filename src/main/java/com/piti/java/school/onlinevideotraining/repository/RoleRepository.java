package com.piti.java.school.onlinevideotraining.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.piti.java.school.onlinevideotraining.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByName(String name);
	
	@Query(
	        value = "SELECT * FROM roles WHERE id NOT IN (SELECT roles_id FROM users_roles WHERE user_id = ?1)", 
	        nativeQuery = true
	)
	List<Role> getUserNotRoles(Long userId);
}
