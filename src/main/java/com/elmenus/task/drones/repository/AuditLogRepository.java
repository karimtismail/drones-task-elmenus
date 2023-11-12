package com.elmenus.task.drones.repository;

import com.elmenus.task.drones.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link AuditLog} entities.
 */
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
