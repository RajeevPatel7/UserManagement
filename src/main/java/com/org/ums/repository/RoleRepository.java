package com.org.ums.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.org.ums.entity.model.Role;

@Transactional
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByRole(String role);

	@Modifying
	@Query(value = "DELETE FROM ROLE WHERE ROLE_NAME =?1" , nativeQuery = true)
	void deleteByRole(String role);

}
