package com.elmenus.task.drones.repository;

import com.elmenus.task.drones.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
