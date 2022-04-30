package com.example.jwtdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jwtdemo.model.Notifications;

public interface NotificationsRepository extends JpaRepository<Notifications, Long>{

	List<Notifications> findAllByPlayerId(Long playerId);

	Long findByPlayerId(long playerId);

	List<Notifications> findAllByReadStatus(int i);

	Notifications findByTypeId(String typeId);

	Long countAllByReadStatus(int i);

	@Query(value = "SELECT n FROM Notifications n ORDER BY updatedAt DESC")
	List<Notifications> findAllNotifications();

}
