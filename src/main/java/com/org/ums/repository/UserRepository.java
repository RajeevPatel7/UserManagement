package com.org.ums.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.org.ums.entity.model.User;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmailId(String email);
	
	@Modifying
	@Query(value = "DELETE FROM USERS WHERE EMAIL_ADDRESS = ?1", nativeQuery = true)
	void deleteByEmailId(String email);
	
}
	