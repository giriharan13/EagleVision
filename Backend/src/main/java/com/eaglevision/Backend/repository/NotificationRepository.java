package com.eaglevision.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eaglevision.Backend.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer>{
	
	public List<Notification> findAllByIsReadAndSentTo_userId(Boolean isRead,Integer userId);

	public List<Notification> findAllBySentTo_userId(Integer userId);

}
