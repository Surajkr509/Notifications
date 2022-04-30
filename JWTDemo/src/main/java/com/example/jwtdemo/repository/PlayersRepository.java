package com.example.jwtdemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.jwtdemo.model.Players;

@Repository
public interface PlayersRepository extends JpaRepository<Players, Long> {

	Boolean existsByEmail(String email);

	boolean existsByMobNo(String mobNo);

	Optional<Players> findByEmail(String email);
	Players findByUserName(String userName);

	Optional<Players> findByPassword(String password);

	@Query(value = "SELECT p.id FROM Players p where p.email =:email", nativeQuery = false)
	Long findIdByEmail(String email);


	


	
}
