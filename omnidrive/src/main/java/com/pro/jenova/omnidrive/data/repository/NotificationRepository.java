package com.pro.jenova.omnidrive.data.repository;


import com.pro.jenova.omnidrive.data.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface NotificationRepository extends JpaRepository<Notification, String> {

}
